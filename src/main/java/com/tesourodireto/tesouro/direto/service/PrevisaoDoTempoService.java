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




import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

public class HttpClient {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public <T> T sendRequest(String url, String requestBody, String method, List<QueryParam> queryParams, List<Header> headers, Type responseType) throws IOException {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        if (queryParams != null && !queryParams.isEmpty()) {
            for (QueryParam param : queryParams) {
                urlBuilder.addQueryParameter(param.getName(), param.getValue());
            }
        }

        RequestBody body = null;
        if (requestBody != null && !requestBody.isEmpty()) {
            body = RequestBody.create(requestBody, MediaType.parse("application/json; charset=utf-8"));
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build())
                .method(method, body);

        if (headers != null && !headers.isEmpty()) {
            for (Header header : headers) {
                requestBuilder.addHeader(header.getName(), header.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, responseType);
        }
    }
}
