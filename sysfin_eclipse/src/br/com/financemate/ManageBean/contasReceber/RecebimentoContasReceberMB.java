package br.com.financemate.ManageBean.contasReceber;


import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Greici
 */
@Named
@ViewScoped
public class RecebimentoContasReceberMB implements  Serializable{
    
     @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private List<Cliente> listaCliente;
    private Cliente cliente;
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
        contasReceber.setValorPago(contasReceber.getValorParcela());
         cliente = contasReceber.getCliente();
         banco = contasReceber.getBanco();
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
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

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
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
        contasReceber.setCliente(cliente);
        contasReceber.setUsuario(usuarioLogadoBean.getUsuario());
        contasReceber = contasReceberFacade.salvar(contasReceber);
        RequestContext.getCurrentInstance().closeDialog(contasReceber);
        return "consConReceber";
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        contasReceber.setValorPago(0.0f);
        return "";
    }
    
    public void calcularValoresRecebimento(){
        Float valorReceber = contasReceber.getValorParcela() - contasReceber.getDesagio() + contasReceber.getJuros();
        contasReceber.setValorPago(valorReceber);
    }

    
}
