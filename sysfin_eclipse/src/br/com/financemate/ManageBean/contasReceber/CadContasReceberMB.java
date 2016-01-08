/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.contasReceber;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Planocontas;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Greici
 */
@Named
@ViewScoped
public class CadContasReceberMB implements Serializable{
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private List<Planocontas> listaPlanoContas;
    private Planocontas planoContas;
    private List<Banco> listaBanco;
    private Banco banco;
    private Contasreceber contasReceber;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contareceber");
        session.removeAttribute("contareceber");
        gerarListaCliente();
        gerarListaBanco();
        gerarListaPlanoContas();
        if (contasReceber == null){
            contasReceber = new Contasreceber();
        }else{
            cliente = contasReceber.getCliente();
            planoContas = contasReceber.getPlanocontas();
            banco = contasReceber.getBanco();
        }
            
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public List<Planocontas> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public Planocontas getPlanoContas() {
        return planoContas;
    }

    public void setPlanoContas(Planocontas planoContas) {
        this.planoContas = planoContas;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    
    
     public void gerarListaPlanoContas() {
        PlanoContasFacade planoContasFacade = new PlanoContasFacade();
        try {
            listaPlanoContas = planoContasFacade.listar();
        } catch (Exception ex) {
            Logger.getLogger(CadContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public void gerarListaBanco(){
        if (cliente!=null){
            BancoFacade bancoFacade = new BancoFacade();
            String sql = "Select b from banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoFacade.listar(sql);
            if (listaBanco!=null){
                listaBanco = new ArrayList<Banco>();
            }
        }else {
            listaBanco = new ArrayList<Banco>();
        }
    } 
    
    public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
     
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public String salvar(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        contasReceber.setBanco(banco);
        contasReceber.setPlanocontas(planoContas);
        contasReceber.setCliente(cliente);
        contasReceber.setValorPago(0.0f);
        contasReceber.setDesagio(0.0f);
        contasReceber.setJuros(0.0f);
        contasReceber.setUsuario(usuarioLogadoBean.getUsuario());
        contasReceber = contasReceberFacade.salvar(contasReceber);
        RequestContext.getCurrentInstance().closeDialog(contasReceber);
        return "consConReceber";
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
   

    
}
