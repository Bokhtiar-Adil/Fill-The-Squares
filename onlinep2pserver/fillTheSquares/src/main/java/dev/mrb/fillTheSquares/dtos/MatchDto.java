package dev.mrb.fillTheSquares.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDto {

    private Long matchId;
    private Long host;
    private Long guest;
    private Long size;
    private String state;
    private Long lastMoveHostCurrType;
    private Long lastMoveHostCurrInd;
    private Long lastMoveGuestCurrType;
    private Long lastMoveGuestCurrInd;
    private Boolean isHostLastMoveNew;
    private Boolean isGuestLastMoveNew;
    private Boolean isHostConnected;
    private Boolean isGuestConnected;
}
