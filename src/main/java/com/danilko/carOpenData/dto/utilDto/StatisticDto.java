package com.danilko.carOpenData.dto.utilDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {
    private ActionFilterDto actionFilter;
    private Integer actionCount;
}
