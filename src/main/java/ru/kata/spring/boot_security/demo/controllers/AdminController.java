package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserServiceImpl userServiceImpl;


    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String getUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/{id}")
    public String getUpdateUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        return "update";
    }

    @PatchMapping("/{id}")
    public String updateUserById(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                 @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        User userFromDB = userServiceImpl.findByUsername(user.getUsername());
        if (userFromDB != null && !Objects.equals(userFromDB.getUsername(), userServiceImpl.findUserById(id).getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже существует");
            return "update";
        }
        userServiceImpl.updateUserById(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String removeUserById(@PathVariable("id") Long id) {
        userServiceImpl.deleteUserById(id);
        return "redirect:/admin/users";

    }

    @GetMapping("/new")
    public String getNewUserForm(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";
        User userFromDB = userServiceImpl.findByUsername(user.getUsername());
        if (userFromDB != null) {
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже существует");
            return "new";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }
}
