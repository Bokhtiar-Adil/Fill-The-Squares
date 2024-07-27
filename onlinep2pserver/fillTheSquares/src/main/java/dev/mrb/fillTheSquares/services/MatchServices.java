package dev.mrb.fillTheSquares.services;

import dev.mrb.fillTheSquares.dtos.MatchDto;
import dev.mrb.fillTheSquares.dtos.PlayerDto;

import java.util.Optional;

public interface MatchServices {

    public Long createMatch(MatchDto matchDto);
    public Optional<Long> findAvailableMatch();
    public int joinAsHost(Long matchId, Long playerId);
    public int updateMatchState(Long matchId, String newState);
    public int updateConnectionStatus(Long matchId, Boolean isHost, Boolean isConnected);
    public int updateLastMove(Long matchId, Boolean isHost, Long[] moveProperties);
    public int leaveMatch(Long matchId, Boolean isGuest);
    public int DeleteMatch(Long matchId);


}
