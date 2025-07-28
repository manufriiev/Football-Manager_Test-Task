package com.testtask.football_manager.controllers;

import com.testtask.football_manager.entity.Player;
import com.testtask.football_manager.entity.Team;
import com.testtask.football_manager.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@AllArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getTeamById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(teamService.getTeamById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeam(@RequestBody Team team) {
        try {
            teamService.addTeam(team);
            return ResponseEntity.status(HttpStatus.CREATED).body("Team added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTeam(@RequestBody Team team) {
        try {
            return ResponseEntity.ok(teamService.updateTeam(team));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestParam Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok("Team deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/transferPlayer")
    public ResponseEntity<?> transferPlayer(@RequestParam Long playerId, @RequestParam Long teamId) {
        try {
            Player updatedPlayer = teamService.transferPlayer(playerId, teamId);
            return ResponseEntity.ok(updatedPlayer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
