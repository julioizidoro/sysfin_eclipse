package br.com.financemate.ManageBean.cadastro;



import br.com.financemate.ManageBean.UsuarioLogadoBean;
import br.com.financemate.facade.TipoAcessoFacade;
import br.com.financemate.model.Acesso;
import br.com.financemate.model.Tipoacesso;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("TipoAcessoMB")
@SessionScoped
public class TipoAcessoMB implements Serializable {
    
    private Tipoacesso tipoacesso;
    private List<Tipoacesso> listarTipoAcesso;
    private UsuarioLogadoBean usuarioLogadoBean;
    private String nomeGrupo;
    private Acesso acesso;

    
    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public Tipoacesso getTipoacesso() {
        return tipoacesso;
    }

    public void setTipoacesso(Tipoacesso Tipoacesso) {
        this.tipoacesso = Tipoacesso;
    }

    public List<Tipoacesso> getListarTipoAcesso() throws SQLException {
        if(listarTipoAcesso==null){
            gerarListaTipoUsuario();
        }
        return listarTipoAcesso;
    }

    public void setListarTipoAcesso(List<Tipoacesso> listarTipoAcesso) {
        this.listarTipoAcesso = listarTipoAcesso;
    }
    
    public UsuarioLogadoBean getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(UsuarioLogadoBean usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }
    
    
    public void gerarListaTipoUsuario() throws SQLException {
        TipoAcessoFacade tipoAcessoFacade = new TipoAcessoFacade();
        listarTipoAcesso = tipoAcessoFacade.listar();
        if (listarTipoAcesso == null) {
            listarTipoAcesso = new ArrayList<Tipoacesso>();
        }
    }
    
    public String novo() {
            tipoacesso = new Tipoacesso();
            tipoacesso.setAcesso(new Acesso());
            return "cadTipoUsuario";
    }
    
    public String editar(){
        if (listarTipoAcesso != null) {
            for (int i = 0; i < listarTipoAcesso.size(); i++) {
                if (listarTipoAcesso.get(i).isSelecionado()) {
                    tipoacesso = listarTipoAcesso.get(i);
                    listarTipoAcesso.get(i).setSelecionado(false);
                    i = 100000;
                    return "cadTipoUsuario";
                }
            }
        }
        return "";
         } 
    
    public String salvar() throws SQLException{
        if (tipoacesso==null){
            tipoacesso = new Tipoacesso();
        }
        TipoAcessoFacade tipoAcessoFacade = new TipoAcessoFacade();
        tipoAcessoFacade.salvar(tipoacesso);
        tipoacesso = new Tipoacesso();
        gerarListaTipoUsuario();
        return "consTipoUsuario";
    }
    
    public String cancelar(){
        return "consTipoUsuario";
    }
    
}
