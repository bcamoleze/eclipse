package br.com.casadiconti.robosintegra.estados;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.casadiconti.robosintegra.leituraOCR.CaptchaSaoPaulo;
import br.com.casadiconti.robosintegra.principal.ListaCNPJ;

public class ConsultaSaoPaulo {
	
	public void consultarSP(WebDriver navegador, String cnpj) {
		
		navegador.get(
				"https://www.cadesp.fazenda.sp.gov.br/(S(0ffvwkrkphx3131oai4y0q1k))/Pages/Cadastro/Consultas/ConsultaPublica/ConsultaPublica.aspx");

		navegador.findElement(By.id(
				"ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_filtroEmitirCertidaoTabPanel_tipoFiltroDropDownList"))
				.sendKeys("CNPJ");

		navegador.findElement(By.id(
				"ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_filtroEmitirCertidaoTabPanel_valorFiltroTextBox"))
				.sendKeys(cnpj);
		
		CaptchaSaoPaulo captcha = new CaptchaSaoPaulo();

		// Continua tentando forçando a quebra do captcha até conseguir				
		for(boolean inserir = false; inserir != true; inserir = captcha.quebrar(navegador) );

		// Captura textos da pagina principal
		String conteudoPrincipal = navegador.findElement(By.className("conteudoPrincipal"))
				.getText();
		
		// Invoca metodo para gerar o JSON		
        gerarJsonSP(conteudoPrincipal);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "resource" })
	public void gerarJsonSP(String conteudoPrincipal) {
		
//		JSONArray  array = new JSONArray();
		
		JsonObject obj   = new JsonObject();
		
		String cnpj        = conteudoPrincipal.substring(conteudoPrincipal.indexOf("CNPJ") + 6,conteudoPrincipal.indexOf("Nome Empresarial"));
		String ie          = conteudoPrincipal.substring(conteudoPrincipal.indexOf("IE") + 4, conteudoPrincipal.indexOf("CNPJ"));
		String razao       = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Nome Empresarial") + 18, conteudoPrincipal.indexOf("Nome Fantasia"));
		String fantasia    = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Nome Fantasia") + 14, conteudoPrincipal.indexOf("Natureza Jurídica"));
		String logradouro  = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Logradouro") + 12, conteudoPrincipal.indexOf("Nº"));
		String num         = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Nº") + 4, conteudoPrincipal.indexOf("Complemento"));
		String complemento = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Complemento") + 13, conteudoPrincipal.indexOf("CEP"));
		String bairro      = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Bairro") + 8,conteudoPrincipal.indexOf("Município"));
		String cidade      = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Município") + 11, conteudoPrincipal.indexOf("UF"));
		String uf          = conteudoPrincipal.substring(conteudoPrincipal.indexOf("UF") + 4, conteudoPrincipal.indexOf("Informações Complementares"));
		String cep         = conteudoPrincipal.substring(conteudoPrincipal.indexOf("CEP") + 5, conteudoPrincipal.indexOf("Bairro"));
		String natureza    = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Natureza Jurídica") + 19, conteudoPrincipal.indexOf("Endereço"));
	    String situacao    = conteudoPrincipal.substring(conteudoPrincipal.indexOf("Situação Cadastral") + 20, conteudoPrincipal.indexOf("Data da Situação Cadastral"));
			
		obj.addProperty("cnpj" , cnpj.replaceAll("[^0-9]", ""));
		obj.addProperty("ie" , ie.replaceAll("[^0-9]", ""));
		obj.addProperty("razao" , razao);
		obj.addProperty("fantasia" , fantasia);
		obj.addProperty("rua" , logradouro);
		obj.addProperty("numero" , num);
		obj.addProperty("complemento" , complemento);
		obj.addProperty("bairro" , bairro);
		obj.addProperty("cidade" , cidade);
		obj.addProperty("uf", uf);
		obj.addProperty("cep" , cep );
		obj.addProperty("natureza" , natureza);
		obj.addProperty("ativo", situacao);
		
//		array.add(obj);
	
		try {
						
			//FileWriter arqJson = new FileWriter("./resultado_saopaulo.json");
			
			File arqJson = new File("./resultado_saopaulo.json");
			
			if (arqJson.exists() && !arqJson.isDirectory()) {
				
				String texto = new String(Files.readAllBytes(Paths.get("./resultado_saopaulo.json")), StandardCharsets.UTF_8);
				
				Object objJava = null;
				JsonArray jarray = null;
				JsonParser jparser = new JsonParser();
				 
				objJava = jparser.parse(texto);
				jarray = (JsonArray) objJava;
				
				jarray.add(obj);
				
			    FileWriter  writer  = new FileWriter(arqJson);
			    PrintWriter pwriter = new PrintWriter(writer);
			    
			    pwriter.println(jarray.toString());
			    
			    writer.close();
				
			
			} else {
				
				JSONArray  array = new JSONArray();
				array.add(obj);
				
			    FileWriter  writer  = new FileWriter(arqJson);
			    PrintWriter pwriter = new PrintWriter(writer);
			    
			    pwriter.println(array.toString());
			    
			    writer.close();		
				
//				arqJson.write(array.toJSONString());
//				arqJson.flush();
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
