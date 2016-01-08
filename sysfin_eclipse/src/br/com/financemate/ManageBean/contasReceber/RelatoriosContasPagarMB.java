/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.contasReceber;


import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.ManageBean.cadastro.cliente.ClienteMB;
import br.com.financemate.Util.Formatacao;
import br.com.financemate.Util.GerarRelatorio;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.FluxoCaixaFacade;
import br.com.financemate.facade.MovimentoBancoFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Fluxocaixa;
import br.com.financemate.model.Movimentobanco;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Wolverine
 */

@Named("RelatoriosContasPagarMB")
@SessionScoped
public class RelatoriosContasPagarMB implements Serializable{
    
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    @Inject
    private ClienteMB clienteMB;
    private Date dataInicio;
    private Date dataTermino;
    private String competencia;
    private String tipoRelatorio;
    private List<Contaspagar> listaContasPagar;
    private String titulo;
    private String periodo;
    private List<Fluxocaixa> listaFluxoCaixa;
    private List<Movimentobanco> listaMovimentoBanco;

    public RelatoriosContasPagarMB() {
        dataInicio = new Date();
        dataTermino = new Date();
    }
    
    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public ClienteMB getClienteMB() {
        return clienteMB;
    }

    public void setClienteMB(ClienteMB clienteMB) {
        this.clienteMB = clienteMB;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<Fluxocaixa> getListaFluxoCaixa() {
        return listaFluxoCaixa;
    }

    public void setListaFluxoCaixa(List<Fluxocaixa> listaFluxoCaixa) {
        this.listaFluxoCaixa = listaFluxoCaixa;
    }

    public List<Movimentobanco> getListaMovimentoBanco() {
        return listaMovimentoBanco;
    }

    public void setListaMovimentoBanco(List<Movimentobanco> listaMovimentoBanco) {
        this.listaMovimentoBanco = listaMovimentoBanco;
    }
    
    public String selecionarUnidade() {
        clienteMB.setPagina("relContasPagar");
        return "selecionarUnidade";
    }
    
    public String cancelar(){
        setTitulo("");
        setCompetencia("");
        setDataInicio(new Date());
        setDataTermino(new Date());
        setPeriodo("");
        setTipoRelatorio("Selecione");
        clienteMB = new ClienteMB();
        return "principal";
    }
    
    
    
    //Relatorios Excel
    
    public String verificarTipoRelatorioExcel(){
        String pagina="";
        if (tipoRelatorio.equalsIgnoreCase("contasVencidas")){
             pagina = gerarExcelContasPagarVencidas();
        }else if (tipoRelatorio.equalsIgnoreCase("fluxoCaixa")){
            pagina = gerarExcelFluxoCaixa();
        }else if (tipoRelatorio.equalsIgnoreCase("pagamento")){
            pagina = gerarExcelPagamentos();
        }
        return pagina;
    }
    
    public String gerarExcelContasPagarVencidas() {
        listaContasPagar = null;
        titulo = "Contas a Pagar Vencidas";
        this.periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        ContasPagarFacade contasPagarController = new ContasPagarFacade();
        String sql = "Select c from Contaspagar c where ";
        if ((dataInicio != null) && (dataTermino != null)) {
            sql = sql + "c.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and c.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' and ";
        }
        sql = sql + " c.cliente.idcliente=" + clienteMB.getCliente().getIdcliente() + " and c.contaPaga='N'";
        sql = sql + " order by c.dataVencimento";
        try {
            listaContasPagar = contasPagarController.listar(sql);
            if (listaContasPagar != null) {
                return "exportRelatorioContas";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public String gerarExcelFluxoCaixa(){
        listaFluxoCaixa = null;
        titulo = "Fluxo de Caixa";
        this.periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicio) 
            + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        FluxoCaixaFacade fluxoCaixaFacade = new FluxoCaixaFacade();
        // Rever depois com o metodo consultar
        //listaFluxoCaixa =fluxoCaixaFacade.consultar(clienteMB.getCliente().getIdcliente());
        if (listaFluxoCaixa!=null){
            return "exportFluxoCaixa";
        }
        return null;
    }
    
    public String gerarExcelPagamentos() {
        listaMovimentoBanco = null;
        titulo = "Relatório de Pagamentos";
        this.periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicio)
                + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        MovimentoBancoFacade movimentoBancoFacade = new MovimentoBancoFacade();
        String sql = "Select m from Movimentobanco m where ";
        if ((dataInicio != null) && (dataTermino != null)) {
            sql = sql + "m.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInicio)
                    + "' and m.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' and ";
        } else {
            sql = sql + "m.compentencia='" + competencia + "' and ";
        }
        sql = sql + "m.cliente.idcliente=" + clienteMB.getCliente().getIdcliente();
        sql = sql + " and m.planocontas.idplanoContas<>" + clienteMB.getCliente().getContaRecebimento();
        sql = sql + " and m.planocontas.idplanoContas<>" + clienteMB.getCliente().getContaReceita();
        sql = sql + " Group by m.planocontas.idplanoContas, m.dataCompensacao, m.descricao, m.valorEntrada, m.valorSaida, m.planocontas.descricao, m.banco.nome, m.cliente.nomeFantasia,  m.compentencia ";
        sql = sql + " Order by m.planocontas.idplanoContas, m.dataCompensacao, m.descricao, m.valorEntrada, m.valorSaida, m.planocontas.descricao, m.banco.nome, m.cliente.nomeFantasia,  m.compentencia ";
        try {
            listaMovimentoBanco = movimentoBancoFacade.listaMovimento(sql);
            if (listaMovimentoBanco != null) {
                return "exportPagamentos";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    
    //Relatorios Jasper
    
    
    public String verificarTipoRelatorioJasper() {
        String pagina = "";
        String msg = validarDadosRelatorios();
        if (msg.length() < 5) {
            if (tipoRelatorio.equalsIgnoreCase("contasVencidas")) {
                relatorioContasPagarVencidas();
            } else if (tipoRelatorio.equalsIgnoreCase("fluxoCaixa")) {
                pagina = gerarExcelFluxoCaixa();
            } else if (tipoRelatorio.equalsIgnoreCase("pagamento")) {
                relatorioContasPagarPagamentos();
            }
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", msg);
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
        }
        return pagina;
    }
    
    public String validarDadosRelatorios(){
        String msg="";
        if (clienteMB.getCliente()==null){
            msg = msg + "Selecione um Cliente\r\n";
        }
        if (tipoRelatorio.equalsIgnoreCase("sn")){
            msg = msg + "Selecione tipo de relatório\r\n";
        }
        return msg;
    }
    
    public void relatorioContasPagarVencidas()   {
        String sql = "Select distinct contasPagar.dataVencimento, contasPagar.descricao, contasPagar.valor, contasPagar.dataAgendamento,cliente.nomeFantasia, contasPagar.fornecedor, contasPagar.numeroDocumento";
        sql = sql + " From ";
        sql = sql + " contasPagar join cliente on contasPagar.cliente_idcliente = cliente.idcliente ";
        sql = sql +" where ";
        if ((dataInicio!=null) && (dataTermino!=null)){
            sql = sql + "contasPagar.dataVencimento>='" +  Formatacao.ConvercaoDataSql(dataInicio) +
                    "' and contasPagar.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' and ";
        }
	sql = sql + " contasPagar.cliente_idcliente=" + clienteMB.getCliente().getIdcliente() + " and contasPagar.contaPaga='N'";
        sql = sql + " order by contasPagar.dataVencimento";
        String caminhoRelatorio = "/resources/report/contaspagar/reportPagamentoVencidas.jasper";  
        Map parameters = new HashMap();
        String periodo = null;
        periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicio) 
            + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        parameters.put("periodo", periodo);
        parameters.put("sql",sql);
        String nomeArquivo = "ContasPagarVencidas";
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeArquivo, null);
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }
    
    public void relatorioContasPagarPagamentos()   {
        String sql = "Select distinct movimentobanco.dataCompensacao, movimentobanco.descricao, ";
        sql = sql + "movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, ";
        sql = sql + "planocontas.descricao as planoContas, movimentobanco.planoContas_idplanoContas as idPlanoContas, movimentobanco.compentencia ";
        sql = sql + "from movimentobanco join cliente on movimentobanco.cliente_idcliente = cliente.idcliente ";
        sql = sql + "join banco on movimentobanco.banco_idbanco = banco.idbanco ";
        sql = sql + "join planocontas on movimentobanco.planoContas_idplanoContas = planocontas.idplanoContas ";
        sql = sql +"where ";
        if ((dataInicio!=null) && (dataTermino!=null)){
            sql = sql + "movimentobanco.dataCompensacao>='" +  Formatacao.ConvercaoDataSql(dataInicio) +
                    "' and movimentobanco.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' and ";
       }else {
            sql = sql + "movimentobanco.compentencia='" + competencia + "' and ";
        }
	sql = sql + "cliente.idcliente=" + clienteMB.getCliente().getIdcliente();
        sql = sql + " and movimentobanco.planoContas_idplanoContas<>" + clienteMB.getCliente().getContaRecebimento();
        sql = sql + " and movimentobanco.planoContas_idplanoContas<>" + clienteMB.getCliente().getContaReceita();
        sql = sql + " Group by movimentobanco.planoContas_idplanoContas, movimentobanco.dataCompensacao, movimentobanco.descricao, movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  movimentobanco.compentencia ";
        sql = sql + " order by movimentobanco.planoContas_idplanoContas, movimentobanco.dataCompensacao, movimentobanco.descricao, movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  movimentobanco.compentencia";
        String caminhoRelatorio = "/resources/report/contaspagar/reportPagamentos01.jasper";  
        Map parameters = new HashMap();
        String periodo = null;
        periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicio) 
            + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        parameters.put("periodo", periodo);
        parameters.put("sql",sql);
        String nomeArquivo = "ContasPagarPagamentos";
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeArquivo, null);
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatoriosContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }
    
    
}
