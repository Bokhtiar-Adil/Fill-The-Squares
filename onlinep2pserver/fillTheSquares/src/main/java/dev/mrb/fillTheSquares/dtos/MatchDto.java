package dev.mrb.fillTheSquares.dtos;

public class MatchDto {

    private Long matchId;
    private PlayerDto host;
    private PlayerDto guest;
    private Long size;
    private String state;
    private Long last_move_host_currType;
    private Long last_move_host_currInd;
    private Long last_move_guest_currType;
    private Long last_move_guest_currInd;
    private Boolean isHostLastMoveUpdated;
    private Boolean isGuestLastMoveUpdated;
    private Boolean isHostConnected;
    private Boolean isGuestConnected;
}
