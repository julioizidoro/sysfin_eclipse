/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.ManageBean.contasReceber;

import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.Util.Formatacao;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Vendas;
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
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Greici
 */
@Named("ContasReceberMB")
@ViewScoped
public class ContasReceberMB implements Serializable{
    @Inject
    private UsuarioLogadoBean usuarioLogadoBean;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Date dataInicial;
    private Date dataFinal;
    private String sql;
    private List<Contasreceber> listaContasReceber;
    private Contasreceber contasReceber;
    private boolean recebidas;
    private boolean verCliente=false;
    private int quantidadeTitulos;
    private float totalContasReceber;
    private float totalJurosReceber;
    private float totalDescontosReceber;
    private float valorTotalRecebido;

    @PostConstruct
    public void init(){
        gerarListaCliente();
    	getUsuarioLogadoBean();
        verificarCliente();
        criarConsultaContaReceber();
        gerarListaContas();
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

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
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

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    
    
    public boolean isRecebidas() {
        return recebidas;
    }

    public void setRecebidas(boolean recebidas) {
        this.recebidas = recebidas;
    }

    public int getQuantidadeTitulos() {
        return quantidadeTitulos;
    }

    public void setQuantidadeTitulos(int quantidadeTitulos) {
        this.quantidadeTitulos = quantidadeTitulos;
    }

    public float getTotalContasReceber() {
        return totalContasReceber;
    }

    public void setTotalContasReceber(float totalContasReceber) {
        this.totalContasReceber = totalContasReceber;
    }

    public float getTotalJurosReceber() {
        return totalJurosReceber;
    }

    public void setTotalJurosReceber(float totalJurosReceber) {
        this.totalJurosReceber = totalJurosReceber;
    }

    public float getTotalDescontosReceber() {
        return totalDescontosReceber;
    }

    public void setTotalDescontosReceber(float totalDescontosReceber) {
        this.totalDescontosReceber = totalDescontosReceber;
    }

    public float getValorTotalRecebido() {
        return valorTotalRecebido;
    }

    public void setValorTotalRecebido(float valorTotalRecebido) {
        this.valorTotalRecebido = valorTotalRecebido;
    }

    public boolean isVerCliente() {
        return verCliente;
    }

    public void setVerCliente(boolean verCliente) {
        this.verCliente = verCliente;
    }

    
    
    public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
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
    
    public void criarConsultaContaReceber(){
        String data = Formatacao.ConvercaoDataPadrao(new Date());
        String mesString = data.substring(3, 5);
        String anoString = data.substring(6, 10);
        int mesInicio = Integer.parseInt(mesString);
        int anoInicio = Integer.parseInt(anoString);
        int mescInicio;
        int mescFinal;
        int anocInicio = 0;
        int anocFinal = 0;
        if (mesInicio==1){
            mescInicio=12;
            anocInicio=anoInicio - 1;
        }else {
            mescInicio = mesInicio - 1;
            anocInicio = anoInicio;
        }
        if (mesInicio==12){
            mescFinal=1;
            anocFinal=anoInicio+1;
        }else {
            mescFinal = mesInicio  + 1;
            anocFinal = anoInicio;
        }
        String dataInicial = anocInicio + "-" + Formatacao.retornaDataInicia(mescInicio);
        String dataFinal = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
        setDataInicial(Formatacao.ConvercaoStringData(dataInicial));
        setDataFinal(Formatacao.ConvercaoStringData(dataFinal));
        if (usuarioLogadoBean.getUsuario().getCliente()>0){
            sql = " Select v from Contasreceber v where v.dataVencimento<='" + dataFinal + 
                "' and v.valorPago=0 and v.cliente.idcliente=" + usuarioLogadoBean.getUsuario().getCliente() + 
                    " order by v.dataVencimento";
        }else {
            sql = " Select v from Contasreceber v where v.cliente.visualizacao='Operacional' and "
                    + "v.dataVencimento<='" + dataFinal + 
                "' and v.valorPago=0 order by v.dataVencimento";
        }
        
    }
    
    public void gerarListaContas() {
        try {
            ContasReceberFacade contasReceberFacadece = new ContasReceberFacade();
            listaContasReceber = contasReceberFacadece.listar(sql);
            if (listaContasReceber == null) {
                listaContasReceber = new ArrayList<Contasreceber>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Contas", "Erro");
        }
        gerarTotalContas();
        
    }

    public void pesquisarContas() {
        sql = "Select v from Contasreceber v where ";
        if (recebidas) {
            if (cliente != null) {
                sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
            }
            sql = sql + "v.dataPagamento>='" + Formatacao.ConvercaoDataSql(getDataInicial())
                    + "' and v.dataPagamento<='" + Formatacao.ConvercaoDataSql(getDataFinal())
                    + "' and v.valorPago>0 order by v.dataVencimento";
        } else {
            if (cliente != null) {
                sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
            } else {
                sql = sql + " v.cliente.visualizacao='Operacional' and ";
            }
            sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(getDataInicial())
                    + "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(getDataFinal())
                    + "' and v.valorPago=0 order by v.dataVencimento";
        }
        gerarListaContas();
    }

    
        
        
    
    public void gerarTotalContas(){
        quantidadeTitulos = listaContasReceber.size();
        totalContasReceber = 0;
        totalJurosReceber = 0;
        totalDescontosReceber = 0;
        valorTotalRecebido = 0;
        for (int i = 0; i < listaContasReceber.size(); i++) {
            totalContasReceber = totalContasReceber + listaContasReceber.get(i).getValorParcela();
            totalJurosReceber = totalJurosReceber + listaContasReceber.get(i).getJuros();
            totalDescontosReceber = totalDescontosReceber + listaContasReceber.get(i).getDesagio();
            valorTotalRecebido = valorTotalRecebido + listaContasReceber.get(i).getValorPago();
         }
    }
    
    public String limparDadosPesquisa() {
        try {
            ContasReceberFacade contasReceberFacadece = new ContasReceberFacade();
            criarConsultaContaReceber();
            listaContasReceber = contasReceberFacadece.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Contas", "Erro");
        }
        return "";

    }
    
   public String editar(Contasreceber contasreceber){
         if (contasreceber!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contareceber", contasreceber);       
            RequestContext.getCurrentInstance().openDialog("cadConReceber");
            
        }
        return "";
   }
   
   public void excluir(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        contasReceberFacade.excluir(contasReceber.getIdcontasReceber());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
        gerarListaContas();
     }
    
   public String recebimentoConta(Contasreceber contasreceber){
            if (contasreceber!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contareceber", contasreceber);       
            RequestContext.getCurrentInstance().openDialog("recebimentoConta");
        }
        return "";
    }
   
   public String verStatus(Contasreceber contasreceber) {
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        data = Formatacao.ConvercaoStringDataBrasil(diaData);
        if (contasreceber.getDataVencimento().before(data)) {
            return "../../resources/img/bolaVermelha.png";
        } else {
            if (!contasreceber.getDataVencimento().after(data)) {
                return "../../resources/img/bolaVerde.png";
            } else {
                if (contasreceber.getDataVencimento().equals(data)) {
                    return "../../resources/img/bolaAmarela.png";
                }
            }
        }
        return "../../resources/img/bolaVerde.png";
   }
   
   public  String receberConta(Contasreceber contasreceber){
       if (contasreceber.getValorPago()>0) {
            return "../../resources/img/jaRecebidas.png";
       }else{
          return "../../resources/img/receber.png";
           
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
   
    public void gerarVendas() throws SQLException{
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        String sql = "Select c from Contasreceber c";
        List<Contasreceber> lista = null;
        try {
            lista = contasReceberFacade.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (lista!=null){
            VendasFacade vendasFacade = new VendasFacade();
            for(int i=0;i<lista.size();i++){
               Vendas venda = vendasFacade.consultar(lista.get(i).getVenda());
               if (venda!=null){
                   lista.get(i).setVendas(venda);
                   contasReceberFacade.salvar(lista.get(i));
               }
            }
        }
    }
    
     public void retornoDialogNovo(SelectEvent event) {
         gerarListaContas();
     }
   
}
