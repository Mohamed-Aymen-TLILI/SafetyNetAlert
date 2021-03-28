package com.safetynet.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity @IdClass(MedicalRecordsId.class)
@Table(name="MedicalRecords")
public class MedicalRecords {

    @Id
    private String firstName;

    @Id
    private String lastName;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @JsonDeserialize( using = LocalDateDeserializer.class)
    @JsonSerialize( using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;

}
