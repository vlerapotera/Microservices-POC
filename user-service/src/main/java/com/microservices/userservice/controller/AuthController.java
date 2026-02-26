package com.microservices.userservice.controller;

import com.microservices.userservice.config.PasswordEncoderConfig;
import com.microservices.userservice.dto.LoginDto;
import com.microservices.userservice.dto.SignupDto;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.service.UserService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ModelAndView userLogin(@ModelAttribute("loginDTO") @Valid LoginDto loginDTO, BindingResult result, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        ModelAndView modelAndView1 = new ModelAndView("redirect:/home");
        if (result.hasErrors()) {
            return modelAndView;
        }

        User user = userService.loadUserByEmail(loginDTO.getEmail());

        if (user == null) {
            modelAndView.addObject("errorMessage", "Invalid email or password");
            return modelAndView;
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            modelAndView.addObject("errorMessage", "Invalid email or password");
            return modelAndView;
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("name",user.getFullName());
        modelAndView1.addObject(user);
        return modelAndView1;
    }



    @PostMapping("/register")
    public ModelAndView userRegister(@Valid SignupDto signUpDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("<br>");
            }
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage.toString());
            return new ModelAndView("redirect:/signup");
        }
        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            redirectAttributes.addFlashAttribute("errorMessage", "Email is already taken!");
            return new ModelAndView( "redirect:/signup");
        }

        // create user object
        User user = new User();
        user.setFullName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        userRepository.save(user);
        return new ModelAndView("redirect:/login");
    }


}
