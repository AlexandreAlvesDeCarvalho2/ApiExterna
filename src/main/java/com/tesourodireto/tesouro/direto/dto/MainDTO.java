package com.tesourodireto.tesouro.direto.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MainDTO {

    private int temperatura;
    private int sensacao_termica;
    private int temperatura_min;
    private int temperatura_max;
    private Long pressao;
    private Long humidade;
    
}
