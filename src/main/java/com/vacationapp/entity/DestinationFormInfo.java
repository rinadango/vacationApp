package com.vacationapp.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DestinationFormInfo {

    private String city;
    private Integer budget;
    private Date startOfVacation;
    private Date endOfVacation;

}
