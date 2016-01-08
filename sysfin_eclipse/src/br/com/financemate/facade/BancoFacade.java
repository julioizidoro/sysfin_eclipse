/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.model.Banco;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Wolverine
 */
public class BancoFacade {
    
    BancoDao bancoDao;
    
    public Banco salvar(Banco banco) throws SQLException{
        bancoDao = new BancoDao();
        return bancoDao.salvar(banco);
    }
    
    public List<Banco> listar(String sql) {
        bancoDao = new BancoDao();
        try {
            return bancoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BancoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Banco consultar(int idBanco) throws SQLException{
        bancoDao = new BancoDao();
        return bancoDao.consultar(idBanco);
    }
    
    public Banco consultar(int idCliente, String nome) throws SQLException{
        bancoDao = new BancoDao();
        return bancoDao.consultar(idCliente, nome);
    }
    
}
