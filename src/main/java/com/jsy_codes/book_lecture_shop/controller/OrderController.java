package com.jsy_codes.book_lecture_shop.controller;


import com.jsy_codes.book_lecture_shop.domain.Order;
import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.item.Item;
import com.jsy_codes.book_lecture_shop.repository.OrderSearch;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.CourseService;
import com.jsy_codes.book_lecture_shop.service.ItemService;
import com.jsy_codes.book_lecture_shop.service.OrderService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;
    private final CourseService courseService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<User> users = userService.findUsers();
        List<Item> items = itemService.findItems();


        model.addAttribute("users", users);
        model.addAttribute("items", items);

        return "order/orderForm";
    }
    @PostMapping("/order")
    public String order(@AuthenticationPrincipal CustomUserDetails userDetails,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(userDetails.getUserId(), itemId, count);
        return "redirect:/orders";
    }

    @PostMapping("/orders")
    public String orderSearch(@AuthenticationPrincipal CustomUserDetails userDetails,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(userDetails.getUserId(), itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@AuthenticationPrincipal CustomUserDetails userDetails,@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        orderSearch.setEmail(userDetails.getUsername());
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);
        return "order/orderList";

    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
