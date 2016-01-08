/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.banco;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.BancoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import java.io.Serializable;
import java.sql.SQLException;
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
public class CadBancoMB implements Serializable {

    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private Banco banco;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("banco");
        if (banco == null) {
            banco = new Banco();
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            banco.setCliente(cliente);
            session.removeAttribute("cliente");
        }
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void salvar() {
        BancoFacade bancoFacade = new BancoFacade();
        try {
            banco = bancoFacade.salvar(banco);
            RequestContext.getCurrentInstance().closeDialog(banco);
        } catch (SQLException ex) {
            Logger.getLogger(CadBancoMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao Cadastrar Banco", "Erro");

        }
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
}
