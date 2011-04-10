package library.jxel.xlsGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import library.dao.BookDao;
import library.domain.Book;

import org.springframework.stereotype.Service;
@Service
public class XlsGenerator {

	public void exportAllBooks(BookDao bookDao) {
		List<Book> books = new ArrayList<Book>();
		books = bookDao.getBooks();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File("AllBook.xls"));
			WritableSheet sheet = workbook.createSheet("Feuille 1", 0);
			//set the width of the row
			sheet.setColumnView(0, 80);
			WritableFont arial14font = new WritableFont(WritableFont.ARIAL, 14);
			WritableFont columnfont = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD);

			WritableCellFormat arial14format = new WritableCellFormat(arial14font);
			WritableCellFormat columnformat = new WritableCellFormat(columnfont);

			arial14format.setWrap(true);

			Label columntitle = new Label (0,0,"Title",columnformat);
			Label columnAuthor = new Label (1,0,"Author",columnformat);
			sheet.addCell(columntitle);
			sheet.addCell(columnAuthor);

			int i = 1;
			for(Book b : books){
				Label titleLabel = new Label(0,i,b.getTitle(),arial14format);
				sheet.addCell(titleLabel);
				Label authorLabel = new Label(1,i,b.getAuthor(),arial14format);
				sheet.addCell(authorLabel);
				Label isbnCell = new Label(2,i,b.getIsbn(),arial14format);
				System.out.println( i + " : " + b.getTitle());
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (WriteException e) {
			throw new RuntimeException(e);
		}
	}

	public ByteArrayOutputStream exportListToXls(List<String> listS, int rowLength) {
		
		ByteArrayOutputStream baots = new ByteArrayOutputStream();
		
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(baots);

			WritableSheet sheet = workbook.createSheet("Feuille 1", 0);
			WritableFont arial14font = new WritableFont(WritableFont.ARIAL, 14);
			WritableCellFormat arial14format = new WritableCellFormat(arial14font);
			arial14format.setWrap(true);
			//			sheet.setColumnView(1 , 50);

			int row = 0;
			int column = 0;
			for (String data : listS) {

				Label label = new Label(column, row, data, arial14format);
				sheet.addCell(label);
				
				column++;	
				if(column > 6){	
					column=0;
					row++;
				}
			
				
			}

			workbook.write();
			workbook.close();
			
			return baots;
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RowsExceededException e) {
			throw new RuntimeException(e);
		} catch (WriteException e) {
			throw new RuntimeException(e);
		}
	}
}
