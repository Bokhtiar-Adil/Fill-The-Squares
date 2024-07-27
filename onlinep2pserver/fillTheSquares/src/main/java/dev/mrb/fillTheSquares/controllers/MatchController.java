package dev.mrb.fillTheSquares.controllers;

import dev.mrb.fillTheSquares.dtos.MatchDto;
import dev.mrb.fillTheSquares.services.MatchServices;
import dev.mrb.fillTheSquares.services.PlayerServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/match")
public class MatchController {

    private final MatchServices matchServices;
    private final PlayerServices playerServices;

    @PostMapping(path = "/start")
    public ResponseEntity<Long> hostNewMatch(@RequestBody Long hostId, @RequestBody Long size, final HttpServletRequest request) {
        if (size<3 || size>8) return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
        Long matchId = matchServices.createMatch(hostId, size);
        if (matchId==null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(matchId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<Long> findMatch(final HttpServletRequest request) {
        Long matchId = matchServices.findAvailableMatch().get();
        if (matchId==null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(matchId, HttpStatus.FOUND);
    }

    @PutMapping(path = "/join")
    public ResponseEntity<String> joinMatch(@RequestBody Long matchId, @RequestBody Long playerId, final HttpServletRequest request) {
        int value = matchServices.joinAsGuest(matchId, playerId);
        if (value==0) return new ResponseEntity<>("Invalid match id", HttpStatus.NOT_FOUND);
        else if (value==-1) return new ResponseEntity<>("Invalid guest player id", HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>("Joined", HttpStatus.OK);
    }






















}
