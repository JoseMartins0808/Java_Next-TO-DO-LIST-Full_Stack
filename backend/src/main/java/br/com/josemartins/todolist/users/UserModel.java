package br.com.josemartins.todolist.users;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="users")
public class UserModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setUsername (String username) {
        this.username = username;
    }

    public String getUsername () {
        return this.username;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return this.password;
    }

    public LocalDateTime getCreatedAt () {
        return this.createdAt;
    }

    public UUID getId () {
        return this.id;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return this.email;
    }
}