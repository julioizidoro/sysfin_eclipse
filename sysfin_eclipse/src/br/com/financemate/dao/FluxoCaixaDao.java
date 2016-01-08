/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Fluxocaixa;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Julio
 */
public class FluxoCaixaDao {
    
    public Fluxocaixa salvar(Fluxocaixa fluxo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        fluxo = manager.merge(fluxo);
        manager.getTransaction().commit();
        return fluxo;
    }
    
    public List<Fluxocaixa> listar(int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select f from Fluxocaixa f where f.cliente.idcliente=" + idCliente);
        List<Fluxocaixa> listaFluxo = q.getResultList();
        manager.getTransaction().commit();
        return listaFluxo;
    }
    
    public void excluir(int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("Delete from fluxocaixa where cliente_idcliente=" + idCliente);
        q.executeUpdate();
        manager.getTransaction().commit();
    }
    
}