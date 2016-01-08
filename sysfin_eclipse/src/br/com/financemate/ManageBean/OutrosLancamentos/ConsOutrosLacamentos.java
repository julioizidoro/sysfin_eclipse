/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.OutrosLancamentos;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.ManageBean.contasReceber.ContasReceberMB;
import br.com.financemate.Util.Formatacao;
import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.MovimentoBancoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class ConsOutrosLacamentos implements Serializable{
    
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private List<Movimentobanco> listaOutrosLancamentos;
    private Banco banco;
    private Cliente cliente;
    private List<Cliente> listaClientes;
    private Date dataInical;
    private Date dataFinal;
    private String sql;
    private List<Banco> listaBancos;
    private boolean verCliente=false;
    
    
    
    @PostConstruct
    public void init(){
        listaOutrosLancamentos = new ArrayList<Movimentobanco>();
        gerarListaCliente();
        getUsuarioLogadoBean();
        verificarCliente();
        gerarListaBancos();
    }

    public List<Movimentobanco> getListaOutrosLancamentos() {
        return listaOutrosLancamentos;
    }

    public void setListaOutrosLancamentos(List<Movimentobanco> listaOutrosLancamentos) {
        this.listaOutrosLancamentos = listaOutrosLancamentos;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Date getDataInical() {
        return dataInical;
    }

    public void setDataInical(Date dataInical) {
        this.dataInical = dataInical;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Banco> getListaBancos() {
        return listaBancos;
    }

    public void setListaBancos(List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public boolean isVerCliente() {
        return verCliente;
    }

    public void setVerCliente(boolean verCliente) {
        this.verCliente = verCliente;
    }
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public void gerarPesquisa() {
        if ((banco != null) && (dataInical != null) && (dataFinal != null)
                && (cliente != null)) {
            sql = "Select m from Movimentobanco m where m.banco=" + banco.getIdbanco()
                    + "  and m.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInical)
                    + "'  and m.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                    + "' and m.cliente=" + cliente.getIdcliente();
            sql = sql + " order by m.dataCompensacao";
            MovimentoBancoFacade movimentoBancoFacade = new MovimentoBancoFacade();
            try {
                listaOutrosLancamentos = movimentoBancoFacade.listaMovimento(sql);
                if (listaOutrosLancamentos==null){
                    listaOutrosLancamentos = new ArrayList<Movimentobanco>();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsOutrosLacamentos.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensagem(ex, "Erro Listar Outros Lançamentos", "Erro");
            }
        }else {
            mostrarMensagem(null, "Dados inválidos", "Aviso");
        }
    }
    
    public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaClientes = clienteFacade.listar("");
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
    
    public void verificarCliente(){
        if (usuarioLogadoBean.getUsuario().getCliente()>0){
            ClienteFacade clienteFacade = new ClienteFacade();
            try {
                cliente = clienteFacade.consultar(usuarioLogadoBean.getUsuario().getCliente());
                verCliente = true;
                if(cliente==null){
                    verCliente=false;
                    cliente = new Cliente();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void gerarListaBancos(){
        if (cliente!=null){
            BancoFacade bancoFacade = new BancoFacade();
            String sql = "Select b from banco b where b.cliente.idcliente=" + usuarioLogadoBean.getUsuario().getCliente() + " order by b.nome";
            listaBancos = bancoFacade.listar(sql);
            if (listaBancos!=null){
                listaBancos = new ArrayList<Banco>();
            }
        }else {
            listaBancos = new ArrayList<Banco>();
        }
    }

}
