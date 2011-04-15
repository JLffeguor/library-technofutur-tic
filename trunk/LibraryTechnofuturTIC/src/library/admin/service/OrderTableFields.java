package library.admin.service;

import com.vaadin.ui.CheckBox;

public enum OrderTableFields {

	LASTNAME("NOM", String.class),
	FIRSTNAME("PRENOM",String.class),
	EMAIL("EMAIL",String.class),
	TITLE("TITRE DU MANUELLE",String.class),
	AUTHOR("AUTEUR",String.class),
	ISBN("ISBN",String.class),
	ORDER("COMMANDE",CheckBox.class),
	SUCCEED("ARRIVE",CheckBox.class),
	TAKEN("ENLEVE",CheckBox.class),
	INALIENABLE("INDISPONIBLE",CheckBox.class),
	PRICE("PRIX",Integer.class);
	
	private String nameColumn;
	private Class classColumn;
	
	private OrderTableFields(String nameColumn, Class classColumn){
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
