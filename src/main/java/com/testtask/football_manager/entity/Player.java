package com.testtask.football_manager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Setter
@Getter
@Table(name = "Players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String bio;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate playingSince;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    @JsonBackReference
    private Team currentTeam;

    public int getAgeFullYears() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public int getExperienceMonths() {
        return Period.between(playingSince, LocalDate.now()).getMonths()
                + Period.between(playingSince, LocalDate.now()).getYears() * 12;
    }

    public double getTransferCost() {
        return Math.ceil((double) (getExperienceMonths() * 100000) / getAgeFullYears() * 100) / 100.0;
    }

}
