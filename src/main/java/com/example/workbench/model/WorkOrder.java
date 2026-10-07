package com.example.workbench.model;

import java.util.Date;
import java.util.Vector;

/**
 * Field work order. Uses Java 8-era types (Date, Vector) on purpose.
 */
public class WorkOrder {

    private long id;
    private String title;
    private String description;
    private String owner;
    private String location;
    private OrderStatus status;
    private Date createdAt;
    private Date updatedAt;
    private Vector notes;

    public WorkOrder() {
        this.notes = new Vector();
        this.status = OrderStatus.OPEN;
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @SuppressWarnings("rawtypes")
    public Vector getNotes() {
        return notes;
    }

    @SuppressWarnings("rawtypes")
    public void setNotes(Vector notes) {
        this.notes = notes;
    }
}
