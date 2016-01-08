/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.cadastro.TipoPlanoConta;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Tipoplanocontas;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Kamila
 */
@Named
@SessionScoped
public class TipoPlanoContasMB implements Serializable {

    private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoplanocontas;
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    
    @PostConstruct
    public void init() {
        gerarListaTipoPlanoConta();
        
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas(){
        if(listarTipoPlanoContas==null){
            gerarListaTipoPlanoConta();
        }
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public Tipoplanocontas getTipoplanocontas() {
        return tipoplanocontas;
    }

    public void setTipoplanocontas(Tipoplanocontas tipoplanocontas) {
        this.tipoplanocontas = tipoplanocontas;
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }
    
    

    public String salvarTipoPlanoContas() throws SQLException {
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()){
            TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
            tipoPlanoContasFacede.salvar(tipoplanocontas);
            gerarListaTipoPlanoConta();
            return "consTipoPlanoConta";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        
    }

    

    public String novo() {
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()) {
            tipoplanocontas = new Tipoplanocontas();
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 300);
            RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta");
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";

    }

    public String editar(Tipoplanocontas tipoplanocontas) {
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getAtipoplanocontas()){
             if (tipoplanocontas != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("tipoplanocontas", tipoplanocontas);
                RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta");
            }
            return  "";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
         
    }

    public void gerarListaTipoPlanoConta() {
        TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
        try {
            listarTipoPlanoContas = tipoPlanoContasFacede.listar();
            if (listarTipoPlanoContas == null) {
                listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TipoPlanoContasMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao gerar a lista de tipo de plano de contas", "Erro");
        }

    }
    
    public void retornoDialogNovo(SelectEvent event){
        Tipoplanocontas tipoplanocontas = (Tipoplanocontas) event.getObject();
       listarTipoPlanoContas.add(tipoplanocontas);
   }
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
}
