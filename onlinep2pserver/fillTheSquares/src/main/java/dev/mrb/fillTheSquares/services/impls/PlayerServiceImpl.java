package dev.mrb.fillTheSquares.services.impls;

import dev.mrb.fillTheSquares.dtos.PlayerDto;
import dev.mrb.fillTheSquares.entities.PlayerEntity;
import dev.mrb.fillTheSquares.repositories.PlayerRepository;
import dev.mrb.fillTheSquares.services.PlayerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerServices {

    private final PlayerRepository playerRepository;
    
    private PlayerDto entityToDto(PlayerEntity playerEntity) {
        if (playerEntity==null) return null;
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(playerEntity.getPlayerId());
        playerDto.setUsername(playerEntity.getUsername());
        return playerDto;
    }
    
    public PlayerDto createNewPlayer(PlayerDto playerDto) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUsername(playerDto.getUsername());
        PlayerEntity savedEntity = playerRepository.save(playerEntity);
        System.out.println(savedEntity.getPlayerId());
        return entityToDto(savedEntity);
    }

    @Override
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Override
    public PlayerDto findPlayer(Long playerId) {
        return entityToDto(playerRepository.findById(playerId).get());
    }

    @Override
    public PlayerDto updateUsername(Long playerId, String newUsername) {
        PlayerEntity playerEntity = playerRepository.findById(playerId).get();
        playerEntity.setUsername(newUsername);
        PlayerEntity savedPlayerEntity = playerRepository.save(playerEntity);
        return entityToDto(savedPlayerEntity);
    }

}
