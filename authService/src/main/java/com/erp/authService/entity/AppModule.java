package com.erp.authService.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AppModule extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String displayName;

    private String description;

    @ManyToMany(mappedBy = "appModules")
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "appModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page> pages = new ArrayList<>();
}
