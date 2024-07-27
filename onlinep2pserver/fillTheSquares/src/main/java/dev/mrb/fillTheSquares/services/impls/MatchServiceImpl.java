package dev.mrb.fillTheSquares.services.impls;

import dev.mrb.fillTheSquares.dtos.MatchDto;
import dev.mrb.fillTheSquares.entities.MatchEntity;
import dev.mrb.fillTheSquares.entities.PlayerEntity;
import dev.mrb.fillTheSquares.repositories.MatchRepository;
import dev.mrb.fillTheSquares.repositories.PlayerRepository;
import dev.mrb.fillTheSquares.services.MatchServices;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MatchServiceImpl implements MatchServices {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    private MatchDto entityToDto(MatchEntity matchEntity) {
        MatchDto matchDto = new MatchDto();
        matchDto.setMatchId(matchEntity.getMatchId());
        matchDto.setHost(matchEntity.getHost().getPlayerId());
        matchDto.setGuest(matchEntity.getGuest().getPlayerId());
        matchDto.setSize(matchEntity.getSize());
        matchDto.setState(matchEntity.getState());
        matchDto.setIsGuestConnected(matchEntity.getIsGuestConnected());
        matchDto.setIsHostConnected(matchEntity.getIsHostConnected());
        matchDto.setIsGuestLastMoveNew(matchEntity.getIsGuestLastMoveNew());
        matchDto.setIsHostLastMoveNew(matchEntity.getIsHostLastMoveNew());
        matchDto.setLastMoveGuestCurrInd(matchEntity.getLastMoveGuestCurrInd());
        matchDto.setLastMoveGuestCurrInd(matchEntity.getLastMoveGuestCurrInd());
        matchDto.setLastMoveGuestCurrType(matchEntity.getLastMoveGuestCurrType());
        matchDto.setLastMoveHostCurrType(matchEntity.getLastMoveHostCurrType());
        return matchDto;
    }
    @Override
    public Long createMatch(Long hostId, Long size) {
//        MatchEntity
        PlayerEntity playerEntity = playerRepository.findById(hostId).get();
        if (playerEntity==null) return null;
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setHost(playerEntity);
        matchEntity.setState("WAITING");
        matchEntity.setGuest(null);
        matchEntity.setSize(size);
        matchEntity.setIsHostConnected(true);
        matchEntity.setIsGuestConnected(false);
        matchEntity.setIsHostLastMoveNew(false);
        matchEntity.setIsGuestLastMoveNew(false);
        matchEntity.setLastMoveHostCurrType(null);
        matchEntity.setLastMoveGuestCurrType(null);
        matchEntity.setLastMoveHostCurrInd(null);
        matchEntity.setLastMoveHostCurrInd(null);
        MatchEntity savedMatchEntity = matchRepository.save(matchEntity);
        return savedMatchEntity.getMatchId();
    }

    @Override
    public Optional<Long> findAvailableMatch() {
        return matchRepository.findAvailableMatch();
    }

    @Override
    public int joinAsGuest(Long matchId, Long playerId) {
        PlayerEntity playerEntity = playerRepository.findById(playerId).get();
        if (playerEntity==null) return -1;
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) return 0;
        matchEntity.setGuest(playerEntity);
        matchEntity.setIsGuestConnected(true);
        matchEntity.setState("ACTIVE");
        matchRepository.save(matchEntity);
        return 1;
    }

    @Override
    public int updateMatchState(Long matchId, String newState) {
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) return 0;
        matchEntity.setState(newState);
        matchRepository.save(matchEntity);
        return 1;
    }

    @Override
    public int updateConnectionStatus(Long matchId, Boolean isHost, Boolean isConnected) {
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) return 0;
        if (isHost) matchEntity.setIsHostConnected(isConnected);
        else matchEntity.setIsGuestConnected(isConnected);
        matchRepository.save(matchEntity);
        return 1;
    }

    @Override
    public int updateLastMove(Long matchId, Boolean isHost, Long[] moveProperties) {
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) return 0;
        if (isHost) {
            matchEntity.setLastMoveHostCurrInd(moveProperties[0]);
            matchEntity.setLastMoveHostCurrType(moveProperties[1]);
            matchEntity.setIsHostLastMoveNew(true);
        }
        else {
            matchEntity.setLastMoveGuestCurrInd(moveProperties[0]);
            matchEntity.setLastMoveGuestCurrType(moveProperties[1]);
            matchEntity.setIsGuestLastMoveNew(true);
        }
        return 1;
    }

    @Override
    public Long[] getOpponentLastMove(Long matchId, Boolean isHost) {
        Long[] res = new Long[3];
        res[1] = null;
        res[2] = null;
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) {
            res[0] = 0L;
            return res;
        }
        if (isHost) {
            if (!matchEntity.getIsGuestLastMoveNew()) {
                res[0] = -1L;
                return res;
            }
            res[1] = matchEntity.getLastMoveGuestCurrInd();
            res[2] = matchEntity.getLastMoveGuestCurrType();
        }
        else {
            if (!matchEntity.getIsHostLastMoveNew()) {
                res[0] = -1L;
                return res;
            }
            res[1] = matchEntity.getLastMoveHostCurrInd();
            res[2] = matchEntity.getLastMoveHostCurrType();
        }
        return res;
    }

    @Override
    public int leaveMatch(Long matchId, Boolean isHost) {
        MatchEntity matchEntity = matchRepository.findById(matchId).get();
        if (matchEntity==null) return 0;
        if (isHost) {
            matchEntity.setHost(null);
            matchEntity.setGuest(null);
            matchEntity.setState("FINISHED");
        }
        matchRepository.save(matchEntity);
        return 1;
    }

    @Override
    public void DeleteMatch(Long matchId) {
        matchRepository.deleteById(matchId);
    }
}
