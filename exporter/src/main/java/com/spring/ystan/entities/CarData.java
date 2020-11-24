package com.spring.ystan.entities;

import com.spring.ystan.annotation.DataExportAttribute;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarData {

    @Id
    @DataExportAttribute(name = "id")
    private String id;

    @DataExportAttribute(name = "brand")
    private String brand;

    @DataExportAttribute(name = "model")
    private String model;
}
