/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Produto;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;




/**
 *
 * @author Wolverine
 */
public class ProdutoDao {
    
    
    public List<Produto> listar(int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select p from Produto p where p.cliente.idcliente=" + idCliente);
        List<Produto> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
    
    public Produto consultar(int idProduto) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Produto produto = manager.find(Produto.class, idProduto);
        manager.getTransaction().commit();
        return produto;
    }
    
    public Produto salvar(Produto produto) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        manager.merge(produto);
        manager.getTransaction().commit();
        return produto;
    }
    
}
