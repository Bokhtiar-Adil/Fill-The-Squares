package dev.mrb.fillTheSquares.services;

import dev.mrb.fillTheSquares.dtos.MatchDto;
import dev.mrb.fillTheSquares.dtos.PlayerDto;

import java.util.Optional;

public interface MatchServices {

    public Long createMatch(Long hostId, Long size);

    Boolean matchIdValidity(Long matchId);

    Long[] findMatchHostAndGuest(Long matchId);
    public Optional<Long> findAvailableMatch();
    public int joinAsGuest(Long matchId, Long playerId);
    public int updateMatchState(Long matchId, String newState);
    public int updateConnectionStatus(Long matchId, Boolean isHost, Boolean isConnected);
    public int updateLastMove(Long matchId, Boolean isHost, Long[] moveProperties);
    public Long[] getOpponentLastMove(Long matchId, Boolean isHost);
    public void leaveMatch(Long matchId, Boolean isHost);
    public void deleteMatch(Long matchId);


}
