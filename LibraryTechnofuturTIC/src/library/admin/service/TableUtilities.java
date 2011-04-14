package library.admin.service;

import java.util.ArrayList;
import java.util.List;

import library.domain.User;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class TableUtilities {
	
	public static Table createOrderTable(){
		
		List list = new ArrayList();
		for(OrderTableFields otf : OrderTableFields.values()){
			list.add(otf.getFieldsProtperties());
		}

		return createTable(list);
		
	}
	
	public static Table createUserTable(){
		
		List list = new ArrayList();
		for(UserTableFields otf : UserTableFields.values()){
			list.add(otf.getFieldsProtperties());
		}

		return createTable(list);
		
	}

	private static Table createTable(List<Object[]> list){
		
		Table table = new Table();
		
		for(Object[] o : list){
			table.addContainerProperty((String)o[0],(Class)o[1],null);
		}
		
		return table;
		
	}
}
