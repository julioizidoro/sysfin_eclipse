/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Banco;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class BancoDao {
    
    public Banco salvar(Banco banco) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        banco = manager.merge(banco);
        manager.getTransaction().commit();
        return banco;
    }
    
    public List<Banco> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        if (q.getResultList().size()>0){
            manager.getTransaction().commit();
            return q.getResultList();
        }
        manager.getTransaction().commit();
        return null;
    }
    
    public Banco consultar(int idBanco) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Banco banco = manager.find(Banco.class, idBanco);
        manager.getTransaction().commit();
        return banco;
    }
    
    public Banco consultar(int idCliente, String nome) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select b from Banco b where b.cliente=" + idCliente +
                " and b.nome='" + nome + "'");
        Banco banco = null;
        if (q.getResultList().size()>0){
            banco= (Banco) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        return banco;
    }
    
   
}
