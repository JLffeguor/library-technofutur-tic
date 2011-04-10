package library.admin.service;

import com.vaadin.ui.CheckBox;

public enum BookTableFields {
	TITLE("TITRE DU MANUEL", String.class),
	AUTEUR("AUTEUR",String.class),
	ISBN("ISBN",String.class);

	
	private String nameColumn;
	private Class classColumn;
	
	private BookTableFields(String nameColumn, Class classColumn){
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
