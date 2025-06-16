package com.danilko.carOpenData.dto.mapper;

public interface Mapper <F, T>{
    T mapFrom(F entity);
}
