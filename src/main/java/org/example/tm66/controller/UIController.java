package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import org.example.tm66.config.AppParams;
import org.example.tm66.service.OrderService;
import org.example.tm66.util.TimeUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UIController {

    private final OrderService orderService;
    private final AppParams appParams;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("version", appParams.getVersion());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", userDetails);
        } else {
            model.addAttribute("user", null);
        }
        return "home";
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("version", appParams.getVersion());
        return "upload";
    }

    @GetMapping("/groups")
    public String showGroups(Model model,
                             @RequestParam(name = "admin", required = false, defaultValue = "false") boolean admin
    ) {
        model.addAttribute("version", appParams.getVersion());
        model.addAttribute("groups", orderService.getGroups());
        model.addAttribute("nowEnd", orderService.getNowEndOrderId());
        model.addAttribute("returned", orderService.getReturnedOrderId());
        model.addAttribute("now", TimeUtils.now());
        model.addAttribute("admin", admin);
        return "groups";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("version", appParams.getVersion());
        model.addAttribute("trash", orderService.getTrashLinkMap());
        model.addAttribute("returned", orderService.getReturnedLinkMap());
        return "edit";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("version", appParams.getVersion());
        return "login";
    }

}
