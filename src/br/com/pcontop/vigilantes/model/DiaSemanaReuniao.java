package br.com.pcontop.vigilantes.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 25/04/12
 * Time: 22:36
 * Objeto contendo a informação do Dia de Reunião, que pode variar de acordo com o tempo conforme definido pelo usuário.
 */
public class DiaSemanaReuniao implements Serializable {
    private Date inicioValidade;
    private int diaSemana;

    public Date getInicioValidade() {
        return inicioValidade;
    }

    public void setInicioValidade(Date inicioValidade) {
        this.inicioValidade = inicioValidade;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public String toString() {
        return "DiaSemanaReuniao{" +
                "inicioValidade=" + inicioValidade +
                ", diaSemana=" + diaSemana +
                '}';
    }
}
