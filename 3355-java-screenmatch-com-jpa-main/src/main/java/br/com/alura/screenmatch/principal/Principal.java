package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7b8ca78e";

    private List<Serie> seriees = new ArrayList<>();

    private SerieRepository repository;

    private List<DadosSerie> _dadosSerie = new ArrayList<>();

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Lista series
                    4 - Dados da series
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listaSeriesBuscadas();
                    break;
                case 4:
                    getDadosSerie();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }


    private void buscarEpisodioPorSerie(){

        listaSeriesBuscadas();
        System.out.println("digite o nome da serie para realizar a buscar ");
        var Nomeserie = leitura.nextLine();

        Optional<Serie> SerieBuscada = seriees.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(Nomeserie.toLowerCase()))
                .findFirst();

//verificando serie
        if(SerieBuscada.isPresent()){
            var SerieEncontrada = SerieBuscada.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= SerieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + SerieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            SerieEncontrada.setEpisodios(episodios);
            repository.save(SerieEncontrada);

        }else {
            System.out.println("Serie não encontrada ");
        }
    }

    private void listaSeriesBuscadas(){
        seriees  = repository.findAll();
        seriees .stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }
}

