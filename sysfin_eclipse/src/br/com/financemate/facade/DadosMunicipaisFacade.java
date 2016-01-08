/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;

import br.com.financemate.dao.DadosMunicipaisDao;
import br.com.financemate.model.Dadosmunicipais;
import java.sql.SQLException;


/**
 *
 * @author Wolverine
 */
public class DadosMunicipaisFacade {
    
    DadosMunicipaisDao dadosMunicipaisDao;
    
    public Dadosmunicipais salvar(Dadosmunicipais dados) throws SQLException{
        dadosMunicipaisDao = new DadosMunicipaisDao();
        return dadosMunicipaisDao.salvar(dados);
    }
    
    public Dadosmunicipais consultar(int idCliente) throws SQLException{
        dadosMunicipaisDao = new DadosMunicipaisDao();
        return dadosMunicipaisDao.consultar(idCliente);
    }
    
}
