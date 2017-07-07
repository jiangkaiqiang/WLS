package com.wls.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
public class Word07ToHtml {
	
	public static void parserHtml(){
		
	}
	public static void parseToHtml() throws IOException {
		File f = new File("I://HIGH CUBE咖啡厅，双十一前夕也得抽空看看.docx");
		if (!f.exists()) {
			System.out.println("Sorry File does not Exists!");
		} else {
			if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {
				
				InputStream in = new FileInputStream(f);
				XWPFDocument document = new XWPFDocument(in);

				File imageFolderFile = new File("I://test");
				XHTMLOptions options = XHTMLOptions.create().URIResolver(
						new FileURIResolver(imageFolderFile));
				options.setExtractor(new FileImageExtractor(imageFolderFile));

				
				OutputStream out = new FileOutputStream(new File(
						"I://HIGH CUBE咖啡厅，双十一前夕也得抽空看看.html"));
				XHTMLConverter.getInstance().convert(document, out, null);
			} else {
				System.out.println("Enter only MS Office 2007+ files");
			}
		}
	}
	
	public static void main(String args[]) {
		try {
			parseToHtml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
