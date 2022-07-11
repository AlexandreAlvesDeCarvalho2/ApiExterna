package com.tesourodireto.tesouro.direto.model;

import java.util.List;

import lombok.Data;

@Data
public class BodyWeather {

    private Clouds clouds;
    private Coord coord;
    private Main main;
    private Sys sys;
    private List<Weather> weather;
    private Wind wind;

    
    private String base;
    private String visibility;
    private Long dt;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;
    
}
