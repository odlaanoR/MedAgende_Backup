package Back;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Atestado {
	public static void EmitirAtestado() {
		 Document documento = new Document(PageSize.A4);
	        System.out.println("FUNCIONA PFV");
	        String nome_medico = "Dr.Lorem";
	        String titulo_pdf = "Receita "+ nome_medico +".pdf";
	        
	        try {
				PdfWriter.getInstance(documento, new FileOutputStream(titulo_pdf));
				documento.open();
				documento.addTitle("Atestado médico");
				documento.add(new Paragraph("Atestado médico \n"));
				documento.add(new Paragraph("Atesto para os devidos fins que _______________________________________, portador do RG ____________ esteve em consulta médica neste consultório, no dia ____/____/_______, do periódo das ____ às ____. \n\n\n________, ________ de _________ de 2025"));
				//Gambiarra assinatura:
				documento.add(new Paragraph(600, "Ass.:_____________________________________________________"));
			} catch (Exception e) {
			
				e.printStackTrace();
			} finally {
				documento.close();
			}
	        
	        try {
				Desktop.getDesktop().open(new File(titulo_pdf));
			} catch (Exception e) {
				e.printStackTrace();
			};
	};
}
