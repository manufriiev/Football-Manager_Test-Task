package com.testtask.football_manager.service;

import com.testtask.football_manager.entity.Player;
import com.testtask.football_manager.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();
    Team getTeamById(Long id);
    void addTeam(Team team);
    Team updateTeam(Team team);
    void deleteTeam(Long id);
    Player transferPlayer(Long playerId, Long teamId);
}
