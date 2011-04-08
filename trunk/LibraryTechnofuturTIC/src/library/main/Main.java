package library.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import library.dao.BookDao;
import library.domain.Book;
import library.jxel.xlsGenerator.XlsGenerator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		BookDao bookDao=(BookDao)applicationContext.getBean("bookDao");
		
//		GroupService groupService =(GroupService)applicationContext.getBean("groupService");
		
//		Book book = new Book();
//		book.setTitle("java for expert");
//		book.setAuthor("jerome");
//		book.setIsbn("AAA111BBB");
//		bookService.addBook(book);
//		Date date = new Date();
//		
//		Group group = new Group();
//		group.setCreationDate(date);
//		group.setName("javablackBelt");
//		group.setNumberOfStudent(12);
//		groupService.createGroup(group);
//		List<Book> bookList = new ArrayList<Book>();
//		bookDao.deleteBookById(12L);
//		System.out.println("Search by isbn : \n");
//		bookList = bookDao.searchBookByIsbn("AAA444");
//		if (bookList.isEmpty()){
//			System.out.println("NO RESULT");
//		}else{
//			for(Book b : bookList){
//				System.out.println(b);
//			}
//		}
//		extractDataFromXlsFile(bookDao);
		XlsGenerator xlsGenerator = new XlsGenerator();
		xlsGenerator.exportAllBooks(bookDao);
//		create50Book(bookDao);
		
	}
	private static void readInSpreadSheet() {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
			WritableSheet sheet = workbook.createSheet("Feuille 1", 0);
			WritableFont arial14font = new WritableFont(WritableFont.ARIAL, 14);
			WritableCellFormat arial14format = new WritableCellFormat (arial14font);
			Label label = new Label(0, 0, "A label record",arial14format); 
			sheet.addCell(label);
			exportAllBooksToXls();
			workbook.write(); 
			workbook.close();
			
			
		} catch (IOException e) {
		throw new RuntimeException(e);
		
		} catch (WriteException e) {
			throw new RuntimeException(e);
		}
	}
	private static void exportAllBooksToXls(){
		
	}
	
	
	private static void extractDataFromXlsFile(BookDao bookDao) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File("C:\\Eclipse\\workspace\\LibraryTechnofuturTIC\\Liste_des_manuels_deb_pour_stagiaires.xls"));
			Sheet sheet = workbook.getSheet(0);
			List<Cell> cellsFromSheet = new ArrayList<Cell>();
			Cell cell = sheet.getCell(0,1);
			
			//put all cell in a array list
			for(int i =1; !cell.getContents().equals("");i++){
				cell = sheet.getCell(0,i);
				cellsFromSheet.add(cell);
			}
			
			for(Cell c:cellsFromSheet){
				Book b = new Book();
				b.setTitle(c.getContents());
				bookDao.addBook(b);
			}
			workbook.close();
		
		} catch (BiffException e) {
		    throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static void create50Book(BookDao bookDao){
		for (int i = 0;i<50;i++){
			Book bk = new Book();
			bk.setAuthor("djey le : " + i);
			bk.setIsbn("AAA" + i + "" + i + "" + i);
			bk.setTitle("java for level : " + i*i);
			bk.setCategory("Java");
			bookDao.addBook(bk);
		}
	}
}
