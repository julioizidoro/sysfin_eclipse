/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Planocontas;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class PlanoContasDao {
    
    public Planocontas salvar(Planocontas plano) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        plano = manager.merge(plano);
        manager.getTransaction().commit();
        return plano;
    }
    
    public List<Planocontas> listar(int idTipo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select p from Planocontas p where p.tipoplanocontas.idtipoplanocontas="+ idTipo  + " order by p.descricao");
        List<Planocontas> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
    
     public List<Planocontas> listar() throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select p from Planocontas p order by p.descricao" );
        manager.getTransaction().commit();
        return q.getResultList();
    }
    
    public List<Planocontas> listar(String descricao, int idTipo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select p from Planocontas p where p.descricao like '%" + descricao + "%'  and p.tipoplanocontas.idtipoplanocontas="+ idTipo  + " order by p.descricao");
        List<Planocontas> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
    
    public Planocontas consultar(int idPlanoContas) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Planocontas plano = manager.find(Planocontas.class, idPlanoContas);
        manager.getTransaction().commit();
        return plano;
    }
    
}
