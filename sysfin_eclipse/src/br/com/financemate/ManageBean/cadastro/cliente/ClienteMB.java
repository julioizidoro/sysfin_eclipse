/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.cadastro.cliente;


import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Cliente;
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


@Named("ClienteMB")
@SessionScoped
public class ClienteMB implements Serializable{
    
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private Cliente cliente;
    private String nomeCliente;
    private List<Cliente> listaClientes;
    private String pagina;
    private String idTipoPlanoConta="0";
    private List<Tipoplanocontas> listarTipoPlanoContas;
    
    @PostConstruct
    public void init() {
        cliente = new Cliente();
        getListaClientes();
    }
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (usuarioLogadoBean.getUsuario().getCliente()>0){
            this.cliente = usuarioLogadoBean.getCliente();
        }else this.cliente = cliente;   
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public List<Cliente> getListaClientes() {
       if (listaClientes==null){
            gerarListaClientes();
        }
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getIdTipoPlanoConta() {
        return idTipoPlanoConta;
    }

    public void setIdTipoPlanoConta(String idTipoPlanoConta) {
        this.idTipoPlanoConta = idTipoPlanoConta;
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }
    
    public void listarTipoPlanoContas() throws SQLException{
        TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
        listarTipoPlanoContas = tipoPlanoContasFacede.listar();
        if (listarTipoPlanoContas==null){
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }
    }

    public void gerarListaClientes() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            if (nomeCliente == null) {
                nomeCliente = "";
            }
            listaClientes = clienteFacade.listar(nomeCliente);
            if (listaClientes == null) {
                listaClientes = new ArrayList<Cliente>();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage mensagem = new FacesMessage("Erro! " + ex);
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
             
        }
        

    }
    
    public String pesquisarNome(){
        gerarListaClientes();
        return "consCliente";
    
    }  
    
    public String selecionarCliente(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        int idCliente=  Integer.parseInt(params.get("id_cliente"));
        cliente = listaClientes.get(idCliente);
        return pagina;
    }
   
    public String cancelar(){
        return "consCliente";
    }
    
    
    public String consultarTipoPlanoContas() throws SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        int idCliente = Integer.parseInt(params.get("idCliente"));
        if (idCliente > 0) {
            ClienteFacade clienteFacade = new ClienteFacade();
            cliente = clienteFacade.consultar(idCliente);
            if (cliente != null) {
                return "cadCliente";
            }
        }
        return null;
    }
    
    public String editar(Cliente cliente){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getAcliente()) {
            if (cliente != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("cliente", cliente);
                RequestContext.getCurrentInstance().openDialog("cadCliente");
            }
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";
    }
    
    public String novo() {
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getIcliente()) {
            try {
                listarTipoPlanoContas();
                cliente = new Cliente();
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("contentWidth", 500);
                RequestContext.getCurrentInstance().openDialog("cadCliente");
                return "";
            } catch (SQLException ex) {
                Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        } 
        return null;

    }
    
    public void retornoDialogNovo(SelectEvent event){
       Cliente cliente = (Cliente) event.getObject();
       listaClientes.add(cliente);
   }
}
    

