package dev.mrb.fillTheSquares.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matchId;
    @OneToOne(fetch = FetchType.EAGER)
    private PlayerEntity host;
    @OneToOne(fetch = FetchType.EAGER)
    private PlayerEntity guest;
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
