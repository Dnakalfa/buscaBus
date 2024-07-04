## Aplicativo confeccionado para estudo da linguagem Kotlin(Android).

<h1>Documentação App BuscaBus API</h1>

# Olá, eu sou o Umberto! 👋

<h2>Activities:</h2>

<h3>MainActivity:</h3>
<p>Activity principal do aplicativo, responsável por iniciar as outras activities, possuindo os seguintes métodos:</p>
<ul>
<li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário.</li>
<li><code>autenticar</code>: Método responsável por autenticar na API. Ele tenta autenticar usando a API OlhoVivoAPI. Se a autenticação for bem-sucedida, exibe um Toast informando "Conectado a API!". Se a autenticação falhar, exibe um Toast informando "Falha ao conectar a API!". Em caso de exceção (por exemplo, falta de internet), exibe um Toast com a mensagem "Sem conexão com a internet".</li>
</ul>

<h3>MapsActivity:</h3>
<p>Activity responsável por exibir um mapa com as marcações de Paradas, Veículos, e Previsão de Veículos, possuindo os seguintes métodos:</p>
<ul>
    <li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário.</li>
    <li><code>carregarDados</code>: Carrega os dados necessários para exibir no mapa, incluindo as paradas e veículos, se os extras da intent contiverem informações sobre uma parada.</li>
    <li><code>recarregar</code>: Recarrega os dados e atualiza o mapa conforme os switches de veículos e paradas.</li>
    <li><code>carregarVeiculos</code>: Carrega a posição dos veículos a partir da API OlhoVivo.</li>
    <li><code>carregarParadas</code>: Carrega as paradas de ônibus a partir da API OlhoVivo.</li>
    <li><code>addClusteredParadas</code>: Adiciona as paradas ao mapa usando clustering para melhor visualização.</li>
    <li><code>addClusteredVeiculos</code>: Adiciona os veículos ao mapa usando clustering para melhor visualização.</li>
    <li><code>addClusteredVeiculosPrevisao</code>: Adiciona as previsões de veículos ao mapa usando clustering para melhor visualização.</li>
    <li><code>buscarPrevisaoParadas</code>: Busca as previsões de veículos para uma determinada parada a partir da API OlhoVivo.</li>
</ul>

<h3>LinhasActivity:</h3>
<p>Activity que permite ao usuário procurar linhas de ônibus utilizando a API OlhoVivo. A atividade exibe uma lista de linhas em um RecyclerView. Possui os seguintes métodos:</p>
<ul>
    <li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário.</li>
    <li><code>carregarRV</code>: Configura o RecyclerView com um adaptador e um layout manager.</li>
    <li><code>popularRV</code>: Popula o RecyclerView com as linhas de ônibus buscadas a partir da API.</li>
    <li><code>buscarLinhas</code>: Busca as linhas de ônibus da API OlhoVivo e trata possíveis exceções.</li>
</ul>

<h3>ParadasActivity:</h3>
<p>Activity que permite ao usuário procurar paradas de ônibus utilizando a API OlhoVivo. A atividade exibe uma lista de paradas em um RecyclerView, possibilitando a visualização dos detalhes de previsão dos veículos e a localização no mapa. Possui os seguintes métodos:</p>
<ul>
    <li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário.</li>
    <li><code>carregarRV</code>: Configura o RecyclerView com um adaptador e um layout manager, além de definir as ações de clique para os itens do RecyclerView.</li>
    <li><code>popularRV</code>: Popula o RecyclerView com as paradas de ônibus buscadas a partir da API.</li>
    <li><code>buscarParadas</code>: Busca as paradas de ônibus da API OlhoVivo, trata possíveis exceções e ordena as paradas pelo nome.</li>
</ul>

<h3>VeiculosPrevisaoActivity:</h3>
<p>Activity iniciada a partir de um clique em algum botão do RecyclerView da ParadasActivity, exibe as previsões de chegada de veículos em uma parada específica. A atividade utiliza a API OlhoVivo para buscar as previsões e apresenta os dados em um RecyclerView. Possui os seguintes métodos:</p>
<ul>
    <li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário e a recuperação dos dados da parada a partir dos extras da Intent.</li>
    <li><code>carregarRV</code>: Configura o RecyclerView com um adaptador e um layout manager.</li>
    <li><code>popularRV</code>: Popula o RecyclerView com as previsões de chegada dos veículos buscadas a partir da API.</li>
    <li><code>buscarPrevisaoParadas</code>: Busca as previsões de chegada dos veículos para uma parada específica da API OlhoVivo, trata possíveis exceções e retorna uma lista de previsões.</li>
</ul>

