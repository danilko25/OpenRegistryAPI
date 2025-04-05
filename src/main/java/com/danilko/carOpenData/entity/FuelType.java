package com.danilko.carOpenData.entity;

public enum FuelType {
    INDEFINITE("НЕ ВИЗНАЧЕНО"),
    PETROL("БЕНЗИН"),
    PETROL_OR_GAS("БЕНЗИН АБО ГАЗ"),
    PETROL_OR_ELECTRIC("ЕЛЕКТРО АБО БЕНЗИН"),
    PETROL_GAS_OR_ELECTRIC("БЕНЗИН, ГАЗ АБО ЕЛЕКТРО"),
    GAS("ГАЗ"),
    GAS_AND_ELECTRIC("ГАЗ ТА ЕЛЕКТРО"),
    DIESEL("ДИЗЕЛЬНЕ ПАЛИВО"),
    DIESEL_OR_GAS("ДИЗЕЛЬНЕ ПАЛИВО АБО ГАЗ"),
    DIESEL_OR_ELECTRIC("ЕЛЕКТРО АБО ДИЗЕЛЬНЕ ПАЛИВО"),
    ELECTRIC("ЕЛЕКТРО");



    private String valueUkr;

    private FuelType(String value) {
        this.valueUkr = value;
    }

    public String toUkrainian(){
        return valueUkr;
    }
}
