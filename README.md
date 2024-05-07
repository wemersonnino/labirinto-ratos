# 🌟 Maze Simulator

Bem-vindo ao Maze Simulator! Este projeto é um simulador interativo onde ratos 🐭 tentam encontrar um pedaço de queijo 🧀 em um labirinto complexo. Aprenda sobre algoritmos de busca e veja-os em ação neste divertido e educativo simulador.

## 🚀 Funcionalidades
Geração Automática de Labirintos: Cada sessão começa com um novo labirinto, gerado aleatoriamente.
Simulação de Busca: Ratos virtuais usam o algoritmo de busca em largura (BFS) para encontrar o caminho até o queijo.
Interface Gráfica: Visualize todo o processo em uma interface gráfica amigável.
## 📋 Pré-requisitos
> Antes de iniciar, certifique-se de ter instalado:

- Java JDK 11 ou superior
- IDE com suporte para Java (recomendado: IntelliJ IDEA ou Eclipse)
## 🛠️ Instalação
> Clone o repositório para sua máquina local usando:

```bash
Copy code
git clone https://github.com/seuusuario/maze-simulator.git
```
## ⚙️ Como Usar
> Para iniciar o simulador, navegue até o diretório do projeto e execute:

```bash
Copy code
java -jar MazeSimulator.jar
```
## 🧠 Algoritmos Utilizados
- Busca em Largura (BFS): Garante que encontraremos o caminho mais curto para o queijo, se houver um caminho disponível.
- Geração de Labirintos: Utiliza uma adaptação do algoritmo de Prim para criar labirintos complexos e solucionáveis.
## 📊 Melhorias Futuras
> - Implementação de outros algoritmos de busca como A* ou Dijkstra.
> - Adição de níveis de dificuldade com labirintos maiores ou múltiplos ratos.
> - Recursos de pausa e reinício durante a simulação.
> -  Implementação do Deep Q Learning (DQL): A aplicação de DQL pode trazer melhorias significativas no comportamento dos ratos virtuais ao permitir que aprendam a maximizar uma recompensa cumulativa através do tempo. 
> -  Veja como isso pode beneficiar o projeto:
>     1) Aprendizado por Reforço: O DQL permite que os agentes aprendam de forma mais eficiente a encontrar o queijo, evitando caminhos subótimos.
>     2) Adaptação a Ambientes Complexos: O algoritmo é robusto o suficiente para lidar com ambientes dinâmicos e complexos, onde decisões são tomadas com base em percepções limitadas.
>     3) Decisões Baseadas em Estado: O rato poderia aprender a tomar decisões considerando o estado completo do ambiente, o que inclui a localização do queijo e a configuração de obstáculos e penalidades.
## 🤝 Contribuições
### Contribuições são sempre bem-vindas! Para contribuir:

## Fork o projeto
- Crie sua Feature Branch (```git checkout -b feature/AmazingFeature```)
- Faça commit de suas mudanças (```git commit -m 'Add some AmazingFeature'```)
- Faça push para a Branch (```git push origin feature/AmazingFeature```)
- Abra um Pull Request
## 📝 Licença
Distribuído sob a licença MIT. Veja LICENSE para mais informações.

## 📞 Contato
Wemerson Nino – @wemersonnino

### Link do Projeto: https://github.com/wemersonnino/labirinto-ratos
