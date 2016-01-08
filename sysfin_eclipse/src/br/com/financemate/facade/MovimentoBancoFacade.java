/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.MovimentoBancoDao;
import br.com.financemate.model.Movimentobanco;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class MovimentoBancoFacade {
    
    MovimentoBancoDao movimentoBancoDao;
    
    public Movimentobanco salvar(Movimentobanco movimento) throws SQLException{
        movimentoBancoDao = new MovimentoBancoDao();
        return movimentoBancoDao.salvar(movimento);
    }
    
    public List<Movimentobanco> listaMovimento(String sql) throws SQLException{
        movimentoBancoDao = new MovimentoBancoDao();
        return movimentoBancoDao.listaMovimento(sql);
    }
    
    public void excluir(int idMovimento) throws SQLException{
        movimentoBancoDao = new MovimentoBancoDao();
        movimentoBancoDao.excluir(idMovimento);
    }
    
}
