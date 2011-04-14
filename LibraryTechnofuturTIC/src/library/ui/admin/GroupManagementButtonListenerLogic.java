package library.ui.admin;

import java.sql.BatchUpdateException;
import java.util.List;

import library.dao.GroupDao;
import library.domain.Group;
import library.navigator7.MyApplication;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.navigator7.NavigableApplication;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Service
public class GroupManagementButtonListenerLogic {
	@Autowired	GroupDao groupDao;
	@Autowired	GroupService groupService;

	public void execute(final VerticalLayout dynamicLayout){
		Button createGroup = new Button("Créer un group");
		createGroup.addStyleName("big");
		Button deletGroup = new Button("Supprimer un group");
		deletGroup.addStyleName("big");
		Button modifyGroup = new Button("Modifier un group");
		modifyGroup.addStyleName("big");


		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);

		buttonsLayout.addComponent(createGroup);
		buttonsLayout.addComponent(deletGroup);
		buttonsLayout.addComponent(modifyGroup);

		
		
		final Table tableGroup  = new Table();
		tableGroup.addStyleName("big strong");
		tableGroup.setSelectable(true);
		tableGroup.setImmediate(true);

		BeanItemContainer<Group> bic = new BeanItemContainer<Group>(Group.class, groupDao.getGroups());


		tableGroup.setContainerDataSource(bic);
		tableGroup.setVisibleColumns(new Object[]{"name", "creationDate", "closingDate"});
		tableGroup.setColumnHeaders(new String[]{"NOM DU GROUP", "DATE DE CREATION", "DATE DE CLOTURE"});


		VerticalLayout tableButtonLayout = new VerticalLayout();
		tableButtonLayout.setSpacing(true);

		tableButtonLayout.addComponent(tableGroup);
		tableButtonLayout.addComponent(buttonsLayout);

		dynamicLayout.addComponent(tableButtonLayout);

		final VerticalLayout fieldValidateLayout = new VerticalLayout();
		fieldValidateLayout.setSpacing(true);

		dynamicLayout.addComponent(fieldValidateLayout);
		dynamicLayout.setExpandRatio(fieldValidateLayout, 1);

		final TextField name = new TextField("Nom du Group");
		final TextField creationDate = new TextField("Date de creation");
		final TextField closingDate = new TextField("Date de cloture");
		final TextField students = new TextField("Nombre d'étudiants");
		students.addValidator(new IntegerValidator("Vous devez mettre un nombre"));
		students.setImmediate(true);
		final Label codeGroup = new Label();
		
		

		final Button saveCreate = new Button("Valider");
		saveCreate.addStyleName("big");
		final Button saveModify = new Button("Valider");
		saveModify.addStyleName("big");

		saveCreate.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				
				if(students.isValid()){
				
				Group group = new Group();
				group.setName((String)name.getValue());
				group.setCreationDate((String)creationDate.getValue());
				group.setClosingDate((String)closingDate.getValue());
				
				
				
				String std = (String)students.getValue();
				Integer i = Integer.valueOf(std);
				

				List<Group> gL = groupDao.getGroupByName(group.getName());
				if(gL.size()==0){
					groupDao.createGroup(group);
				}else{
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Ce nom du groupe existe dèjà");
				}
				
				
				
				
				tableGroup.addItem(group);
				
				codeGroup.setValue("code : "+groupService.generatedCode(group));
				
				}else{
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Veulliez entrer le nombre d'étudiants");
				}

			}
		});

		saveModify.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				
				Item i = tableGroup.getItem(tableGroup.getValue());
				i.getItemProperty("name").setValue((String)name.getValue());
				i.getItemProperty("creationDate").setValue((String)creationDate.getValue());
				i.getItemProperty("closingDate").setValue((String)closingDate.getValue());
				i.getItemProperty("students").setValue((Integer)students.getValue());
				
				Group g = (Group)tableGroup.getValue();
				
				groupDao.upDateGroup(g);
				

			}
		});

		createGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				name.setValue("");
				creationDate.setValue("");
				closingDate.setValue("");
				
				codeGroup.setValue("code : ");
				
				fieldValidateLayout.addComponent(name);
				fieldValidateLayout.addComponent(creationDate);
				fieldValidateLayout.addComponent(closingDate);
				fieldValidateLayout.addComponent(students);
				fieldValidateLayout.addComponent(codeGroup);
				fieldValidateLayout.addComponent(saveCreate);
				

			}
		});

		modifyGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				Item item = tableGroup.getItem(tableGroup.getValue());

				if(item == null){
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Vous devez selectionner un element dans la table");
				}else{

					Group group = (Group)tableGroup.getValue();

					name.setValue(group.getName());
					creationDate.setValue(group.getCreationDate());
					closingDate.setValue(group.getClosingDate());
					students.setValue(group.getStudents());

					fieldValidateLayout.removeAllComponents();

					fieldValidateLayout.addComponent(name);
					fieldValidateLayout.addComponent(creationDate);
					fieldValidateLayout.addComponent(closingDate);
					fieldValidateLayout.addComponent(students);
					fieldValidateLayout.addComponent(saveModify);


				}


			}
		});

		deletGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				final Group g = (Group)tableGroup.getValue();
				final Application myApp = (MyApplication)NavigableApplication.getCurrent();

				if(g!=null){
					
					final Window window = new Window();

					window.center();

					Button button = new Button("Supprimer");

					window.addComponent(button);

					myApp.getMainWindow().addWindow(window);

					button.addListener(new Button.ClickListener() {

						public void buttonClick(ClickEvent event) {
							myApp.getMainWindow().removeWindow(window);

							groupService.deleteGroup(g);
							tableGroup.removeItem(g);

						}
					});


				}else{
					myApp.getMainWindow().showNotification("Vous devez selectioner un element dans le tableau");
				}
			}

		});




	}
	
	
	/** Look for a BatchUpdateException in the causes, in order to display the real cause of that exception. */		
	public void printBatchUpdateException(Throwable throwable) {
		Throwable cause = getCauseException(throwable);
		while (cause != null) {
			if (cause instanceof BatchUpdateException) {
				BatchUpdateException bue = (BatchUpdateException)cause;
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXX NEXT from BatchUpdateException");
				bue.getNextException().printStackTrace();
			}
			cause = getCauseException(cause);
		}				

	}
	
	protected  Throwable getCauseException(Throwable t) {
		if (t instanceof Exception) {
			return t.getCause();
		} else {
			return null;
		}
	}
}
