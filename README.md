# Consumir APis externas

Criar projeto com Spring Initializr https://start.spring.io/ 
- Java 11 
- dependencias [ Spring Web, Dev tools, Lombok]

Consumir API do tempo em https://openweathermap.org/current

Em application.proprerties adicionar variaveis da aplicação

  api.key= criar a api Key no site.. https://openweathermap.org/api

  url.api=https://api.openweathermap.org/data/2.5/weather?q=      *Buscar por cidade*

montar as classes do json 

configurar o controlador 

    @RestController
    @RequestMapping("/tempo")
    public class PrevisaoDoTempo {

      @Value("${api.key}")
      private String apiKey;

      @Value("${url.api}")
      private String urlApi;


      @GetMapping("/{cidade}")
      public BodyWeather getTempo(@PathVariable String cidade){
          RestTemplate restTemplate = new RestTemplate();
          StringBuilder stringBuilder = new StringBuilder();

          String urlFinal = stringBuilder.append(urlApi).append(cidade).append("&appid=").append(apiKey).toString();

          ResponseEntity<BodyWeather> entity = restTemplate.getForEntity(urlFinal, BodyWeather.class);

          return entity.getBody();
      }
    } 
  
 Exemplo de busca:
 
  http://localhost:8080/tempo/São%20Paulo  
   
   retorno: 

  ```json
{
  "temp": 296.91,
  "feels_like": 296.41,
  "temp_min": 296.18,
  "temp_max": 297.35,
  "pressure": 1016,
  "humidity": 41
  }
``` 
 

		<dependency>
			<groupId>com.amazonaws</groupId>
			<artifactId>aws-java-sdk-ses</artifactId>
			<version>1.12.119</version>
		</dependency>

  public void sendEmail(List<DataRequestMensal> dataRequestMensal) {
        List<DataResponse> dataResponseList = new ArrayList<>();



        String senderEmail = "alexandrealvesdecarvalho2@gmail.com";
        String receiverEmail = "alexandrealvesdecarvalho2@gmail.com";
        String emailSubject = "Test Email Subject";

        try {
            SendEmailRequest sendEmailRequest = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(receiverEmail))
                    .withMessage(
                            new Message()
                                    .withBody(
                                            new Body()
                                                    .withHtml(
                                                            new Content().withCharset("UTF-8").withData(CorpoEmail.criarCorpoEmail(dataResponseList, dataResponse.getEmpresa_apontamento(), dataRequestMensal.get(0).getIdClienet(),dataRequestMensal.get(0).getNumeroContrato()))))
                                    .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
                    .withSource(senderEmail);
            SendEmailResult result = getAmazonSimpleEmailService().sendEmail(sendEmailRequest);
            System.out.println(result.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




package com.aws.ses.service;

import java.util.List;

import org.springframework.context.annotation.Bean;

import com.aws.ses.response.DataResponse;

public class CorpoEmail {


    public static String criarCorpoEmail(List<DataResponse> dataResponseList, String nomeCliente, String nrContrato, String dtConsulta) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("</head>");
        sb.append("<table>");
        sb.append(
                "<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> tipo_apontamento");
        sb.append("</th>");
        sb.append(
                "<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> nome_apontamento");
        sb.append("</th>");
        sb.append(
                "<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> empresa_apontamento");
        sb.append("</th>");
        sb.append(
                "<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> dt_ocorrido");
        sb.append("</th>");
        sb.append(
                "<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> vlr_apontamento");
        sb.append("</th>");

        for (DataResponse dataResponse : dataResponseList) {
            sb.append("<tr>");
            sb.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> " + dataResponse.getCodigo_apontamento());
            sb.append("</td>");
            sb.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> " + dataResponse.getNome_apontamento());
            sb.append("</td>");
            sb.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> " + dataResponse.getEmpresa_apontamento());
            sb.append("</td>");
            sb.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> " + dataResponse.getData_ocorrencia());
            sb.append("</td>");
            sb.append("<td style = \"padding: 6px; border: 1px solid #ccc; text-align: left;\"> " + dataResponse.getValor());
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
     
        return sb.toString();
    }


   

}
