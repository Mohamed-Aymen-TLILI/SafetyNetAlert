package com.safetynet.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FireStationPeopleDTO {

    private List<PeopleCommunity>  FireStationPeopleDTOList = new ArrayList<>();

    private Long adultsNumber;

    private Long childrenNumber;

    public long getAdultsNumber() {
        adultsNumber = FireStationPeopleDTOList.stream().filter(c -> c.getAge() > 18).count();
        return adultsNumber;
    }

    public long getChildrenNumber(){
        childrenNumber = FireStationPeopleDTOList.stream().filter(c -> c.getAge() <= 18).count();
        return childrenNumber;
    }

}
