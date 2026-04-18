package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import org.example.tm66.service.OrderService;
import org.example.tm66.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UIController {

    private final OrderService orderService;

    @GetMapping("/")
    public String showUploadForm(Model model) {
        return "upload";
    }

    @GetMapping("/groups")
    public String showGroups(Model model) {
        model.addAttribute("groups", orderService.getGroups());
        model.addAttribute("nowEnd", orderService.getNowEndOrderId());
        model.addAttribute("returned", orderService.getReturnedOrderId());
        model.addAttribute("now", TimeUtils.now());
        model.addAttribute("admin", false);
        return "groups";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("trash", orderService.getTrashLinkMap());
        model.addAttribute("returned", orderService.getReturnedLinkMap());
        return "edit";
    }

}
