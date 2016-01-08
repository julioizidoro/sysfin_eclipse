/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.ProdutoDao;
import br.com.financemate.model.Produto;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class ProdutoFacade {
    
    ProdutoDao produtoDao;
    
    public List<Produto> listar(int idCliente) throws SQLException{
        produtoDao = new ProdutoDao();
        return produtoDao.listar(idCliente);
    }
    
    public Produto consultar(int idProduto) throws SQLException{
        produtoDao = new ProdutoDao();
        return produtoDao.consultar(idProduto);
    }
    
    public Produto salvar(Produto produto) throws SQLException{
        produtoDao = new ProdutoDao();
        return produtoDao.salvar(produto);
    }
    
}
