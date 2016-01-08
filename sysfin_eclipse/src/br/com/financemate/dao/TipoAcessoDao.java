/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Tipoacesso;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author Kamila
 */
public class TipoAcessoDao {
    
    
    public Tipoacesso salvar(Tipoacesso tipoacesso) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        tipoacesso = manager.merge(tipoacesso);
        manager.getTransaction().commit();
        return tipoacesso;
    }
    public List<Tipoacesso> listar() throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select t from Tipoacesso t order by t.descricao");
        List<Tipoacesso> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
     public Tipoacesso consultar(int idTipoPlanoContas) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Tipoacesso tipoacesso = manager.find(Tipoacesso.class, idTipoPlanoContas);
        manager.getTransaction().commit();
        return tipoacesso;
    }

    public Tipoacesso consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
