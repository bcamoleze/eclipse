package br.com.casadiconti.robosintegra.leituraOCR;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class LeituraOCR {
	
	   public String readImage(String urlImage){
		   
	        File imageFile = new File(urlImage);
	        ITesseract instance = new Tesseract(); 
	        String result = new String();
	        
	        instance.setDatapath("./Tess4j/tessdata");
	        instance.setLanguage("eng");
	        
	        instance.setTessVariable("user_defined_dpi", "300");
	        //instance.setTessVariable("debug_file", "/dev/null");
	        
	        try {
	            result = instance.doOCR(imageFile);
	            System.out.println(result);
	        } catch (TesseractException e) {
	            System.err.println(e.getMessage());
	        }
	        
			return result;
	    }


}
