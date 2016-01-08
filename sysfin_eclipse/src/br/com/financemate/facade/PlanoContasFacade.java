/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.model.Planocontas;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author Wolverine
 */
public class PlanoContasFacade {
    
    PlanoContasDao planoContasDao;
    
    public Planocontas salvar(Planocontas plano) throws SQLException{
        planoContasDao = new PlanoContasDao();
        return planoContasDao.salvar(plano);
    }
    
    public List<Planocontas> listar(int idTipo) throws SQLException{
        planoContasDao = new PlanoContasDao();
        return planoContasDao.listar(idTipo);
    }
    
    public Planocontas consultar(int idPlanoContas) throws SQLException{
        planoContasDao = new PlanoContasDao();
        return planoContasDao.consultar(idPlanoContas);
    }
    
    public List<Planocontas> listar(String descricao, int idTipo) throws SQLException{
        planoContasDao = new PlanoContasDao();
        return planoContasDao.listar(descricao, idTipo);
    }
    public List<Planocontas> listar() throws Exception{
        planoContasDao = new PlanoContasDao();
        return planoContasDao.listar();
        
    }
    
}
