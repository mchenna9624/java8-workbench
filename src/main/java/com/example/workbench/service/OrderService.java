package com.example.workbench.service;

import com.example.workbench.model.CreateOrderRequest;
import com.example.workbench.model.OrderNote;
import com.example.workbench.model.OrderStatus;
import com.example.workbench.model.WorkOrder;
import com.example.workbench.web.BadRequestException;
import com.example.workbench.web.NotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Vector store = new Vector();
    private final AtomicLong ids = new AtomicLong(1000L);

    public OrderService() {
        seed();
    }

    public synchronized List list(String owner, OrderStatus status) {
        List matches = new ArrayList();
        Iterator iterator = store.iterator();
        while (iterator.hasNext()) {
            WorkOrder order = (WorkOrder) iterator.next();
            if (StringUtils.isNotBlank(owner)
                    && (order.getOwner() == null
                    || !order.getOwner().toLowerCase().contains(owner.toLowerCase()))) {
                continue;
            }
            if (status != null && order.getStatus() != status) {
                continue;
            }
            matches.add(order);
        }
        return Collections.unmodifiableList(matches);
    }

    public synchronized WorkOrder get(long id) {
        WorkOrder found = find(id);
        if (found == null) {
            throw new NotFoundException("Work order " + id + " was not found");
        }
        return found;
    }

    public synchronized WorkOrder create(CreateOrderRequest request) {
        String title = request == null ? null : StringUtils.trimToNull(request.getTitle());
        if (title == null) {
            throw new BadRequestException("Title is required");
        }
        WorkOrder order = new WorkOrder();
        order.setId(ids.incrementAndGet());
        order.setTitle(title);
        order.setDescription(request.getDescription());
        order.setOwner(StringUtils.defaultIfBlank(request.getOwner(), "unassigned"));
        order.setLocation(StringUtils.defaultIfBlank(request.getLocation(), "HQ"));
        order.setStatus(OrderStatus.OPEN);
        store.add(order);
        return order;
    }

    public synchronized WorkOrder update(long id, CreateOrderRequest request) {
        WorkOrder order = get(id);
        if (request.getTitle() != null) {
            String title = StringUtils.trimToNull(request.getTitle());
            if (title == null) {
                throw new BadRequestException("Title cannot be blank");
            }
            order.setTitle(title);
        }
        if (request.getDescription() != null) {
            order.setDescription(request.getDescription());
        }
        if (request.getOwner() != null) {
            order.setOwner(request.getOwner());
        }
        if (request.getLocation() != null) {
            order.setLocation(request.getLocation());
        }
        order.setUpdatedAt(new Date());
        return order;
    }

    public synchronized WorkOrder changeStatus(long id, OrderStatus status) {
        if (status == null) {
            throw new BadRequestException("Status is required");
        }
        WorkOrder order = get(id);
        order.setStatus(status);
        order.setUpdatedAt(new Date());
        order.getNotes().add(new OrderNote("system", "Status changed to " + status));
        return order;
    }

    public synchronized WorkOrder addNote(long id, String author, String text) {
        if (StringUtils.isBlank(text)) {
            throw new BadRequestException("Note text is required");
        }
        WorkOrder order = get(id);
        order.getNotes().add(new OrderNote(StringUtils.defaultIfBlank(author, "anonymous"), text.trim()));
        order.setUpdatedAt(new Date());
        return order;
    }

    public synchronized void delete(long id) {
        WorkOrder order = get(id);
        store.remove(order);
    }

    public synchronized int count() {
        return store.size();
    }

    private WorkOrder find(long id) {
        Iterator iterator = store.iterator();
        while (iterator.hasNext()) {
            WorkOrder order = (WorkOrder) iterator.next();
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    private void seed() {
        CreateOrderRequest hvac = new CreateOrderRequest();
        hvac.setTitle("Replace HVAC filter in Building 4");
        hvac.setDescription("Quarterly filter swap. Access via loading dock.");
        hvac.setOwner("Priya");
        hvac.setLocation("Building 4");
        WorkOrder first = create(hvac);
        changeStatus(first.getId(), OrderStatus.IN_PROGRESS);

        CreateOrderRequest badge = new CreateOrderRequest();
        badge.setTitle("Repair badge reader at east gate");
        badge.setDescription("Reader beeps but does not unlock.");
        badge.setOwner("Marcus");
        badge.setLocation("East Gate");
        create(badge);

        CreateOrderRequest lamps = new CreateOrderRequest();
        lamps.setTitle("Swap warehouse bay lamps");
        lamps.setDescription("Three fixtures out in aisle C.");
        lamps.setOwner("unassigned");
        lamps.setLocation("Warehouse");
        WorkOrder third = create(lamps);
        addNote(third.getId(), "dispatcher", "Parts ordered. ETA Friday.");
    }
}
