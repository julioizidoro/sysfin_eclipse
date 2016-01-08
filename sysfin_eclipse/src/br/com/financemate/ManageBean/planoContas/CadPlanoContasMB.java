/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.planoContas;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Tipoplanocontas;
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
public class CadPlanoContasMB implements Serializable {

    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private Planocontas planocontas;
    private List<Tipoplanocontas> listarTipoPlanoContas;

    @PostConstruct
    public void init() {
         FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        planocontas = (Planocontas) session.getAttribute("planocontas");
        session.removeAttribute("planocontas");
        if (planocontas == null) {
            planocontas = new Planocontas();
        }

    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }
    
    
    
     public void gerarlistaTipoPlanoContas() {
        TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
        try {
            listarTipoPlanoContas = tipoPlanoContasFacede.listar();
        } catch (SQLException ex) {
            Logger.getLogger(PlanoContasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }
    }

    
    public String salvarPlanoContas() {
         PlanoContasFacade planoContasFacade = new PlanoContasFacade();
        try {
            planocontas = planoContasFacade.salvar(planocontas);
            RequestContext.getCurrentInstance().closeDialog(planocontas);
        } catch (SQLException ex) {
            Logger.getLogger(CadPlanoContasMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro  Cadastro Plano de Contas", "Erro");

        }
        return "";
    }
    
    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
}
