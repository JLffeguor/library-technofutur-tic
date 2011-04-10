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
		
		final Table table = TableUtilities.createOrderTable();
		table.setPageLength(12);
		table.addStyleName("big strong");
		table.setVisible(false);
		dynamicLayout.addComponent(table);
		
		final Button excel = new Button("Télécharger fichier excel");
		excel.addStyleName("big default");
		dynamicLayout.addComponent(excel);
		excel.setVisible(false);		
		dynamicLayout.setExpandRatio(excel, 1);
		
		
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
	
	public void fillTable(List<User> userList, List<Order> orderList, Table table){

		final List<String> styleList = new ArrayList<String>();
		table.removeAllItems();

		boolean copy = false;
		String style = "white";
		int i=0;
		for(User user  : userList){
			for(Order order : orderList){
				
				if(order.getUser().getId().equals(user.getId())){
					i++;
			
					if(copy == false){
						table.addItem(new Object[] {user.getLastName(),user.getFirstName(),user.getEmail(),
								order.getBook_title(), order.getAuthor(),order.getIsbn(),new CheckBox(),new CheckBox(),new CheckBox(),new CheckBox(),order.getPrice()},new Integer(i));
						styleList.add(style);
						copy=true;
					}else{
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
		
		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
		    public String getStyle(Object itemId, Object propertyId) {
		       int row = ((Integer)itemId).intValue();
		       
		       return styleList.get(row-1);

		    }
		});

	}
	
}
