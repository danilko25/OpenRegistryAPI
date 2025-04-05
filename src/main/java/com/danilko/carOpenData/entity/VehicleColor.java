package com.danilko.carOpenData.entity;

public enum VehicleColor {
    INDEFINITE("НЕВИЗНАЧЕНИЙ"),
    WHITE("БІЛИЙ"),
    BEIGE("БЕЖЕВИЙ"),
    YELLOW("ЖОВТИЙ"),
    GREEN("ЗЕЛЕНИЙ"),
    BROWN("КОРИЧНЕВИЙ"),
    ORANGE("ОРАНЖЕВИЙ"),
    BLUE("СИНІЙ"),
    GREY("СІРИЙ"),
    PURPLE("ФІОЛЕТОВИЙ"),
    RED("ЧЕРВОНИЙ"),
    BLACK("ЧОРНИЙ");


    private String colorUkr;

    private VehicleColor(String color) {
        this.colorUkr = color;
    }

    public String toUkrainian() {
        return colorUkr;
    }
}
