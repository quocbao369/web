package com.example.web.controller;

import com.example.web.dto.UserInforDTO;
import com.example.web.model.InforUser;
import com.example.web.model.User;
import com.example.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    public UserController() {
    }

    @GetMapping(value = {"/login","/"})
    public String login() {
        return "account/login";
    }

    @PostMapping("login")
    public String login(String username, String password, Model model) {
        if (userService.login(username, password)) {
            String userid = userService.getUserID(username);
            String role = userService.getRole(username);

            session.setAttribute("username", username);
            session.setAttribute("userid", userid);

            if ("admin".equals(role)) {
                return "redirect:/admin";
            } else if ("user".equals(role)) {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
        }
        return "account/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userinforDTO", new UserInforDTO());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(UserInforDTO userinforDTO, Model model) {
        if (!userService.check_pass(userinforDTO.getPassword(),userinforDTO.getRepassword() )) {
            model.addAttribute("error", "Password and Confirm Password do not match.");
            return "account/register";
        }
        if (!userService.check_username(userinforDTO.getUsername())) {
            model.addAttribute("error", "Username already exists.");
            return "account/register";
        }
        if (!userService.check_email(userinforDTO.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "account/register";
        }
        if (!userService.check_phone(userinforDTO.getPhone())) {
            model.addAttribute("error", "Phone already exists.");
            return "account/register";
        }
        if (userService.registerUser(userinforDTO)){
            return "account/login";
        }
        return "account/register";
    }
    @GetMapping("/profile")
    public String showAccount(Model model) {
        String userid = (String) session.getAttribute("userid");
        User user = userService.getUser(userid);
        InforUser userInfo = userService.getInfor(userid);
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        return "account/profile";
    }
    @PostMapping("/profile")
    public String updateAccount(Model model){
        return null;
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("userid");
        session.removeAttribute("username");
        return "redirect:/login";
    }
}
