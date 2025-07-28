package com.testtask.football_manager.service;

import com.testtask.football_manager.entity.Player;
import com.testtask.football_manager.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class PayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    @Override
    public void addPlayer(Player player) {
        if (player.getDateOfBirth().isAfter(LocalDate.now()) || player.getPlayingSince().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date cannot be in the future");
        }
        if (Period.between(player.getDateOfBirth(), LocalDate.now()).getYears() < 18)
            throw new IllegalArgumentException("Player must be over 18");
        if (playerRepository.findByName(player.getName()).isPresent()){
            throw new IllegalArgumentException("Player name taken");
        }
        playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        Player playerToUpdate = playerRepository.findById(player.getId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        if (playerToUpdate.getCurrentTeam() != null && !Objects.equals(playerToUpdate.getCurrentTeam().getId(), player.getCurrentTeam().getId()))
            throw new IllegalArgumentException("Transfer detected, use proper transfer endpoint");
        if (!playerToUpdate.getName().equals(player.getName()) && playerRepository.findByName(player.getName()).isPresent())
            throw new IllegalArgumentException("Player name taken");
        if (Period.between(player.getDateOfBirth(), LocalDate.now()).getYears() < 18)
            throw new IllegalArgumentException("Player must be over 18");

        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayerTeam(Player player) {
        Player playerToUpdate = playerRepository.findById(player.getId()).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        playerToUpdate.setCurrentTeam(player.getCurrentTeam());
        return playerRepository.save(playerToUpdate);
    }

    @Override
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id))
            throw new IllegalArgumentException("Player not found");
        playerRepository.deleteById(id);
    }
}
