package com.webapp.boot.view.json;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.webapp.boot.models.entity.Cliente;

@Component("clientes.json")
public class ClienteJson extends MappingJackson2JsonView {

	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		model.remove("titulo");
		model.remove("page");
		
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = (List<Cliente>) model.get("clientesJson");
		model.remove("clientesJson");
		model.put("clientes", clientes);
		
		return super.filterModel(model);
	}

	
	
}
