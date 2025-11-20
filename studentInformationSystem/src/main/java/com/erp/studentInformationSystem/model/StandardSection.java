package com.erp.studentInformationSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class StandardSection extends IdentityEntity{

    private String name;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "standard_id")
    private Standard standard;
}
