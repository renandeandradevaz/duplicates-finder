package com.renanvaz.duplicatesfinder.dto;

import com.renanvaz.duplicatesfinder.enums.Accuracy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    private Integer sourceId;
    private Integer matchId;
    private Accuracy accuracy;
}