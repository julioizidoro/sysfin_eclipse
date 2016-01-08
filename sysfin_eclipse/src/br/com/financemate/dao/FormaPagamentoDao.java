/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;


import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Formapagamento;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class FormaPagamentoDao {
    
    public Formapagamento salvar(Formapagamento formaPagamento) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        formaPagamento = manager.merge(formaPagamento);
        manager.getTransaction().commit();
        return formaPagamento;
    }
    
    public void Excluir(int idFormaPagamento) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Formapagamento formaPagamento = manager.find(Formapagamento.class, idFormaPagamento);
        manager.remove(formaPagamento);
        manager.getTransaction().commit();
    }
    
    public List<Formapagamento> listar(int idVenda) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select f from Formapagamento f where f.vendas=" + idVenda) ;
        List<Formapagamento> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }    
}
