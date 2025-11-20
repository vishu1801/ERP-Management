package com.erp.studentInformationSystem.model;

import jakarta.persistence.Entity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AcademicYear extends IdentityEntity{

    private String name;

    private Date startDate;

    private Date endDate;
}
