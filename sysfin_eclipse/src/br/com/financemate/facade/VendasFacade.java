/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.VendasDao;
import br.com.financemate.model.Emissaonota;
import br.com.financemate.model.Vendas;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Wolverine
 */
public class VendasFacade {
    
    VendasDao vendasDao;
    
    public Vendas salvar(Vendas venda) throws SQLException{
        vendasDao = new VendasDao();
        return vendasDao.salvar(venda);
    }
    
    public List<Vendas> listar(String sql) throws SQLException{
        vendasDao = new VendasDao();
        return vendasDao.listar(sql);
    }
    
    public Vendas consultar(int idVenda) throws SQLException{
        vendasDao = new VendasDao();
        return vendasDao.consultar(idVenda);
    }
    
    public void Excluir(int idVendas) throws SQLException{
        vendasDao = new VendasDao();
        vendasDao.Excluir(idVendas);
    }
    
    public Emissaonota salvar(Emissaonota emissaonota)throws SQLException{
        vendasDao = new VendasDao();
        return vendasDao.salvar(emissaonota);
    }
    
    public Emissaonota getEmissao(int idVendas)throws SQLException{
        vendasDao = new VendasDao();
        return vendasDao.getEmissao(idVendas);
    }
    
   
}
