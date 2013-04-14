package br.com.pcontop.vigilantes.model.bean;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Paulo
 * Date: 02/04/12
 * Time: 21:21
 * Encapsula as informações de limite de dados de uma dada semana. Contém a informação do limite diário, e dos pontos
 * livres, que podem ser consumidos pela semana. A semana é definida como o intervalo entre as datas de reunião, e
 * portanto não começa necessariamente no domingo.
 * Em alguns casos, a semana pode até ter menos de 7 dias, se o dia da reunião for antecipada, a semana durará até a próxima
 * reunião.
 */
public class LimitePontos {
    private Date dataInicio;
    private Date dataFim;
    private int pontosDia = -1;
    private int pontosLivreSemana;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getPontosDia() {
        return pontosDia;
    }

    public void setPontosDia(int pontosDia) {
        this.pontosDia = pontosDia;
    }

    public int getPontosLivreSemana() {
        return pontosLivreSemana;
    }

    public void setPontosLivreSemana(int pontosLivreSemana) {
        this.pontosLivreSemana = pontosLivreSemana;
    }

    public String toString(){
        return "De " + getDataInicio() + " a " + getDataFim() + " - Limite Pontos dia/Semana: " + getPontosDia() + ", Limite Pontos Livres:" + getPontosLivreSemana();
    }


}
