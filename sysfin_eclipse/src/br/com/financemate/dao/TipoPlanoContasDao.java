/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Tipoplanocontas;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author Kamila
 */
public class TipoPlanoContasDao {
    
    
    public Tipoplanocontas salvar(Tipoplanocontas tipoplano) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        tipoplano = manager.merge(tipoplano);
        manager.getTransaction().commit();
        return tipoplano;
    }
    public List<Tipoplanocontas> listar() throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select t from Tipoplanocontas t order by t.descricao");
        List<Tipoplanocontas> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
     public Tipoplanocontas consultar(int idTipoPlanoContas) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Tipoplanocontas tipoplano = manager.find(Tipoplanocontas.class, idTipoPlanoContas);
        manager.getTransaction().commit();
        return tipoplano;
    }

}
