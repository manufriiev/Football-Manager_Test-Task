package com.testtask.football_manager.service;

import com.testtask.football_manager.entity.Player;
import com.testtask.football_manager.entity.Team;
import com.testtask.football_manager.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }

    @Override
    public void addTeam(Team team) {
        if (team.getEstablishmentDate().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Establishment date cannot be in the future");
        if (teamRepository.findByName(team.getName()).isPresent())
            throw new IllegalArgumentException("Name is already taken");
        if (team.getCommissionRate()<0 || team.getCommissionRate()>10)
            throw new IllegalArgumentException("Commission rate must be between 0 and 10");
        teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team team) {
        Team teamToUpdate = teamRepository.findById(team.getId()).orElseThrow(() -> new IllegalArgumentException("Team not found"));
        if (!teamToUpdate.getName().equals(team.getName()) && teamRepository.findByName(team.getName()).isPresent())
            throw new IllegalArgumentException("Name is already taken");
        if (team.getCommissionRate()<0 || team.getCommissionRate()>10)
            throw new IllegalArgumentException("Commission rate must be between 0 and 10");
        return teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found"));
        for (Player player : team.getPlayers()) {
            player.setCurrentTeam(null);
            playerService.updatePlayerTeam(player);
        }
        teamRepository.deleteById(id);
    }

    @Override
    public Player transferPlayer(Long playerId, Long teamId) {
        Team futureTeam = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Team not found"));
        Player player = playerService.getPlayer(playerId);
        if (player.getCurrentTeam() == futureTeam){
            throw new IllegalArgumentException("Player already belongs to the requested team");
        }
        if (player.getCurrentTeam() == null){
            throw new IllegalArgumentException("Player doesn't belong to a team, update player instead");
        }
        Team currentTeam = player.getCurrentTeam();
        double totalTransferCost = Math.ceil(player.getTransferCost()*((currentTeam.getCommissionRate()/100f)+1f) * 100) / 100.0;
        if (futureTeam.getBalance()<totalTransferCost){
            throw new IllegalArgumentException("Buying team doesn't have enough cash");
        }
        futureTeam.setBalance(futureTeam.getBalance()-totalTransferCost);
        currentTeam.setBalance(currentTeam.getBalance()+totalTransferCost);
        player.setCurrentTeam(futureTeam);
        updateTeam(futureTeam);
        updateTeam(currentTeam);
        return playerService.updatePlayerTeam(player);
    }
}
