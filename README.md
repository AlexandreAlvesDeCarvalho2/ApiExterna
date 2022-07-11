# Consumir APis externas

Criar projeto com Spring Initializr https://start.spring.io/ 
- Java 11 
- dependencias [ Spring Web, Spring Boot Dev tools, Lombok]

Consumir API do tempo em https://openweathermap.org/current

Em application.proprerties adicionar variaveis da aplicação

  api.key=https://openweathermap.org/api

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
 

