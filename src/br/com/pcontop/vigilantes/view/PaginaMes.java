package br.com.pcontop.vigilantes.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.pcontop.vigilantes.R;
import br.com.pcontop.vigilantes.control.*;
import br.com.pcontop.vigilantes.control.comportamento.ComportamentoPaginaMes;
import com.google.inject.Inject;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 17/04/12
 * Time: 21:03
 * Lista todos os dias do mês passado, com a largura de 7 dias. O primeiro dia da semana será o dia da reunião com início
 * de validade mais próxima do final do mês. A conformidade dos totais de EntradaPontos com os LimitePontos da semana serão
 * apresentados com um código de cor. Cada dia poderá ser verde (dentro do limite do dia), amarelo (acima do limite do dia,
 * dentro dos pontos livres) ou vermelho (acima do limite do dia e dos pontos livres).
 *
 * Clicar em qualquer dia irá indicar que a tela da PaginaDia do dia será carregada. É possível navegar para os meses
 * anteriores e posteriores utilizando as setas direcionais no título.
 *
 */
public class PaginaMes extends PaginaSistema {
    private Date mes;
    @InjectView(R.id.pagina_mes_nome) TextView nomeMes;
    @InjectView(R.id.s1d1) TextView s1d1;
    @InjectView(R.id.s1d2) TextView s1d2;
    @InjectView(R.id.s1d3) TextView s1d3;
    @InjectView(R.id.s1d4) TextView s1d4;
    @InjectView(R.id.s1d5) TextView s1d5;
    @InjectView(R.id.s1d6) TextView s1d6;
    @InjectView(R.id.s1d7) TextView s1d7;
    @InjectView(R.id.s2d1) TextView s2d1;
    @InjectView(R.id.s2d2) TextView s2d2;
    @InjectView(R.id.s2d3) TextView s2d3;
    @InjectView(R.id.s2d4) TextView s2d4;
    @InjectView(R.id.s2d5) TextView s2d5;
    @InjectView(R.id.s2d6) TextView s2d6;
    @InjectView(R.id.s2d7) TextView s2d7;
    @InjectView(R.id.s3d1) TextView s3d1;
    @InjectView(R.id.s3d2) TextView s3d2;
    @InjectView(R.id.s3d3) TextView s3d3;
    @InjectView(R.id.s3d4) TextView s3d4;
    @InjectView(R.id.s3d5) TextView s3d5;
    @InjectView(R.id.s3d6) TextView s3d6;
    @InjectView(R.id.s3d7) TextView s3d7;
    @InjectView(R.id.s4d1) TextView s4d1;
    @InjectView(R.id.s4d2) TextView s4d2;
    @InjectView(R.id.s4d3) TextView s4d3;
    @InjectView(R.id.s4d4) TextView s4d4;
    @InjectView(R.id.s4d5) TextView s4d5;
    @InjectView(R.id.s4d6) TextView s4d6;
    @InjectView(R.id.s4d7) TextView s4d7;
    @InjectView(R.id.s5d1) TextView s5d1;
    @InjectView(R.id.s5d2) TextView s5d2;
    @InjectView(R.id.s5d3) TextView s5d3;
    @InjectView(R.id.s5d4) TextView s5d4;
    @InjectView(R.id.s5d5) TextView s5d5;
    @InjectView(R.id.s5d6) TextView s5d6;
    @InjectView(R.id.s5d7) TextView s5d7;
    @InjectView(R.id.s6d1) TextView s6d1;
    @InjectView(R.id.s6d2) TextView s6d2;
    @InjectView(R.id.s6d3) TextView s6d3;
    @InjectView(R.id.s6d4) TextView s6d4;
    @InjectView(R.id.s6d5) TextView s6d5;
    @InjectView(R.id.s6d6) TextView s6d6;
    @InjectView(R.id.s6d7) TextView s6d7;
    @InjectView(R.id.pagina_mes_anterior) ImageView botaoAnterior;
    @InjectView(R.id.pagina_mes_posterior) ImageView botaoPosterior;
    @InjectView(R.id.s1botao) Button s1Botao;
    @InjectView(R.id.s2botao) Button s2Botao;
    @InjectView(R.id.s3botao) Button s3Botao;
    @InjectView(R.id.s4botao) Button s4Botao;
    @InjectView(R.id.s5botao) Button s5Botao;
    @InjectView(R.id.s6botao) Button s6Botao;
    @InjectView(R.id.layout_pagina_mes) LinearLayout mainLayout;
    @InjectView(R.id.ds1) TextView ds1;
    @InjectView(R.id.ds2) TextView ds2;
    @InjectView(R.id.ds3) TextView ds3;
    @InjectView(R.id.ds4) TextView ds4;
    @InjectView(R.id.ds5) TextView ds5;
    @InjectView(R.id.ds6) TextView ds6;
    @InjectView(R.id.ds7) TextView ds7;

