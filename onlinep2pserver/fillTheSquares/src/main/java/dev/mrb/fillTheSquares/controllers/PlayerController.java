package dev.mrb.fillTheSquares.controllers;

import dev.mrb.fillTheSquares.dtos.PlayerDto;
import dev.mrb.fillTheSquares.services.PlayerServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/edit/{playerId}")
    public ResponseEntity<String> editPlayerUsername(@RequestBody Long playerId, @RequestBody String username, final HttpServletRequest request) {
        if (playerService.findPlayer(playerId)==null)
            return new ResponseEntity<>("Invalid player id", HttpStatus.NOT_FOUND);
        playerService.updateUsername(playerId, username);
        return new ResponseEntity<>("Username updated", HttpStatus.OK);
    }

}
