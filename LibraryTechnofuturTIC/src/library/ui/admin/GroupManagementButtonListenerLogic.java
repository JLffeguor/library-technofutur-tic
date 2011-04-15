package library.ui.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import library.admin.service.TableUtilities;
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
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
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
		Button createGroup = new Button("Créer un groupe");
		createGroup.addStyleName("big");
		Button deletGroup = new Button("Supprimer un groupe");
		deletGroup.addStyleName("big");
		Button modifyGroup = new Button("Modifier un groupe");
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

		tableGroup.addGeneratedColumn("creationDate", new ColumnGenerator() {

			public Component generateCell(Table source, Object itemId, Object columnId) {

				Property prop = source.getItem(itemId).getItemProperty(columnId);

				Date date = (Date) prop.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				Label label = new Label(sdf.format(date));

				return label;
			}
		});

		tableGroup.addGeneratedColumn("closingDate", new ColumnGenerator() {

			public Component generateCell(Table source, Object itemId, Object columnId) {

				Property prop = source.getItem(itemId).getItemProperty(columnId);

				Date date = (Date) prop.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				Label label = new Label(sdf.format(date));

				return label;
			}
		});


		tableGroup.setContainerDataSource(bic);
		tableGroup.setVisibleColumns(new Object[]{"name", "creationDate", "closingDate"});
		tableGroup.setColumnHeaders(new String[]{"NOM DU GROUP", "DATE DE CREATION", "DATE DE CLOTURE"});


		VerticalLayout tableButtonLayout = new VerticalLayout();
		tableButtonLayout.setHeight("100%");
		tableButtonLayout.setSpacing(true);

		tableButtonLayout.addComponent(tableGroup);
		tableButtonLayout.addComponent(buttonsLayout);

		leftSide.addComponent(tableButtonLayout);

		final VerticalLayout formVerticalLayout = new VerticalLayout();
		formVerticalLayout.setSpacing(true);

		leftSide.addComponent(formVerticalLayout);


		final Table tableUser = TableUtilities.createUserTable();
		tableUser.addStyleName("big strong");
		tableUser.setSelectable(true);
		tableUser.setImmediate(true);


		final TextField name = new TextField("Nom du Groupe");


		final DateField creationDate = new DateField("Date de création");
		creationDate.setImmediate(true);
		creationDate.setResolution(DateField.RESOLUTION_DAY);
		final DateField closingDate = new DateField("Date de clotûre");
		closingDate.setImmediate(true);
		closingDate.setResolution(DateField.RESOLUTION_DAY);

		final TextField students = new TextField("Nombres d'étudiants");
		students.addValidator(new IntegerValidator("Vous devez mettre un nombre"));
		students.setImmediate(true);

		final Label codeGroup = new Label();
		codeGroup.addStyleName("h1 color");

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

					group.setCreationDate((Date)creationDate.getValue());
					group.setClosingDate((Date)closingDate.getValue());

					String std = (String)students.getValue();
					Integer i = Integer.valueOf(std);

					group.setStudents(i);

					List<Group> gL = groupDao.getGroupByName(group.getName());
					if(gL.size()==0){

						groupDao.createGroup(group);
						tableGroup.addItem(group);

						groupService.generatedCode(group);

						formVerticalLayout.removeAllComponents();

					}else{
						Application myApp = (MyApplication)NavigableApplication.getCurrent();
						myApp.getMainWindow().showNotification("Ce nom du groupe existe dèjà");
					}
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
				i.getItemProperty("students").setValue(Integer.valueOf((String)students.getValue()));

				Group g = (Group)tableGroup.getValue();

				groupDao.upDateGroup(g);

				tableGroup.select(i);

				formVerticalLayout.removeAllComponents();

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
				students.setValue("12");
				creationDate.setValue(new Date());
				closingDate.setValue(new Date());

				formVerticalLayout.addComponent(name);
				formVerticalLayout.addComponent(creationDate);
				formVerticalLayout.addComponent(closingDate);
				formVerticalLayout.addComponent(students);

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
					myApp.getMainWindow().showNotification("Vous devez selectionner un élément dans la table");
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

							rightSide.removeAllComponents();

						}
					});

				}else{
					myApp.getMainWindow().showNotification("Vous devez selectioner un élément dans la table");
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

		tableGroup.addListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				Group g = (Group)tableGroup.getValue();

				if(g!=null){

					tableUser.removeAllItems();
					tableUser.setPageLength(g.getStudents());

					rightSide.removeAllComponents();

					codeGroup.setValue("Code : "+g.getCode());
					rightSide.addComponent(codeGroup);

					rightSide.addComponent(tableUser);

					fillUserTable(g, tableUser);

				}

			}

		});	

	}

	public void fillUserTable(Group g, Table table){

		List<User> userList = userDao.getUsersByGroupName(g.getName());

		int i = 0;

		while(i<g.getStudents()){

			CustomTextField firstName = new CustomTextField();
			CustomTextField lastName = new CustomTextField();

			if(userList.size()>i){				
				firstName.setValue(userList.get(i).getFirstName());
				lastName.setValue(userList.get(i).getLastName());
			}

			table.addItem(new Object[] {lastName, firstName}, new Integer(i));

			i++;

			UserEntryListener uE = new UserEntryListener(firstName, lastName, g);

			firstName.addListener(uE);
			lastName.addListener(uE);


		}

	}

	public class UserEntryListener implements ValueChangeListener{

		final CustomTextField firstName;
		final CustomTextField lastName;
		final Group group;

		public UserEntryListener(final CustomTextField fN, final CustomTextField lN, final Group g){


			this.firstName = fN;
			this.lastName = lN;
			this.group = g;

			firstName.setPreviousValue((String)firstName.getValue());
			lastName.setPreviousValue((String)lastName.getValue());

		}

		public void valueChange(ValueChangeEvent event) {

			String fN = (String)firstName.getValue();
			String lN = (String)lastName.getValue();

			if((!"".equals(fN)&&(!"".equals(lN)))){

				List<User> uLPr = userDao.getUserByFirstNameAndLastName(firstName.getPreviousValue().trim(), lastName.getPreviousValue().trim());
				List<User>	uLCr = userDao.getUserByFirstNameAndLastName(((String)firstName.getValue()).trim(), ((String)lastName.getValue()).trim());

				if((firstName.getPreviousValue().isEmpty())&&(lastName.getPreviousValue().isEmpty())){

					if((uLCr.size()==0)){

						User u = new User();
						u.setFirstName(fN);
						u.setLastName(lN);
						u.setGroup(group);

						userDao.addUser(u);

						firstName.setPreviousValue((String)firstName.getValue());
						lastName.setPreviousValue((String)lastName.getValue());

					}else{

						firstName.setValue("");
						lastName.setValue("");
						Application myApp = (MyApplication)NavigableApplication.getCurrent();
						myApp.getMainWindow().showNotification("Cet étudiant est déja dans la table");

					}

				}else{

					if((uLPr.size()!=0)&&(uLCr.size()==0)){
						User u = uLPr.get(0);
						u.setFirstName(fN);
						u.setLastName(lN);

						userDao.updateUser(u);

						firstName.setPreviousValue(fN);
						lastName.setPreviousValue(lN);

					}else if((uLCr.size()!=0)&&(uLCr.size()!=0)){

						firstName.setValue(firstName.getPreviousValue());
						lastName.setValue(lastName.getPreviousValue());

						Application myApp = (MyApplication)NavigableApplication.getCurrent();
						myApp.getMainWindow().showNotification("Ces informations correspondent à un autre étudiant dans la table");

					}else if((uLPr.size()==0)&&(uLCr.size()!=0)){

						firstName.setValue("");
						lastName.setValue("");
						Application myApp = (MyApplication)NavigableApplication.getCurrent();
						myApp.getMainWindow().showNotification("Cet étudiant est déja dans la table");

					}

				}
			}
		}
	}

	public class CustomTextField extends TextField{

		private String previousValue;

		public CustomTextField(){
			super();
			setImmediate(true);
		}

		public String getPreviousValue(){
			return this.previousValue;
		}

		public void setPreviousValue(String previousValue){
			this.previousValue = previousValue;
		}

	}

}
