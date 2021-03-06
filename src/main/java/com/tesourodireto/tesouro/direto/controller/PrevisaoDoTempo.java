package com.tesourodireto.tesouro.direto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tesourodireto.tesouro.direto.dto.MainDTO;
import com.tesourodireto.tesouro.direto.model.BodyWeather;
import com.tesourodireto.tesouro.direto.model.Main;
import com.tesourodireto.tesouro.direto.service.PrevisaoDoTempoService;

@RestController
@RequestMapping("/tempo")
public class PrevisaoDoTempo {

    @Value("${api.key}")
    private String apiKey;

    @Value("${url.api}")
    private String urlApi;

    @Autowired
    private PrevisaoDoTempoService previsaoDoTempoService;


    @GetMapping("/{cidade}")
    public MainDTO getTempo(@PathVariable String cidade){
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder stringBuilder = new StringBuilder();
        
        String urlFinal = stringBuilder.append(urlApi).append(cidade).append("&appid=").append(apiKey).toString();

        ResponseEntity<BodyWeather> entity = restTemplate.getForEntity(urlFinal, BodyWeather.class);
        
        return previsaoDoTempoService.setBody(entity.getBody().getMain());
    }
}
