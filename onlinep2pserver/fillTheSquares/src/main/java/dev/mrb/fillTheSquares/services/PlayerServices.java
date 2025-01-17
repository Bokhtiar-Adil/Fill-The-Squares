package dev.mrb.fillTheSquares.services;

import dev.mrb.fillTheSquares.dtos.PlayerDto;

public interface PlayerServices {
    public PlayerDto createNewPlayer(PlayerDto playerDto);

    public void deletePlayer(Long playerId);

    public PlayerDto findPlayer(Long playerId);

    public PlayerDto updateUsername(Long playerId, String newUsername);
}
