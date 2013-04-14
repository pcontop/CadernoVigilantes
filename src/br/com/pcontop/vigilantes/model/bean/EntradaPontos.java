package br.com.pcontop.vigilantes.model.bean;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Paulo
 * Date: 26/03/12
 * Time: 23:19
 * Objeto contendo as informações de uma entrada unitária de pontos. Na entrada, há o nome do alimento,
 * a quantidade de unidades, a pontuação por unidade e a data da inserção.
 *
 */
public class EntradaPontos {
    private long id=-1;
    private String nome;
    private Date dataInsercao;
    private double pontos;
    private double quantidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPontosMultiplicados(){
        return quantidade * pontos;
    }

    public void definaDataInsercaoInicial(Date dataInicial) {
        if (getDataInsercao()==null){
            setDataInsercao(dataInicial);
        }
    }
}
