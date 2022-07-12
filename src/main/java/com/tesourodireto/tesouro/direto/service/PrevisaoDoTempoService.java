package com.tesourodireto.tesouro.direto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.text.html.parser.Entity;

import org.springframework.stereotype.Service;

import com.tesourodireto.tesouro.direto.dto.MainDTO;
import com.tesourodireto.tesouro.direto.model.BodyWeather;
import com.tesourodireto.tesouro.direto.model.Main;

@Service
public class PrevisaoDoTempoService {
    

    private int kelvinToCelsius(double kelvin){
    
        double resultado = kelvin - 273.15 ;
        BigDecimal bd = new BigDecimal(resultado).setScale(0, RoundingMode.HALF_EVEN);
        
        return bd.intValue();
    }

    public MainDTO setBody(Main entity){
        MainDTO mainDto = new MainDTO();
        
        mainDto.setHumidade(entity.getHumidity());
        mainDto.setSensacao_termica(kelvinToCelsius(entity.getFeels_like()));
        mainDto.setPressao(entity.getPressure());
        mainDto.setTemperatura(kelvinToCelsius(entity.getTemp()));
        mainDto.setTemperatura_max(kelvinToCelsius(entity.getTemp_max()));
        mainDto.setTemperatura_min(kelvinToCelsius(entity.getTemp_min()));
        return mainDto;
    }


}
