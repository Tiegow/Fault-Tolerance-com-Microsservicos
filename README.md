# Fault Tolerance com Microsservicos
Simulação de um sistema de compras de passagens aereas, estruturado em microsserviços para aplicação de padrões e técnicas de tolerância a falhas.

## Containers
A aplicação se divide em 4 serviços: IMDTravel, AirlinesHub, Exchange e Fidelity. Para simular uma arquitetura em microsserviços rodando em máquinas diferentes, cada sub-sistema será executado em containers dockers diferentes. Os serviços fazem requisições entre si usando REST.

## Como executar

### Pré-requisitos
Certifique-se de ter instalado:
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

Na raiz do projeto, execute o comando abaixo para construir e iniciar todos os microserviços:

```bash
docker compose up --build
```

Para pará-los execute:

```bash
docker compose down
```

## IMDTravel

## AirlinesHub

## Exchange
Serviço que retorna um número real positivo que indica uma taxa de conversão da de dólar para real. O valor é gerado aleatoriamente, onde 1 dólar pode variar entre 5 e 6 reais.

## Fidelity
