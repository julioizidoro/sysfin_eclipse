/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Movimentobanco;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class MovimentoBancoDao {
    
    public Movimentobanco salvar(Movimentobanco movimento) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        movimento = manager.merge(movimento);
        manager.getTransaction().commit();
        return movimento;
    }
    
    public List<Movimentobanco> listaMovimento(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Movimentobanco> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
    
    
    public void excluir(int idMovimento) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Movimentobanco movimento = manager.find(Movimentobanco.class, idMovimento);
        manager.remove(movimento);
        manager.getTransaction().commit();
    }
    
    
}
