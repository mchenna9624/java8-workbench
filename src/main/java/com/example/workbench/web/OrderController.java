package com.example.workbench.web;

import com.example.workbench.model.CreateOrderRequest;
import com.example.workbench.model.NoteRequest;
import com.example.workbench.model.OrderStatus;
import com.example.workbench.model.StatusChangeRequest;
import com.example.workbench.model.WorkOrder;
import com.example.workbench.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orders;

    public OrderController(OrderService orders) {
        this.orders = orders;
    }

    @GetMapping
    public List list(
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "status", required = false) OrderStatus status) {
        return orders.list(owner, status);
    }

    @GetMapping("/{id}")
    public WorkOrder get(@PathVariable("id") long id) {
        return orders.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrder create(@RequestBody CreateOrderRequest request) {
        return orders.create(request);
    }

    @PutMapping("/{id}")
    public WorkOrder update(@PathVariable("id") long id, @RequestBody CreateOrderRequest request) {
        return orders.update(id, request);
    }

    @PostMapping("/{id}/status")
    public WorkOrder changeStatus(@PathVariable("id") long id, @RequestBody StatusChangeRequest request) {
        return orders.changeStatus(id, request == null ? null : request.getStatus());
    }

    @PostMapping("/{id}/notes")
    public WorkOrder addNote(@PathVariable("id") long id, @RequestBody NoteRequest request) {
        String author = request == null ? null : request.getAuthor();
        String text = request == null ? null : request.getText();
        return orders.addNote(id, author, text);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        orders.delete(id);
    }
}
