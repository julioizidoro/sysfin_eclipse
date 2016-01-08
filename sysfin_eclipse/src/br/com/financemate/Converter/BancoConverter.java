/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.Converter;


import br.com.financemate.model.Banco;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kamila
 */
@FacesConverter(value = "BancoConverter")
public class BancoConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Banco> listaBanco = (List<Banco>) arg1.getAttributes().get("listaBanco");
	    if (listaBanco != null) {
	        for (Banco banco : listaBanco) {
	            if (banco.getNome().equalsIgnoreCase(arg2)) {
	                return banco;
	            }
	        }
	    } else {
	        Banco banco = new Banco();
	        return banco;
	    }
	    Banco banco = new Banco();
	    return banco;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Banco banco = (Banco) arg2;
	        return banco.getNome();
	    }
	}
    
}