package br.com.alura.screenmatch.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @Column(unique = true)
   private String titulo;
   private Integer totalTemporadas;
   private String avaliacao;
   @Enumerated(EnumType.STRING)
   private Categoria genero;
   private String atores;
   private String poster;
   private String sinopse;

   @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private List<Episodio> episodios = new ArrayList<>();

   public Serie (){
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

public Serie( DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = String.valueOf(OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0)); /*transformação de String para douuble*/
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse().split(",")[0].trim());
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;

    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;

    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }



    @Override
    public String toString() {
        return  " titulo: " + titulo + '\'' +
                ", genero: " + genero + '\'' +
                ", avaliacao: " + avaliacao + '\'' +
                ", atores: " + atores + '\'' +
                ", sinopse: " + sinopse + '\'' +
                ", poster: " + poster ;

    }
}