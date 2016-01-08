/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;

import br.com.financemate.dao.TipoAcessoDao;
import br.com.financemate.model.Tipoacesso;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class TipoAcessoFacade {
    
    TipoAcessoDao tipoAcessoDao;
    
    public Tipoacesso salvar(Tipoacesso tipoacesso) throws SQLException{
        tipoAcessoDao = new TipoAcessoDao();
        return tipoAcessoDao.salvar(tipoacesso);
    }
    
    public List<Tipoacesso> listar() throws SQLException{
        tipoAcessoDao = new TipoAcessoDao();
        return tipoAcessoDao.listar();
    }
    
    public Tipoacesso consultar(int getIdtipoacesso) throws SQLException{
        tipoAcessoDao = new TipoAcessoDao();
        return tipoAcessoDao.consultar(getIdtipoacesso);
    }
    
    public Tipoacesso consultar() throws SQLException{
        tipoAcessoDao = new TipoAcessoDao();
        return tipoAcessoDao.consultar();
    }
    
}
