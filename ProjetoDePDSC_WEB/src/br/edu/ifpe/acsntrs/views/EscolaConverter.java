package br.edu.ifpe.acsntrs.views;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.ifpe.acsntrs.entity.Escola;

/**
 * 
 * @author Tássio
 *
 */
@FacesConverter(value = "escolaConverter", forClass = Escola.class)
public class EscolaConverter implements Converter
{
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
	{
		if(value != null & value.isEmpty() == false)
		{
			return (Escola) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
	{
		if(value instanceof Escola)
		{
			Escola escola = (Escola) value;
			if(escola != null && escola instanceof Escola && escola.getId() != null)
			{
				uiComponent.getAttributes().put(escola.getId().toString(), escola);
				return escola.getId().toString();
			}
		}
		return "";
	}
}