package com.erp.studentInformationSystem.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Standard extends IdentityEntity{

    private String name;

    @OneToMany(mappedBy = "standard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StandardSection> sections = new ArrayList<>();
}
