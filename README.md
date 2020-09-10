
  
  
## Desafio Órama  
  
A idéia do projeto foi desenvolver um aplicativo que utilize a API disponibilizada pela Órama para retornar a lista de fundos disponíveis no ambiente de testes.  
Dentre outros requisitos o aplicativo precisava ser feito em Java, além de contar com detalhamento dos itens, e persistência de dados para uso offline.  
  
## Como utilizar  
  
A forma mais fácil para rodar a aplicação, é clonar/download/forkar esse repositório e com Android Studio instalar o aplicativo em um emulador ou aparelho físico.  
  
**Atenção para rodar os testes instrumentados em AndroidTest é necessário remover o comentário da seguinte linha no build.gradle(modulo)** 
   
 ```
 debugImplementation "androidx.fragment:fragment-testing:$fragment_version_debug"  
````  
**Ao inserir a dependência acima a variant Debug, a que roda em emulador/devices de teste por padrão, vai apresentar um bug visual na animação de transição.**  

*A versão release não é afetada. A versão mais estável da biblioteca, lançada em agosto, apresentou incompatibilidade com java*
  
  
## O que foi adicionado ao desafio  
  
Além dos requisitos mínimos, os seguintes pontos foram adicionados.  
  
- O aplicativo fornece opção de filtro de acordo com rentabilidade, data, nome, e data de início do fundo.  
- A rotação do aparelho não gera perda de dados.  
- Animação sharedElement transition para uma navegação mais fluída entre telas.  
- Utilização de dagger2 para os testes instrumentados facilita o setup de testes, principalmente para testes de UI dedicados a cada fragmento, mas também facilita injeção de dependência/retornos para a tela, permitindo testes totalmente independentes de conexão com o servidor.  
  
  
## Bibliotecas de destaque  
  
-   **Jetpack Navigations**  
-   **ViewModel**  
-   **LiveData**  
-   **Room**  
-   **Dagger2**  
-   **Retrofit2**  
-   **Espresso**  
-   **Mockito**  
  
  
## O que ficou a desejar  
  
- O design ficou usável mas acredito que pode ser aprimorado.  
- O tratamento de erros pode ser aprimorado em termos de UI.  
- A primeira transição (animação) para o fragmento de detalhes está deixando um rastro no emulador.