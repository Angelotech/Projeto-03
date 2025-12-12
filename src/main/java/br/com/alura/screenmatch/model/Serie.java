package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

import static java.lang.String.valueOf;

public class Serie {

   private String titulo;
   private Integer totalTemporadas;
   private String avaliacao;
   private Categoria genero;
   private String atores;
   private String poster;
   private String sinopse;

public Serie( DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = String.valueOf(OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0)); /*transformação de String para douuble*/
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse().split(",")[0].trim());
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