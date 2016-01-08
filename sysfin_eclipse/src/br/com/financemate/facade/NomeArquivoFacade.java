/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.facade;

import br.com.financemate.dao.NomeArquivoDao;
import br.com.financemate.model.Nomearquivo;
import java.sql.SQLException;

/**
 *
 * @author Wolverine
 */
public class NomeArquivoFacade {
    
    NomeArquivoDao nomeArquivoDao;
    
    public Nomearquivo salvar(Nomearquivo nomeArquivo) throws SQLException{
        nomeArquivoDao = new NomeArquivoDao();
        return nomeArquivoDao.salvar(nomeArquivo);
    }
    
    public Nomearquivo listar(int idConta) throws SQLException{
        nomeArquivoDao = new NomeArquivoDao();
        return nomeArquivoDao.listar(idConta);
    }
    
}
