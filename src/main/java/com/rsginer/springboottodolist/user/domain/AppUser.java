package com.rsginer.springboottodolist.user.domain;

import com.rsginer.springboottodolist.user.dto.AppUserDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class AppUser {

    @Id
    private UUID id;

    @Column(unique = true)
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    public AppUser() {
        this.id = UUID.randomUUID();
    }

    public AppUser(UUID uuid) {
        this.id = uuid;
    }

    public AppUser(String uuid) {
        this.id = UUID.fromString(uuid);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AppUserDto toDto() {
        var dto = new AppUserDto();
        dto.setFirstName(this.getFirstName());
        dto.setLastName(this.getLastName());
        dto.setUsername(this.getUsername());
        dto.setPassword("******");
        dto.setId(this.getId());

        return dto;
    }
}
