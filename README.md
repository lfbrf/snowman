# snowman

### Tecnologias usadas:
- Java, Spring, HTML, CSS, bootstrap, Jquery, SonarLint, Junit, mockito e maven.
- Eclipse e github;
- Integrações com Facebook (oauth) e Google API.

## Instruçes para deploy e para rodar
### No código está a key para usar SSL, talvez seja necessário gerar uma nova antes de rodar o projeto. 
Adicionar em src/main/resources

.keytool -genkeypair -alias snowman -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore snowman.p12 -validity 3650

### Será necessário configurar as informaçes referente à API do facebook e do banco de dados no arquivo yml
Adicionei um exemplo em: src/main/resources/applicattion_old.yml


### É preciso instalar as dependências do projeto, rodei pelo eclipse mas not erminal também funciona
mvn clean install


### Rodar a classe Main em src/main/java

### OpenAPI 3.0 document
## Disponvel em https://localhost:8445/v3/api-docs/

### Referente a LGPD, restringi recursos diretamente no facebook, para evitar acessos por idade e também de outros países.