<h3>CorredoresActivity:</h3>
<p>Activity que exibe uma lista de corredores de transporte público. A atividade utiliza a API OlhoVivo para buscar os dados dos corredores e mostra em um RecyclerView. Possui os seguintes métodos:</p>
<ul>
    <li><code>inicializarComponentes</code>: Método responsável por definir as ações do clique dos botões na interface do usuário.</li>
    <li><code>carregarRV</code>: Configura o RecyclerView com um adaptador e um layout manager.</li>
    <li><code>popularRV</code>: Popula o RecyclerView com os dados dos corredores buscados a partir da API.</li>
    <li><code>buscarCorredores</code>: Busca a lista de corredores da API OlhoVivo, trata possíveis exceções e retorna uma lista de corredores ordenada por código do corredor.</li>
</ul>

<h2>Adapters:</h2>
<ul>
    <li><strong>marker:</strong> Personaliza o conteúdo exibido nos balões de informações dos marcadores no mapa.</li>
    <li><strong>ParadasMapAdapter:</strong> Responsável por personalizar os balões dos marcadores das paradas.</li>
    <li><strong>VeiculosMapAdapter:</strong> Responsável por personalizar os balões dos marcadores dos veículos.</li>
    <li><strong>VeiculosPrevisaoMapAdapter:</strong> Responsável por personalizar os balões dos marcadores de previsões dos veículos.</li>
</ul>

<h2>RecyclerView Adapters:</h2>
<ul>
    <li><strong>ParadaAdapter:</strong> Responsável pelo RecyclerView de Paradas e exibir uma lista de itens do tipo Parada, além de inflar o layout do item.</li>
    <li><strong>LinhaAdapter:</strong> Responsável pelo RecyclerView de Linhas e exibir uma lista de itens do tipo Linha, além de inflar o layout do item.</li>
    <li><strong>VeiculosPrevisaoAdapter:</strong> Responsável pelo RecyclerView de previsão dos veículos e exibir uma lista de itens do tipo VeiculosPrevisao, além de inflar o layout do item.</li>
    <li><strong>CorredorAdapter:</strong> Responsável pelo RecyclerView de corredores e exibir uma lista de itens do tipo Corredor, além de inflar o layout do item.</li>
</ul>

<h2>APIs:</h2>
<ul>
    <li><strong>OlhoVivoAPI:</strong> Define um conjunto de métodos que especificam operações de requisição para interagir com uma API usando a biblioteca Retrofit no Android.</li>
    <li><strong>RetrofitHelper:</strong> Responsável por instanciar um objeto Retrofit, além de conter as constantes de token e URL.</li>
</ul>

<h2>Models:</h2>
<p>Contém as DataClass para representação dos objetos de resposta da API OlhoVivo:</p>
<ul>
    <li>Corredor</li>
    <li>Linha</li>
    <li>LinhasPosicao</li>
    <li>LinhasPrevisao</li>
    <li>Parada</li>
    <li>ParadaPrevisao</li>
    <li>Posicao</li>
    <li>Previsao</li>
    <li>VeiculosPosicao</li>
    <li>VeiculosPrevisao</li>
</ul>

<h2>Renderers:</h2>
<p>Renderers personalizados para marcar objetos no Google Maps usando o ClusterManager:</p>
<ul>
    <li>ParadaRenderer</li>
    <li>VeiculoRender</li>
    <li>VeiculoPrevisaoRenderer</li>
</ul>

<h2>Utilitários:</h2>
<ul>
    <li><strong>BitmapHelper:</strong> Classe utilitária para converter em um Bitmap:</li>
    <li><code>vectorToBitmap</code>: Para transformar recursos vetoriais em BitmapDescriptors, usado ao personalizar a aparência dos marcadores no Google Maps.</li>
</ul>

<h2>Principais Bibliotecas Utilizadas:</h2>
<ul>
    <li>Google Play Services Maps: Integração com os serviços do Google Maps, permitindo a exibição de mapas e a interação com eles em aplicativos Android.</li>
    <li>Android Maps Utils: Fornece utilitários adicionais para trabalhar com o Google Maps Android API, facilitando tarefas comuns como cálculos de distância e clusterização.</li>
    <li>Retrofit: Biblioteca de cliente HTTP para Android que simplifica o consumo de APIs RESTful.</li>
    <li>Converter Gson: Conversor para Retrofit que permite converter automaticamente entre JSON e objetos Kotlin utilizando Gson.</li>
    <li>Gson: Biblioteca de serialização/desserialização JSON para Java e Kotlin.</li>
</ul>

## Desenvolvido apartir de conhecimentos adquiridos em cursos, palestras e mentorias.

Atualmente estou ampliando estes pequenos projetos, que ocorrerão a medida que o aprendizado evoluir.