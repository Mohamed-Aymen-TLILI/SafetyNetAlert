package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FloodListDTO {
    private String address;

    private List<FloodDTO> floodDTOList = new ArrayList<>();
}
