package com.microservices.userservice.controller;

import com.microservices.userservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class Routes {

    private final UserRepository userRepository;
    @GetMapping("/")
    public String landing() {
        return "landing";
    }
    @GetMapping("/login")
    public String login() {
        return "log-in";
    }
    @GetMapping("/signup")
    public String signup() {
        return "sign-up";
    }
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("name") != null) {
            model.addAttribute("name", session.getAttribute("name"));
            model.addAttribute("userId",session.getAttribute("userId"));
        }
        return "home";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

}
