package com.vacationapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "destination_info")
public class DestinationInfo {

    @Id
    private Integer id;

    private String country;
    private String city;
    private Integer Min_Budget;
    private Integer Max_Budget;

}
