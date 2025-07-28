package com.testtask.football_manager.service;

import com.testtask.football_manager.entity.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {
    List<Player> getAllPlayers();
    Player getPlayer(Long id);
    void addPlayer(Player player);
    Player updatePlayer(Player player);
    Player updatePlayerTeam(Player player);
    void deletePlayer(Long id);
}
