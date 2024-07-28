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
        if (!matchServices.matchIdValidity(matchId)) return new ResponseEntity<>("Invalid match id", HttpStatus.NOT_FOUND);
        int value = matchServices.joinAsGuest(matchId, playerId);
        if (value==-1) return new ResponseEntity<>("Invalid guest player id", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Joined", HttpStatus.OK);
    }

    @GetMapping(path = "/getOppLastMove")
    public ResponseEntity<Long[]> getOpponentLastMove(@RequestBody Long matchId, @RequestBody Long playerId, @RequestBody Boolean isHost, final HttpServletRequest request) {
        if (!matchServices.matchIdValidity(matchId)) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        if (playerServices.findPlayer(playerId)==null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Long[] moveProps = matchServices.getOpponentLastMove(matchId, isHost);
        if (moveProps==null) return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        return new ResponseEntity<>(moveProps, HttpStatus.FOUND);
    }

    @PutMapping(path = "/updateLastMove")
    public ResponseEntity<String> updateLastMove(@RequestBody Long matchId, @RequestBody Long playerId, @RequestBody Boolean isHost, @RequestBody Long[] moveProps, final HttpServletRequest request) {
        if (!matchServices.matchIdValidity(matchId)) return new ResponseEntity<>("Invalid match id", HttpStatus.NOT_FOUND);
        if (playerServices.findPlayer(playerId)==null) return new ResponseEntity<>("Invalid player id", HttpStatus.NOT_FOUND);
        Long[] hostGuest = matchServices.findMatchHostAndGuest(matchId);
        if (isHost && hostGuest[0]!=playerId) return new ResponseEntity<>("Invalid host id", HttpStatus.NOT_ACCEPTABLE);
        if (!isHost && hostGuest[1]!=playerId) return new ResponseEntity<>("Invalid guest id", HttpStatus.NOT_ACCEPTABLE);
        int ff = matchServices.updateLastMove(matchId, isHost, moveProps);
        return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/leave")
    public ResponseEntity<String> leaveMatch(@RequestBody Long matchId, @RequestBody Long playerId, @RequestBody Boolean isHost, @RequestBody Long[] moveProps, final HttpServletRequest request) {
        if (!matchServices.matchIdValidity(matchId)) return new ResponseEntity<>("Invalid match id", HttpStatus.NOT_FOUND);
        if (playerServices.findPlayer(playerId)==null) return new ResponseEntity<>("Invalid player id", HttpStatus.NOT_FOUND);
        Long[] hostGuest = matchServices.findMatchHostAndGuest(playerId);
        if (isHost && hostGuest[0]!=playerId) return new ResponseEntity<>("Invalid host id", HttpStatus.NOT_ACCEPTABLE);
        if (!isHost && hostGuest[1]!=playerId) return new ResponseEntity<>("Invalid guest id", HttpStatus.NOT_ACCEPTABLE);
        matchServices.leaveMatch(matchId, isHost);
        return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<String> deleteMatch(@RequestBody Long matchId, final HttpServletRequest request) {
        if (!matchServices.matchIdValidity(matchId)) return new ResponseEntity<>("Invalid match id", HttpStatus.NOT_FOUND);
        matchServices.deleteMatch(matchId);
        return new ResponseEntity<>("Match Finished", HttpStatus.GONE);
    }





















}