    TextView [][] diasCalendario;
    TextView[] cabecalhoCalendario;
    Button[] botoesSemana;
    @Inject ControleCaderno controleCaderno;
    int[] ordemSemana;
    int diaSemanaReuniaoMes;

    @Inject
    ComportamentoPaginaMes comportamentoPaginaMes;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_mes);
        AdicionaPropagandaAdView.adicioneAdView(this, mainLayout);
        getMesDoIntent();
        inicializeComportamentoMes();
        inicializaMes();
        preparaBotoesAnteriorPosterior();
        comportamentoPaginaMes.setOnTouchListenerFor(mainLayout);
    }

    private void inicializeComportamentoMes(){
        comportamentoPaginaMes.inicializePaginaMes(controleCaderno, this, mes);

    }

    private void vaParaDia(Date data){
        comportamentoPaginaMes.vaParaDia(data);
    }

    private void vaParaMesAnterior(){
        comportamentoPaginaMes.vaParaMesAnterior();
    }

    private void vaParaMesSeguinte(){
        comportamentoPaginaMes.vaParaMesSeguinte();
    }

    private void preparaBotoesAnteriorPosterior() {
        botaoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaParaMesAnterior();
            }
        });
        botaoPosterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaParaMesSeguinte();
            }
        });
    }

    private void inicializaMes() {

        SimpleDateFormat formatNomeMes = new SimpleDateFormat("MMMMMMMM yyyy");
        String descricaoMes = formatNomeMes.format(mes);
        descricaoMes = descricaoMes.substring(0,1).toUpperCase() + descricaoMes.substring(1);
        nomeMes.setText(descricaoMes);

        preparaCabecalho();
        preparaBotoesSemana();
        prepareDiasMes();
        definaCabecalho();
        definaDiasMes();

    }

    private void getMesDoIntent() {
        mes = (Date) getIntent().getSerializableExtra("mes");
        if (mes==null){
            mes = new Date();
        }
    }

    private void definaCabecalho(){
        diaSemanaReuniaoMes = controleCaderno.getDiaSemanaReuniaoMes(mes);
        String[] diasDaSemanaAbreviados = getResources().getStringArray(R.array.dias_semana_abreviados);
        ordemSemana = new int[7];
        String diaSemanaAbreviado;
        int diaSemana= diaSemanaReuniaoMes+1;
        diaSemana=diaSemana==8?1:diaSemana;
        for (int i=0;i<7;i++){
            if (diaSemana>7){
                diaSemana=1;
            }
            diaSemanaAbreviado = diasDaSemanaAbreviados[diaSemana-1];
            cabecalhoCalendario[i].setText(diaSemanaAbreviado);
            cabecalhoCalendario[i].setOnClickListener(new BotaoDiaSemanaOnClickListener(new Date(), diaSemana));
            ordemSemana[i]=diaSemana;
            diaSemana++;
        }

    }

    private void preparaBotoesSemana(){
        botoesSemana = new Button[6];
        botoesSemana[0] = s1Botao;
        botoesSemana[1] = s2Botao;
        botoesSemana[2] = s3Botao;
        botoesSemana[3] = s4Botao;
        botoesSemana[4] = s5Botao;
        botoesSemana[5] = s6Botao;
    }

    private void preparaCabecalho(){
       cabecalhoCalendario = new TextView[7];
        cabecalhoCalendario[0] = ds1;
        cabecalhoCalendario[1] = ds2;
        cabecalhoCalendario[2] = ds3;
        cabecalhoCalendario[3] = ds4;
        cabecalhoCalendario[4] = ds5;
        cabecalhoCalendario[5] = ds6;
        cabecalhoCalendario[6] = ds7;
    }

    private void prepareDiasMes() {
        diasCalendario = new TextView[6][7];
        diasCalendario[0][0] = s1d1;
        diasCalendario[0][1] = s1d2;
        diasCalendario[0][2] = s1d3;
        diasCalendario[0][3] = s1d4;
        diasCalendario[0][4] = s1d5;
        diasCalendario[0][5] = s1d6;
        diasCalendario[0][6] = s1d7;
        diasCalendario[1][0] = s2d1;
        diasCalendario[1][1] = s2d2;
        diasCalendario[1][2] = s2d3;
        diasCalendario[1][3] = s2d4;
        diasCalendario[1][4] = s2d5;
        diasCalendario[1][5] = s2d6;
        diasCalendario[1][6] = s2d7;
        diasCalendario[2][0] = s3d1;
        diasCalendario[2][1] = s3d2;
        diasCalendario[2][2] = s3d3;
        diasCalendario[2][3] = s3d4;
        diasCalendario[2][4] = s3d5;
        diasCalendario[2][5] = s3d6;
        diasCalendario[2][6] = s3d7;
        diasCalendario[3][0] = s4d1;
        diasCalendario[3][1] = s4d2;
        diasCalendario[3][2] = s4d3;
        diasCalendario[3][3] = s4d4;
        diasCalendario[3][4] = s4d5;
        diasCalendario[3][5] = s4d6;
        diasCalendario[3][6] = s4d7;
        diasCalendario[4][0] = s5d1;
        diasCalendario[4][1] = s5d2;
        diasCalendario[4][2] = s5d3;
        diasCalendario[4][3] = s5d4;
        diasCalendario[4][4] = s5d5;
        diasCalendario[4][5] = s5d6;
        diasCalendario[4][6] = s5d7;
        diasCalendario[5][0] = s6d1;
        diasCalendario[5][1] = s6d2;
        diasCalendario[5][2] = s6d3;
        diasCalendario[5][3] = s6d4;
        diasCalendario[5][4] = s6d5;
        diasCalendario[5][5] = s6d6;
        diasCalendario[5][6] = s6d7;
    }

    private int getOffestDiasInicioMes(int diaSemanaDiaUm){
        int offset=0;
        if (diaSemanaDiaUm >= diaSemanaReuniaoMes){
            offset = diaSemanaDiaUm - diaSemanaReuniaoMes-1;
        }
        if (diaSemanaReuniaoMes > diaSemanaDiaUm){
            offset = 6 - (diaSemanaReuniaoMes-diaSemanaDiaUm);
        }
        return offset;
    }

    private void setBotaoDesativado(TextView view){
        view.setBackgroundResource(R.drawable.borda_data_desativada);
        view.setTextColor(Color.parseColor("#F9F9F9"));
        view.setEnabled(false);
    }

    private void definaDiasMes() {
        TextView entradaDia;
        final GregorianCalendar calendar = controleCaderno.getCalendar(mes);
        int mesCalendario = calendar.get(Calendar.MONTH);
        calendar.set(GregorianCalendar.DATE, 1);
        int diaSemanaPrimeiroDiaMes = calendar.get(Calendar.DAY_OF_WEEK);
        int mesAtual, diaAtual;
        int offsetDias = -getOffestDiasInicioMes(diaSemanaPrimeiroDiaMes);
        calendar.add(Calendar.DATE,offsetDias);
        for (int contador=0;contador<42;contador++){
            int x = contador%7;
            int y= Math.round(contador/7);
            entradaDia = diasCalendario[y][x];
            diaAtual = calendar.get(Calendar.DATE);
            entradaDia.setText(diaAtual + "");
            mesAtual = calendar.get(Calendar.MONTH);
            Date dataApresentada = calendar.getTime();
            if (x==5){
                definaAcaoBotaoSemanaLimites(y, calendar.getTime());
            }
            if (x==0 && y==5 && mesAtual!=mesCalendario){
                setBotaoDesativado(s6Botao);
                s6Botao.setEnabled(false);
            }
            if (mesAtual!=mesCalendario){
                setBotaoDesativado(entradaDia);
            } else {
                definaLayoutDiaAtivo(entradaDia, dataApresentada);
                entradaDia.setOnClickListener(new BotaoDiaOnClickListener(calendar));
                comportamentoPaginaMes.setOnTouchListenerFor(entradaDia);
                entradaDia.setOnLongClickListener(new BotaoDiaOnLongClickListener(calendar));
            }
            if (ContextoGlobal.debugMode){
                if (controleCaderno.isDataEmPeriodoLimiteAlteravel(dataApresentada)){
                    entradaDia.setText("S " + entradaDia.getText());
                }
            }

            calendar.add(Calendar.DATE,1);
        }
    }

    private void definaLayoutDiaAtivo(TextView entradaDia, Date dataApresentada) {
        int cor = controleCaderno.getCorData(dataApresentada);
        if (cor== Color.GREEN){
            if (controleCaderno.isInicioLimite(dataApresentada)) {
                entradaDia.setBackgroundResource(R.drawable.borda_data_verde_inicio);
            } else if (controleCaderno.isFinalLimite(dataApresentada)){
                entradaDia.setBackgroundResource(R.drawable.borda_data_verde_final);
            } else {
                entradaDia.setBackgroundResource(R.drawable.borda_data_verde_centro);
            }
        }
        if (cor==Color.RED){
            if (controleCaderno.isInicioLimite(dataApresentada)) {
                entradaDia.setBackgroundResource(R.drawable.borda_data_vermelha_inicio);
            } else if (controleCaderno.isFinalLimite(dataApresentada)){
                entradaDia.setBackgroundResource(R.drawable.borda_data_vermelha_final);
            } else {
                entradaDia.setBackgroundResource(R.drawable.borda_data_vermelha_centro);
            }
        }
        if (cor==Color.YELLOW){
            if (controleCaderno.isInicioLimite(dataApresentada)) {
                entradaDia.setBackgroundResource(R.drawable.borda_data_amarela_inicio);
            } else if (controleCaderno.isFinalLimite(dataApresentada)){
                entradaDia.setBackgroundResource(R.drawable.borda_data_amarela_final);
            } else {
                entradaDia.setBackgroundResource(R.drawable.borda_data_amarela_centro);
            }
        }
        if (controleCaderno.isDataAtual(dataApresentada)){
            entradaDia.setTextColor(Color.BLUE);
        }

    }

    private void definaAcaoBotaoSemanaLimites(int semana, Date data) {
        Log.d("PaginaMes", "Definindo acao de botao da semana " + semana);
        Button botao = botoesSemana[semana];
        botao.setOnClickListener(new BotaoSemanaOnClickListener(data));
    }

    private void toast(String mensagem){
        controleCaderno.toast(mensagem);
    }

    public void activate(){
        startActivity(getIntent());
        finish();
        toast("Dados atualizados.");
    }

    private class BotaoDiaOnClickListener implements View.OnClickListener {
        private Date date;

        public BotaoDiaOnClickListener(Calendar calendar){
            this.date = (Date)calendar.getTime().clone();
        }

        @Override
        public void onClick(View view) {
            vaParaDia(date);
        }
    }

    private class BotaoSemanaOnClickListener implements View.OnClickListener {
        Date data;
        public BotaoSemanaOnClickListener(Date data){
            this.data = data;
        }


        @Override
        public void onClick(View view) {
            controleCaderno.busqueLimitePontos(PaginaMes.this, data);
        }
    }

    private class BotaoDiaOnLongClickListener implements View.OnLongClickListener {
        Date data;
        public BotaoDiaOnLongClickListener(Calendar calendar){
            this.data = calendar.getTime();
        }

        @Override
        public boolean onLongClick(View view) {
            controleCaderno.busqueLimitePontos(PaginaMes.this, data);
            return false;
        }
    }

    private class BotaoDiaSemanaOnClickListener implements View.OnClickListener {
        Date data;
        private int diaSemana;

        public BotaoDiaSemanaOnClickListener(Date data, int diaSemana){
            this.data = data;
            this.diaSemana = diaSemana;
        }

        @Override
        public void onClick(View view) {
            controleCaderno.busqueDiaSemanaReuniao(PaginaMes.this, diaSemana);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pagina_mes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId()==R.id.sobre){
            mostreSobre();
        }
        return false;
    }

    private void mostreSobre(){
        controleCaderno.mostreSobre(this);
    }

}
