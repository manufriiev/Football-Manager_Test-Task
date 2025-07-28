package com.testtask.football_manager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String bio;

    @Column(nullable = false)
    private LocalDate establishmentDate;

    @Column(nullable = false)
    private double commissionRate = 0.;

    private double balance = 0;

    @OneToMany(mappedBy = "currentTeam")
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();
}
