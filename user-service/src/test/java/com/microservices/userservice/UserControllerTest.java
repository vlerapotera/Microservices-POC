package com.microservices.userservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservices.userservice.controller.AuthController;
import com.microservices.userservice.dto.LoginDto;
import com.microservices.userservice.dto.SignupDto;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private HttpSession session;
    @InjectMocks
    private AuthController userController;
    @BeforeEach
    public void setup() {
    }


    @Test
    public void testUserLogin_validCredentials() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        LoginDto loginDTO = new LoginDto(email, password);
        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Mock dependencies
        when(userService.loadUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        // Call the method and assert
        ModelAndView modelAndView = userController.userLogin(loginDTO, new BeanPropertyBindingResult(loginDTO, "loginDTO"), session);

        assertEquals("redirect:/home", modelAndView.getViewName());
    }
    @Test
    public void testUserLogin_invalidCredentials() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        String wrongPassword = "wrongPassword";
        LoginDto loginDTO = new LoginDto(email, wrongPassword);
        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Mock dependencies
        when(userService.loadUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(wrongPassword, user.getPassword())).thenReturn(false);

        // Call the method and assert
        ModelAndView modelAndView = userController.userLogin(loginDTO, new BeanPropertyBindingResult(loginDTO, "loginDTO"), null);

        assertEquals("redirect:/login", modelAndView.getViewName());
        assertEquals("Invalid email or password", modelAndView.getModel().get("errorMessage"));
    }
    @Test
    public void testUserLogin_missingUser() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        LoginDto loginDTO = new LoginDto(email, password);

        // Mock dependencies
        when(userService.loadUserByEmail(email)).thenReturn(null);

        // Call the method and assert
        ModelAndView modelAndView = userController.userLogin(loginDTO, new BeanPropertyBindingResult(loginDTO, "loginDTO"), null);

        assertEquals("redirect:/login", modelAndView.getViewName());
        assertEquals("Invalid email or password", modelAndView.getModel().get("errorMessage"));
    }
    @Test
    public void testUserRegister_validInput() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password123";
        String phoneNumber = "12345678";
        SignupDto signUpDto = SignupDto.builder()
                .name("Test User")
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        // Mock dependencies
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // Call the method and assert
        ModelAndView modelAndView = userController.userRegister(signUpDto, new BeanPropertyBindingResult(signUpDto, "signUpDto"), null);

        assertEquals("redirect:/login", modelAndView.getViewName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUserRegister_emailAlreadyExists() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password123";
        String phoneNumber = "12345678";
        SignupDto signUpDto = SignupDto.builder()
                .name("Test User")
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        // Mock dependencies
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Call the method and assert
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ModelAndView modelAndView = userController.userRegister(signUpDto,
                new BeanPropertyBindingResult(signUpDto, "signUpDto"), redirectAttributes);

        assertEquals("redirect:/signup", modelAndView.getViewName());
    }

    @Test
    public void testUserRegister_invalidInput() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "";
        String phoneNumber = "12345678";
        SignupDto signUpDto = SignupDto.builder()
                .name("Test User")
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(signUpDto, "signUpDto");
        bindingResult.rejectValue("password", "error.password", "Password is required");

        // Call the method and assert
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ModelAndView modelAndView = userController.userRegister(signUpDto, bindingResult, redirectAttributes);

        assertEquals("redirect:/signup", modelAndView.getViewName());
    }
}
