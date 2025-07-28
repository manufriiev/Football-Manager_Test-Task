package com.testtask.football_manager.controllers;

import com.testtask.football_manager.entity.Player;
import com.testtask.football_manager.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
@Slf4j
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getPlayerById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(playerService.getPlayer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlayer(@RequestBody Player player) {
        try {
            playerService.addPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body("Player added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlayer(@RequestBody Player player) {
        try {
            return ResponseEntity.ok(playerService.updatePlayer(player));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePlayer(@RequestParam Long id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.ok("Player deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
