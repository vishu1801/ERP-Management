package com.erp.studentInformationSystem.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Caste extends IdentityEntity {

    private String name;
}
