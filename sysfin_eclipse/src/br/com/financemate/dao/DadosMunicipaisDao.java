/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.Connection.ConectionFactory;
import br.com.financemate.model.Dadosmunicipais;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class DadosMunicipaisDao {
    
    
    public Dadosmunicipais salvar(Dadosmunicipais dados) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        dados = manager.merge(dados);
        manager.getTransaction().commit();
        return dados;
    }
    
    public Dadosmunicipais consultar(int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select d from Dadosmunicipais d where d.cliente=" + idCliente);
        Dadosmunicipais dadosmunicipais = null;
        if (q.getResultList().size()>0){
            dadosmunicipais = (Dadosmunicipais) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        return dadosmunicipais;
    }
}
