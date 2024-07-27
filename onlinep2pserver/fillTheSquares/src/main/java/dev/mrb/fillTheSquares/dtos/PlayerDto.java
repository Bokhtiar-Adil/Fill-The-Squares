package dev.mrb.fillTheSquares.dtos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {

    private Long playerId;
    private String username;
}
