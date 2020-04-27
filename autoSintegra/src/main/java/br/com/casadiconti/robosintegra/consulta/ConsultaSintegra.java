package br.com.casadiconti.robosintegra.consulta;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.common.collect.ImmutableList;

import br.com.casadiconti.robosintegra.estados.ConsultaSaoPaulo;

public class ConsultaSintegra {

	public void consultar(String uf, String cnpj) {

		System.setProperty("webdriver.chrome.driver", "./chromedriver/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		WebDriver navegador = new ChromeDriver();

		ImmutableList<String> estados = ImmutableList.of("AC", "AL", "AM", "AP", "BA", "CE", 
														 "DF", "ES", "GO", "MA", "MT", "MS", 
														 "MG", "PA", "PB", "PR", "PE", "PI", 
														 "RJ", "RN", "RO", "RS", "RR", "SC", 
														 "SE", "SP", "TO");
		
		int index = estados.lastIndexOf(uf);
		
		switch (index) {
		
		case 15:
			/*
			 * ConsultaSintegraPR pr = new ConsultaSintegraPR(); pr.consultarPR(navegador,
			 * cnpj); break;
			 */			
		case 23: 
			/*
			 * ConsultaSintegraSC sc = new ConsultaSintegraSC(); sc.consultarSC(navegador,
			 * cnpj); break;
			 */
		case 25:
			ConsultaSaoPaulo sp = new ConsultaSaoPaulo();
			sp.consultarSP(navegador, cnpj);
			break;

		}
		
		//encerra chromedriver
		navegador.quit();

	}

}
