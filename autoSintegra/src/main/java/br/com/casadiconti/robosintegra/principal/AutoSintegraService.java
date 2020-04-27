package br.com.casadiconti.robosintegra.principal;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.casadiconti.robosintegra.consulta.ConsultaSintegra;


@SpringBootApplication
@RestController
public class AutoSintegraService {
	
	@RequestMapping(value = "/robosintegra" , method = RequestMethod.POST) 
	 public void iniciar(@RequestBody String listaString) throws Exception{
		 	 
		 Object objeto = null;
		 JsonArray jarray = null;
		 JsonParser jparser = new JsonParser();
		 ArrayList<ListaCNPJ> listaCNPJ = new ArrayList<ListaCNPJ>();
		 
		 objeto = jparser.parse(listaString);
		 jarray = (JsonArray) objeto;
		 
		 System.out.println(jarray);
		 
		 for (JsonElement elemento : jarray) {

				ListaCNPJ item = new ListaCNPJ();
				JsonObject jsonObject = elemento.getAsJsonObject();

				String cnpj = jsonObject.get("cnpj").getAsString();
				String uf = jsonObject.get("uf").getAsString();

				item.setCnpj(cnpj);
				item.setUf(uf);

				listaCNPJ.add(item);

		 }
		 
		 
		 ConsultaSintegra c = new ConsultaSintegra();
		 
		 for(ListaCNPJ item : listaCNPJ) {
			c.consultar(item.getUf(), item.getCnpj());
		 }
	 
	 }
		
	public static void main(String[] args) {
		SpringApplication.run(AutoSintegraService.class, args);
	}

}
