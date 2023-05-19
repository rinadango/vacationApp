package com.vacationapp.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DestinationInfo {

    private Integer id;
    private String country;
    private String city;
    private Integer Min_Budget;
    private Integer Max_Budget;

}
