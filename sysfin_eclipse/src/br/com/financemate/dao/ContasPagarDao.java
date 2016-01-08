/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Arquivocontaspagar;
import br.com.financemate.model.Contaspagar;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class ContasPagarDao {
    
    public Contaspagar salvar(Contaspagar conta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        //abrindo uma transação
        manager.getTransaction().begin();
        conta = manager.merge(conta);
        //fechando uma transação
        manager.getTransaction().commit();
        return conta;
    }
    
    /**
     *
     */
    public List<Contaspagar> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaContas = q.getResultList();
        manager.getTransaction().commit();
        return listaContas;
    }

    public List<Contaspagar> listarContas(String sql) throws SQLException{
        sql = "SELECT c FROM Contaspagar c";
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaContas = q.getResultList();
        manager.getTransaction().commit();
        return listaContas;
    }

    
    public Contaspagar consultar(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar conta = manager.find(Contaspagar.class, idConta);
        manager.getTransaction().commit();
        return conta;
    }
    
    public void excluir(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar conta = manager.find(Contaspagar.class, idConta);
        manager.remove(conta);
        manager.getTransaction().commit();
    }
    
    
    
    public Contaspagar consultarVenda(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().commit();
        Query q = manager.createQuery(sql);
        Contaspagar conta = null;
        if (q.getResultList().size()>0){
            conta = (Contaspagar) q.getResultList().get(0);
        }
        manager.getTransaction().begin();
        return conta;
    }
    
   
    
    public void salvarArquivo(Arquivocontaspagar arquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        //abrindo uma transação
        manager.getTransaction().begin();
        manager.merge(arquivo);
        //fechando uma transação
        manager.getTransaction().commit();
    }
    
    public Arquivocontaspagar consultarArquivo(int idContasPagar) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select c from  Arquivocontaspagar  c where c.contasPagar=" + idContasPagar);
        Arquivocontaspagar arquivo = null;
        if (q.getResultList().size()>0){
            arquivo =  (Arquivocontaspagar) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        return arquivo;
    }
    
    public void excluirArquivo(int idArquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Arquivocontaspagar arquivo = manager.find(Arquivocontaspagar.class, idArquivo);
        if (arquivo!=null){
            manager.remove(arquivo);
        }
        manager.getTransaction().commit();
    }
    
   
}
