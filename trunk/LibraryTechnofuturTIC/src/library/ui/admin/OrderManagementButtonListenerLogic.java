package library.ui.admin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import library.admin.service.TableUtilities;
import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.Order;
import library.domain.User;
import library.jxel.xlsGenerator.XlsGenerator;
import library.navigator7.MyApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.navigator7.NavigableApplication;

import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * The method execute is called when the button orderManagement is clicked in the AdminPage
 */
@Service
public class OrderManagementButtonListenerLogic{

	@Autowired GroupDao groupDao;
	@Autowired OrderDao orderDao;
	@Autowired UserDao userDao;
	@Autowired XlsGenerator xlsGenerator;
	
	private  List<Order> orderList;
	private  List<User> userList;
	
	public void execute(final VerticalLayout dynamicLayout){		
		
		final HorizontalLayout groupButton = new HorizontalLayout();
		groupButton.setStyleName("segment");
		
		final VerticalLayout tableLayout = new VerticalLayout();
		
		dynamicLayout.addComponent(groupButton);
		
		//this table will display orders
		final Table table = TableUtilities.createOrderTable();
		table.setPageLength(12);
		table.addStyleName("big strong");
		table.setVisible(false);
		dynamicLayout.addComponent(table);
		
		//this button will generate a xls file and will downloaded
		final Button excel = new Button("Télécharger fichier excel");
		excel.addStyleName("big default");
		dynamicLayout.addComponent(excel);
		excel.setVisible(false);		
		dynamicLayout.setExpandRatio(excel, 1);
		
		
		//create a button for each group and add it to groupButton
		List<String> groupNames = groupDao.getGroupNames();
		for (String name :  groupNames){
			
			final Button button = new Button(name);
			groupButton.addComponent(button);
			button.addStyleName("wide tall big");
			
			button.addListener(new Button.ClickListener() {
				
				public void buttonClick(ClickEvent event) {
						
					String groupName = event.getButton().getCaption();
					orderList = orderDao.getOrderByGroupName(groupName);
					userList = userDao.getUsersByGroupName(groupName);
		
					fillTable(userList, orderList, table);
					table.setVisible(true);
					excel.setVisible(true);

				}
			});
		}
		
		//generate and download a xsl file with date form the table
		excel.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				
				List<String> temp = new ArrayList<String>();
				
				for(User user : userList){
					for(Order order : orderList){
						if(order.getUser().getId().equals(user.getId())){
							temp.add(user.getLastName());
							temp.add(user.getFirstName());
							temp.add(user.getEmail());
							
							temp.add(order.getBook_title());
							temp.add(order.getAuthor());
							temp.add(String.valueOf(order.getIsbn()));
							temp.add(String.valueOf(order.getPrice()));
						}
					}
				}
				
				final ByteArrayOutputStream baots = xlsGenerator.exportListToXls(temp, 7);
				
				StreamResource.StreamSource srst = new StreamResource.StreamSource(){
					public InputStream getStream() {
						return new ByteArrayInputStream(baots.toByteArray());
					}
				};
				
				Application myApp = (MyApplication)NavigableApplication.getCurrent();
				StreamResource s = new StreamResource(srst,"commande.xls",myApp);
				
				myApp.getMainWindow().open(s,"_top");
					
			}
		});
	}
	
	/**
	 * Each time a button in groupButton is clicked the table is filled with orders of given group
	 */
	public void fillTable(List<User> userList, List<Order> orderList, Table table){

		final List<String> styleList = new ArrayList<String>();
		table.removeAllItems();

		boolean copy = false;//if a user has more than one order than his information will be displayed only once and his orders will follow
		String style = "white";
		int i=0;
		for(User user  : userList){
			for(Order order : orderList){
				
				if(order.getUser().getId().equals(user.getId())){
					i++;
			
					if(copy == false){//new user : fill all table cell
						table.addItem(new Object[] {user.getLastName(),user.getFirstName(),user.getEmail(),
								order.getBook_title(), order.getAuthor(),order.getIsbn(),new CheckBox(),new CheckBox(),new CheckBox(),new CheckBox(),order.getPrice()},new Integer(i));
						styleList.add(style);
						copy=true;
					}else{//the user has more than one order : table cell with user information will be null
						table.addItem(new Object[] {"","","",
								order.getBook_title(), order.getAuthor(),order.getIsbn(),new CheckBox(),new CheckBox(),new CheckBox(),new CheckBox(),order.getPrice()},new Integer(i));
						styleList.add(style);
					}
				}
			}
			if("white".equals(style)){
				style="blue";
			}else{
				style="white";
			}
			
			copy=false;

		}
		
		//set row color for each user
		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
		    public String getStyle(Object itemId, Object propertyId) {
		       int row = ((Integer)itemId).intValue();
		       
		       return styleList.get(row-1);

		    }
		});

	}
	
}
