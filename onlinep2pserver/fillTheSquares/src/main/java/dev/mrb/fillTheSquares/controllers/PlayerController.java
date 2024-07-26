package dev.mrb.fillTheSquares.controllers;

import dev.mrb.fillTheSquares.dtos.PlayerDto;
import dev.mrb.fillTheSquares.services.PlayerServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/players")
public class PlayerController {

    private final PlayerServices playerService;

    @PostMapping(path = "/add")
    public ResponseEntity<PlayerDto> addNewPlayer(@RequestBody String username, final HttpServletRequest request) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(Long.valueOf(1));
        playerDto.setUsername(username);
        PlayerDto savedPlayer = playerService.createNewPlayer(playerDto);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }
}
