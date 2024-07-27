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
    private Long lastMoveHostCurrType;
    private Long lastMoveHostCurrInd;
    private Long lastMoveGuestCurrType;
    private Long lastMoveGuestCurrInd;
    private Boolean isHostLastMoveNew;
    private Boolean isGuestLastMoveNew;
    private Boolean isHostConnected;
    private Boolean isGuestConnected;
}
