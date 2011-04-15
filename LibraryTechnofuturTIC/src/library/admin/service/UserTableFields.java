package library.admin.service;

import com.vaadin.ui.TextField;

public enum UserTableFields {
	
	LASTNAME("NOM", TextField.class),
	FIRSTNAME("PRENOM", TextField.class);
	
	private String nameColumn;
	private Class classColumn;
	
	private UserTableFields(String nameColumn, Class classColumn){
		this.nameColumn = nameColumn;
		this.classColumn = classColumn;
	}
	
	public Object[] getFieldsProtperties(){
		
		Object[] o = new Object[2];
		o[0] = nameColumn;
		o[1] = classColumn;
		
		return o;
	}
	
}
