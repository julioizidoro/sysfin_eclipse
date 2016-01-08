/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.facade;

import br.com.financemate.dao.TipoPlanoContasDao;
import br.com.financemate.model.Tipoplanocontas;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Kamila
 */
public class TipoPlanoContasFacede {
    
    TipoPlanoContasDao tipoPlanoContasDao;
    
     public Tipoplanocontas salvar(Tipoplanocontas tipoplano) throws SQLException{
        tipoPlanoContasDao = new TipoPlanoContasDao();
        return tipoPlanoContasDao.salvar(tipoplano);
    }
     public List<Tipoplanocontas> listar() throws SQLException{
        tipoPlanoContasDao = new TipoPlanoContasDao();
        return tipoPlanoContasDao.listar();
    }
     public Tipoplanocontas consultar(int idtipoplanocontas) throws SQLException{
        tipoPlanoContasDao = new TipoPlanoContasDao();
        return tipoPlanoContasDao.consultar(idtipoplanocontas);
    }
    
      
    
}
