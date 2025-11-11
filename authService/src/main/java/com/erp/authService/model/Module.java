package com.erp.authService.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Module extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String displayName;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "module_action_mapping",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    private List<ModuleAction> actions = new ArrayList<>();

    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();
}
