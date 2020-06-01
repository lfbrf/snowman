# snowman

<details><summary>Usuário Autenticado</summary>


![ezgif com-gif-maker](https://user-images.githubusercontent.com/22921756/83372380-81227980-a39b-11ea-91ef-e2df5c3bc3d3.gif)


</details>

## Simples demonstração



<details><summary> Usuário não autenticado</summary>


![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/22921756/83372346-6b14b900-a39b-11ea-91cf-cbfe9b3e53b9.gif)


</details>

### Tecnologias usadas:
- Java, Spring, HTML, CSS, bootstrap, Jquery, SonarLint, Junit 5, mockito e maven.
- Eclipse (STS) e github;
- Integrações com Facebook (oauth) e Google API.

## Instruçes para deploy e para rodar
### No código está a key para usar SSL, talvez seja necessário gerar uma nova antes de rodar o projeto. 
Adicionar em src/main/resources

.keytool -genkeypair -alias snowman -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore snowman.p12 -validity 3650

### Será necessário configurar as informaçes referente à API do facebook e do banco de dados no arquivo yml
Adicionei um exemplo em: src/main/resources/applicattion_old.yml


### É preciso instalar as dependências do projeto, rodei pelo eclipse mas no terminal também funciona
mvn clean install -DskipTests


### A integração com o google foi realizada por javascript, talvez por algum motivo pode ser necessário trocar.
A configuração foi realizada nos scripts da index.html e touristDetail.html, bastando trocar a key da url
'https://maps.googleapis.com/maps/api/js?key=AIzaSyCykXMI93ml2s-159-aiOPQh1_dUyoUjsg&libraries=places&callback=initMap'

### Rodar a classe Main em src/main/java
Obs: reparar no uso de ssl para navegação
Abrir no navegador https://localhost:8445

### OpenAPI 3.0 document
## Disponvel em https://localhost:8445/v3/api-docs/

### Postman collection
Observação: não foi feita requisições para o caso de busca por proximidade pois faço a lógica com resultados
de uma busca anterior diretamente no javascript. Demais requisiçes no link a seguir:
https://www.getpostman.com/collections/032a2f165941f03d4206

### Referente a LGPD, restringi recursos diretamente no facebook, para evitar acessos por idade e também de outros países.

### Caso tenham alguma dificuldade em rodar o projeto por gentileza me avisem
Fico à disposição para uma apresentação e/ou sanar qualquer dúvida, obrigado
luizfelipebasile@gmail.com | 42988313783



