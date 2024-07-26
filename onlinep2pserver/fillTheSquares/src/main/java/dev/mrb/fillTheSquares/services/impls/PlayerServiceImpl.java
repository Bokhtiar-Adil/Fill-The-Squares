package dev.mrb.fillTheSquares.services.impls;

import dev.mrb.fillTheSquares.dtos.PlayerDto;
import dev.mrb.fillTheSquares.entities.PlayerEntity;
import dev.mrb.fillTheSquares.repositories.PlayerRepository;
import dev.mrb.fillTheSquares.services.PlayerServices;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerServices {

    private final PlayerRepository playerRepository;
    public PlayerDto createNewPlayer(PlayerDto playerDto) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUsername(playerDto.getUsername());
        PlayerEntity savedEntity = playerRepository.save(playerEntity);
        PlayerDto savedDto = new PlayerDto();
        savedDto.setPlayerId(savedEntity.getPlayerId());
        savedDto.setUsername(savedEntity.getUsername());
        return savedDto;
    }
}
