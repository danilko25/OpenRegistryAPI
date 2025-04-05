package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Action;

import java.util.List;

public interface ActionJdbcRepository {
    void saveAll(List<Action> actions);
}
