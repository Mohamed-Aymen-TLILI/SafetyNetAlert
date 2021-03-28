package com.safetynet.project.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.project.model.dto.Views;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Firestations")
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Private.class)
    private Long id;

    @JsonView(Views.Private.class)
    private String address;

    @JsonView(Views.Private.class)
    private Integer station;
}
