package br.com.financemate.ManageBean.cadastro;


import br.com.financemate.ManageBean.cadastro.cliente.ClienteMB;
import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.ProdutoFacade;
import br.com.financemate.model.Produto;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named("ProdutoMB")
@SessionScoped
public class ProdutoMB implements Serializable {
    @Inject private ClienteMB clienteMB;
    private Produto produto;
    private List<Produto> listaProduto;
    private UsuarioLogadoBean usuarioLogadoBean;

    public ClienteMB getClienteMB() {
        return clienteMB;
    }

    public void setClienteMB(ClienteMB clienteMB) {
        this.clienteMB = clienteMB;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListaProduto() {
        if (listaProduto==null){
            gerarListaProduto();
        }
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }
    
    
    
    public String cancelar(){
        return "consProduto";
    }
    
    public String novo(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getIproduto()){
            if ((clienteMB.getCliente()!=null) && (clienteMB.getCliente().getIdcliente()!=null)){
                produto = new Produto();
                return "cadProduto";
            }else {
                FacesMessage mensagem = new FacesMessage("Erro! ", "Selecione um cliente");
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
                return "";
            }
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        
    }
    public String salvar() {
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getIproduto()) {
            ProdutoFacade produtoFacade = new ProdutoFacade();
            produto.setCliente(clienteMB.getCliente());
            try {
                produtoFacade.salvar(produto);
                produto = new Produto();
                gerarListaProduto();
                return "consProduto";
            } catch (SQLException ex) {
                Logger.getLogger(ProdutoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return null;
    }
    
    public void gerarListaProduto() {
        if ((clienteMB.getCliente() != null) && (clienteMB.getCliente().getIdcliente() != null)) {
            ProdutoFacade produtoFacade = new ProdutoFacade();
            try {
                listaProduto = produtoFacade.listar(clienteMB.getCliente().getIdcliente());
                if (listaProduto == null) {
                    listaProduto = new ArrayList<Produto>();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProdutoMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    public String editar(){
        if (usuarioLogadoBean.getUsuario().getTipoacesso().getAcesso().getAproduto()){
            if (listaProduto!=null){
                for(int i=0;i<listaProduto.size();i++){
                    if (listaProduto.get(i).isSelecionado()){
                        produto = listaProduto.get(i);
                        listaProduto.get(i).setSelecionado(false);
                        i=100000;
                        return "cadProduto";
                    }
                }
            }
            return  "";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        
    }
    
    public String voltar(){
        return "principal";
    }
    
    public String selecionarUnidade(){
        clienteMB.setPagina("consProduto");
        listaProduto = null;
        return "selecionarUnidade";
    }
}
