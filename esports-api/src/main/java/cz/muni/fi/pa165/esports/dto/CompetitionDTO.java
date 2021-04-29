package cz.muni.fi.pa165.esports.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompetitionDTO {
    @EqualsAndHashCode.Include
    private String name;
    private String game;
    private String location;
    private int prizepool;
    private LocalDate date;
}
