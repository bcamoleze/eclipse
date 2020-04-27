package br.com.casadiconti.robosintegra.leituraOCR;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CaptchaSaoPaulo {
	
	public boolean quebrar(WebDriver navegador) {
		
		boolean retorno = true;
        
		// faz download da imagem 
		try {

			String urlImage = navegador.findElement(By.id(
					"ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_filtroEmitirCertidaoTabPanel_imagemDinamica"))
					.getAttribute("src");

			try {
				BufferedImage bufferedImage = ImageIO.read(new URL(urlImage));
				File outputfile = new File("./saopaulo.png");
				ImageIO.write(bufferedImage, "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
			// cria objeto tipo LeituraOCR
			LeituraOCR ocr = new LeituraOCR();
            // le imagem e converte para texto
			String captcha = ocr.readImage("./saopaulo.png");
			
			// limpa o campo de texto de captcha
			navegador.findElement(By.id(
					"ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_filtroEmitirCertidaoTabPanel_imagemDinamicaTextBox"))
			.clear();
			
			// insere texto convertido no captcha
			navegador.findElement(By.id(
					"ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_filtroEmitirCertidaoTabPanel_imagemDinamicaTextBox"))
					.sendKeys(captcha);
			
			// Captura textos da pagina principal
			String msg = navegador.findElement(By.id("ctl00_conteudoPaginaPlaceHolder_filtroTabContainer_body"))
					.getText();

			// se a mensagem aparecer é porque deu erro
			if (msg.length() > 0) {
				retorno = false;
			} 

		} catch (Exception e) {
			//e.printStackTrace();
		}

		return retorno;

	}


}
