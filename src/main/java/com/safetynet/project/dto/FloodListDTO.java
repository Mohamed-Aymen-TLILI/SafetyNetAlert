package com.safetynet.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FloodListDTO {
    private String address;

    private List<FloodDTO> floodDTOList = new ArrayList<>();
}
