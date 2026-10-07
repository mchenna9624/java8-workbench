package com.example.workbench.web;

import com.example.workbench.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final OrderService orders;

    public HomeController(OrderService orders) {
        this.orders = orders;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("orderCount", Integer.valueOf(orders.count()));
        return "index";
    }
}
