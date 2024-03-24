# Read Me First
The following was discovered as part of building this project:

# Como Executar

- Clonar repositório git:
```
git clone git@github.com:felipebortoli/desafio-back-votos.git
```
- Construir o projeto:
```
./mvn clean install
```
- Na pasta do projeto executar via docker:
```
docker compose up -d

Entrar em qualquer client SQL na conexão localhost do banco postgresSQL criado pelo docker
localhost:5432
username:postgres
password: postgres
criar database chamado "assembleia_DB"
```
- Executar(necessario ter o banco de dados postgreSQL rodando com o databse assembleia_DB criado):
```
java -jar ./target/sistema-votacao-0.0.1-SNAPSHOT.jar
```

- End-point Criar Pauta
```
http://localhost:8080/api/pauta/create?version=1
Exemplo body:
{
	"nome": "votacao um",
    "associados":[
        {"nome":"Felipe","cpf":"07788490903"},
        {"nome":"Joao","cpf":"99209122046"},
        {"nome":"Carlos","cpf":"09313420074"},
        {"nome":"Antonio","cpf":"02114798020"},
        {"nome":"Henrique","cpf":"13755924080"}
    ]

}
```
- End-point Abrir Sessão
```
http://localhost:8080/api/sessao/abrir?version=1
Exemplo body:
{
	"duracao": 3,
    "nomePauta" : "votacao um"
}
```
- End-point voto
```
http://localhost:8080/api/assembleia/voto?version=1
Exemplo body:
{
	"nomePauta": "votacao um",
    "cpf": "99209122046",
    "voto": "SIM"
}
```
- Cadastro via kafka 
```
producer: 
docker exec -it kafka kafka-console-producer.sh --topic voto-api-consumer --bootstrap-server localhost:9092

exemplo Json: {"nomePauta":"votacao um", "cpf":"07788490903", "voto":"SIM"}

consumer: 
docker exec -it kafka kafka-console-consumer.sh --topic voto-api-producer --bootstrap-server localhost:9092
```