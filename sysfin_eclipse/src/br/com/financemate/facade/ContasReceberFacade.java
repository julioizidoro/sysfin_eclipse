/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.model.Contasreceber;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Wolverine
 */
public class ContasReceberFacade {
    
    ContasReceberDao contasReceberDao;
    
   public Contasreceber salvar(Contasreceber conta) {
        contasReceberDao = new ContasReceberDao();
        try {
            return contasReceberDao.salvar(conta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Contasreceber> listar(String sql) throws SQLException{
        contasReceberDao = new ContasReceberDao();
        return contasReceberDao.listar(sql);  
    }
    
    public Contasreceber consultar(int idConta) throws SQLException{
        contasReceberDao = new ContasReceberDao();
        return contasReceberDao.consultar(idConta);
    }
    
    public void excluir(int idConta){
        contasReceberDao = new ContasReceberDao();
        try {
            contasReceberDao.excluir(idConta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Contasreceber consultarVendaFornecedor(int idVenda) throws SQLException{
        contasReceberDao = new ContasReceberDao();
        return contasReceberDao.consultarVendaFornecedor(idVenda);
    }
    
}
