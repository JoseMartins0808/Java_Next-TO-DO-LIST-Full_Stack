package br.com.josemartins.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "tasks")
public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private String description;

    @Column(length = 50)
    private String title;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    @Column
    private String priority;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private UUID userId;

    public UUID getId () {
        return this.id;
    }

    public void setId (UUID id) {
        this.id = id;
    }

    public String getDescription () {
        return this.description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getTitle () {
        return this.title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public LocalDateTime getStartAt () {
        return this.startAt;
    }

    public void setStartAt (LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt () {
        return this.endAt;
    }

    public void setEndAt (LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getPriority () {
        return this.priority;
    }

    public void setPriority (String priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt () {
        return this.createdAt;
    }

    public UUID getUserId () {
        return this.userId;
    }

    public void setUserId (UUID userId) {
        this.userId = userId;
    }



}
