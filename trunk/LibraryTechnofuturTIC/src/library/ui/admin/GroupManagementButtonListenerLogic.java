package library.ui.admin;

import java.util.List;

import library.dao.GroupDao;
import library.dao.UserDao;
import library.domain.Group;
import library.domain.User;
import library.navigator7.MyApplication;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.navigator7.NavigableApplication;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
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
	@Autowired  UserDao userDao;

	public void execute(final VerticalLayout dynamicLayout){

		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(true);

		VerticalLayout leftSide = new VerticalLayout();
		leftSide.setSpacing(true);

		final VerticalLayout rightSide = new VerticalLayout();
		leftSide.setSpacing(true);

		mainLayout.addComponent(leftSide);
		mainLayout.addComponent(rightSide);

		dynamicLayout.addComponent(mainLayout);

		////////////////////////////////////////////////////////////////////////
		//LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		//LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		//LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		//LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		//LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		////////////////////////////////////////////////////////////////////////
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

		leftSide.addComponent(tableButtonLayout);

		final VerticalLayout formVerticalLayout = new VerticalLayout();
		formVerticalLayout.setSpacing(true);

		leftSide.addComponent(formVerticalLayout);

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
		final Button cancle = new Button("Annuler");
		cancle.addStyleName("big");

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

		cancle.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				formVerticalLayout.removeAllComponents();
			}
		});

		createGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				formVerticalLayout.removeAllComponents();

				name.setValue("");
				creationDate.setValue("");
				closingDate.setValue("");

				codeGroup.setValue("code : ");

				formVerticalLayout.addComponent(name);
				formVerticalLayout.addComponent(creationDate);
				formVerticalLayout.addComponent(closingDate);
				formVerticalLayout.addComponent(students);
				formVerticalLayout.addComponent(codeGroup);

				HorizontalLayout createAndModifyHorizontalLayout = new HorizontalLayout();
				createAndModifyHorizontalLayout.setSpacing(true);
				createAndModifyHorizontalLayout.addComponent(saveCreate);
				createAndModifyHorizontalLayout.addComponent(cancle);

				formVerticalLayout.addComponent(createAndModifyHorizontalLayout);

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

					formVerticalLayout.removeAllComponents();

					formVerticalLayout.addComponent(name);
					formVerticalLayout.addComponent(creationDate);
					formVerticalLayout.addComponent(closingDate);
					formVerticalLayout.addComponent(students);

					HorizontalLayout createAndModifyHorizontalLayout = new HorizontalLayout();
					createAndModifyHorizontalLayout.setSpacing(true);
					createAndModifyHorizontalLayout.addComponent(saveModify);
					createAndModifyHorizontalLayout.addComponent(cancle);

					formVerticalLayout.addComponent(createAndModifyHorizontalLayout);

				}


			}
		});

		deletGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				formVerticalLayout.removeAllComponents();

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


		////////////////////////////////////////////////////////////////////////	
		//RighSide////RighSide////RighSide////RighSide////RighSide////RighSide//
		//RighSide////RighSide////RighSide////RighSide////RighSide////RighSide//
		//RighSide////RighSide////RighSide////RighSide////RighSide////RighSide//
		//RighSide////RighSide////RighSide////RighSide////RighSide////RighSide//
		//RighSide////RighSide////RighSide////RighSide////RighSide////RighSide//
		////////////////////////////////////////////////////////////////////////

		final Table tableUser  = new Table();
		tableUser.addStyleName("big strong");
		tableUser.setSelectable(true);
		tableUser.setEditable(true);
		tableUser.setImmediate(true);
		
		tableUser.setTableFieldFactory(new ImmediateFieldFactory());



		tableGroup.addListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				Group g = (Group)tableGroup.getValue();
				tableUser.setPageLength(g.getStudents());

				List<User> userList = userDao.getUsersByGroupName(g.getName());

				while(userList.size()<g.getStudents()){
					User u = new User();
					u.setEmail("");
					u.setFirstName("");
					u.setLastName("");

					userList.add(u);
				}

				BeanItemContainer<User> bicu = new BeanItemContainer<User>(User.class, userList);
						
				tableUser.setContainerDataSource(bicu);
						
				
				tableUser.setVisibleColumns(new Object[]{"lastName", "firstName", "email"});
				tableUser.setColumnHeaders(new String[]{"NOM", "PRENOM", "EMAIL"});

				rightSide.removeAllComponents();
				rightSide.addComponent(tableUser);			

			}

		});	

	}
	
	public class ImmediateFieldFactory extends DefaultFieldFactory {
	    public Field createField(Container container,
	                             Object itemId,
	                             Object propertyId,
	                             Component uiContext) {
	        // Let the DefaultFieldFactory create the fields...
	        Field field = super.createField(container, itemId,
	                                        propertyId, uiContext);
	        
	        // ...and just set them as immediate.
	        ((AbstractField)field).setImmediate(true);
	      
	        
	        
	        
	        return field;
	    }
	}

}
