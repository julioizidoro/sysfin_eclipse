/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.facade;


import br.com.financemate.dao.FluxoCaixaDao;
import br.com.financemate.model.Fluxocaixa;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Julio
 */
public class FluxoCaixaFacade {
    
    FluxoCaixaDao fluxoCaixaDao;
    
    public Fluxocaixa salvar(Fluxocaixa fluxo) throws SQLException{
        fluxoCaixaDao = new FluxoCaixaDao();
        return fluxoCaixaDao.salvar(fluxo);
    }
    
    public List<Fluxocaixa> listar(int idCliente) throws SQLException{
        fluxoCaixaDao = new FluxoCaixaDao();
        return fluxoCaixaDao.listar(idCliente);
    }
    
    public void excluir(int idCliente)throws SQLException{
        fluxoCaixaDao = new FluxoCaixaDao();
        fluxoCaixaDao.excluir(idCliente);
    }
    
}
