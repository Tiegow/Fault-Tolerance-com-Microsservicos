# Fault Tolerance com Microsservicos
Simulação de um sistema de compras de passagens aereas, estruturado em microsserviços para aplicação de padrões e técnicas de tolerância a falhas.

# Containers
A aplicação se divide em 4 serviços: IMDTravel, AirlinesHub, Exchange e Fidelity. Para simular uma arquitetura em microsserviços rodando em máquinas diferentes, cada sub-sistema será executado em containers dockers diferentes. Os serviços fazem requisições entre si usando REST.

## IMDTravel

## AirlinesHub

## Exchange
Serviço que retorna um número real positivo que indica uma taxa de conversão da de dólar para real. O valor é gerado aleatoriamente, onde 1 dólar pode variar entre 5 e 6 reais.

### Especs.:
- Aplicação Java (Spring);
- SO do container: Linux/amd64;
- Tamanho da imagem: 903 MB;

### Rodando o Container:
Baixe a imagem do Docker Hub:
```powershell
docker pull tiegorocha/exchange-rest:v2
```

Execute o container:
```powershell
docker run -d -p 8080:8080 tiegorocha/exchange-rest:v2
```

## Fidelity