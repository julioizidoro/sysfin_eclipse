/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.Converter;

import br.com.financemate.model.Cliente;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Greici
 */
@FacesConverter(value="ClienteConverter")
public class ClienteConverter implements Converter{

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Cliente> listaCliente = (List<Cliente>) arg1.getAttributes().get("listaCliente");
        for (Cliente cliente : listaCliente) {
                if (cliente.getNomeFantasia().equalsIgnoreCase(arg2)) {
                    return cliente;
                }
            }
        return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Cliente cliente = (Cliente) arg2;
            return cliente.getNomeFantasia();
        }
    }
    
}
