package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import org.example.tm66.model.Group;
import org.example.tm66.service.OrderService;
import org.example.tm66.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        List<Group> groups = orderService.getGroups();
        List<String> nowEnd = orderService.getNowEndOrderId();
        List<String> returned = orderService.getReturnedOrderId();
        model.addAttribute("groups", groups);
        model.addAttribute("nowEnd", nowEnd);
        model.addAttribute("returned", returned);
        model.addAttribute("now", TimeUtils.now());
        model.addAttribute("admin", false);
        return "groups";
    }

}
