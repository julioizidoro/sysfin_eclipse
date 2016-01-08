/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean;

import br.com.financemate.ManageBean.cadastro.cliente.ClienteMB;
import br.com.financemate.model.Cliente;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Wolverine
 */

@Named
@SessionScoped
public class MenuMB implements Serializable{
    
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    @Inject ClienteMB clienteMB;

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
    
    public String contasPagar(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getContaspagar()){
            clienteMB.setCliente(new Cliente());
            return "consConPagar";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        
    }
    
    public String contasReceber(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getContasreceber()){
            clienteMB.setCliente(new Cliente());
            return "consConReceber";    
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        
    }
    
    public String outrosLancamento(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getOutroslancamentos()){
            clienteMB.setCliente(new Cliente());
            return "consOutrosLancamentos";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String tipoPlanoContas(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getTipoplanocontas()){
            clienteMB.setCliente(new Cliente());
            return "consTipoPlanoConta";
        }else {
             FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String planoContas(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getPlanocontas()){
           clienteMB.setCliente(new Cliente());
            return "consPlanoConta";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String vendas(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getVendas()){
            clienteMB.setCliente(new Cliente());
            return "consVendas";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String home(){
        clienteMB.setCliente(new Cliente());
        return "principal";
    }
    
    public String relatorioContasPagar(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getRpagar()){
            clienteMB.setCliente(new Cliente());
            return "relContasPagar";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    } 
    
    public String banco(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getBanco()){
            clienteMB.setCliente(new Cliente());
            return "consBanco";   
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    } 
    
    public String cliente(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getCliente()){
            clienteMB.setCliente(new Cliente());
            return "consCliente";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    } 
    public String relatorioVenda(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getVendas()){
            clienteMB.setCliente(new Cliente());
            return "relVendas";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    } 
    public String produto(){
        //if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getProduto()){
            clienteMB.setCliente(new Cliente());
            return "consProduto";
        //}else {
          //  FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
           // FacesContext.getCurrentInstance().addMessage(null, mensagem);
           // return "";
       // }
    } 
    public String usuario(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getUsuario()){
            clienteMB.setCliente(new Cliente());
            return "consTipoUsuario";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    } 
}
