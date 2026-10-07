package com.example.workbench.service;

import com.example.workbench.model.CreateOrderRequest;
import com.example.workbench.model.OrderStatus;
import com.example.workbench.model.WorkOrder;
import com.example.workbench.web.BadRequestException;
import com.example.workbench.web.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    @Test
    public void createAndFindOrder() {
        OrderService service = new OrderService();
        int before = service.count();
        CreateOrderRequest request = new CreateOrderRequest();
        request.setTitle("Inspect roof drain");
        request.setOwner("Asha");
        request.setLocation("Roof");
        WorkOrder created = service.create(request);
        Assertions.assertEquals(before + 1, service.count());
        Assertions.assertEquals("Inspect roof drain", service.get(created.getId()).getTitle());
        Assertions.assertEquals(OrderStatus.OPEN, created.getStatus());
    }

    @Test
    public void rejectsBlankTitle() {
        OrderService service = new OrderService();
        BadRequestException expected = Assertions.assertThrows(BadRequestException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                service.create(new CreateOrderRequest());
            }
        });
        Assertions.assertTrue(expected.getMessage().contains("Title"));
    }

    @Test
    public void missingOrderThrowsNotFound() {
        OrderService service = new OrderService();
        NotFoundException expected = Assertions.assertThrows(NotFoundException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                service.get(1L);
            }
        });
        Assertions.assertTrue(expected.getMessage().contains("1"));
    }

    @Test
    public void filterByOwnerAndStatus() {
        OrderService service = new OrderService();
        Assertions.assertFalse(service.list("Priya", OrderStatus.IN_PROGRESS).isEmpty());
        Assertions.assertTrue(service.list("nobody", null).isEmpty());
    }
}
