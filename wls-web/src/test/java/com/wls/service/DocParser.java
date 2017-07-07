package com.wls.service;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.model.PicturesTable;  
import org.apache.poi.hwpf.usermodel.CharacterRun;  
import org.apache.poi.hwpf.usermodel.Picture;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.hwpf.extractor.WordExtractor;  
import org.apache.poi.hwpf.usermodel.Paragraph;     
import org.apache.poi.hwpf.usermodel.Table;     
import org.apache.poi.hwpf.usermodel.TableCell;     
import org.apache.poi.hwpf.usermodel.TableIterator;     
import org.apache.poi.hwpf.usermodel.TableRow;    
import org.apache.poi.openxml4j.opc.PackagePart;
//import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
public class DocParser {
	public static final String inputFile="H:\\资料夹\\Trumpe Cadde商业中心.docx";  
//	public static final String inputFile="H:\\资料夹\\巴西的集装箱家具展厅.docx";  
	public static void main(String[] args) {
		try {  
            getWordAndStyle(inputFile);  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		
	}
	
	
	public static void byte2image(byte[] data,String path){
	    if(data.length<3||path.equals("")) return;
	    try{
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close();
	    } catch(Exception ex) {
	      System.out.println("Exception: " + ex);
	      ex.printStackTrace();
	    }
	  }
	
	
	 public static void getWordAndStyle(String fileName) throws Exception {  
		 XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(fileName));
		 int pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
		 int characters = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getCharacters();
		 List<XWPFParagraph> paras = docx.getParagraphs();  
//		 for (int i = 0; i < paras.size(); i++) {
//			 System.out.println(i);
//			 System.out.println(paras.get(i).getText());
//		}
//		 List<XWPFPictureData> pics=docx.getAllPictures();
//		 for (int i = 0; i < pics.size(); i++) {
//			byte2image(pics.get(i).getData(),"I:\\pic\\"+i+".jpg");
//		}
		 
		 List<PackagePart> lpList=docx.getAllEmbedds(); 
		 for (int i = 0; i < lpList.size(); i++) {
			
		}
		 
	     
	 }
}
