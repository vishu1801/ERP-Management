package com.erp.authService.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Institute extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
