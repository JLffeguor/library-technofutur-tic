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

/**
 * The method execute is called when the button groupManagement is clicked in the AdminPage
 */
@Service
public class GroupManagementButtonListenerLogic {
	@Autowired
	GroupDao groupDao;
	@Autowired
	GroupService groupService;
	@Autowired
	UserDao userDao;

	public void execute(final VerticalLayout dynamicLayout) {

		// This layout will contain two VerticalLayouts(left and right)
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(true);

		// Here we will have the group table with buttons at the bottom :
		// create/delete/modify
		VerticalLayout leftSide = new VerticalLayout();
		leftSide.setSpacing(true);

		// Here a user table will be displayed with users of a given group
		final VerticalLayout rightSide = new VerticalLayout();
		leftSide.setSpacing(true);

		mainLayout.addComponent(leftSide);
		mainLayout.addComponent(rightSide);

		dynamicLayout.addComponent(mainLayout);

		// //////////////////////////////////////////////////////////////////////
		// LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		// LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		// LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		// LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		// LeftSide////LeftSide////LeftSide////LeftSide////LeftSide////LeftSide//
		// //////////////////////////////////////////////////////////////////////
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

		final Table tableGroup = new Table();

		// ** big strong ** is a style from chameleontheme
		tableGroup.addStyleName("big strong");
		tableGroup.setSelectable(true);
		tableGroup.setImmediate(true);

		// Display all the groups in the database
		BeanItemContainer<Group> bic = new BeanItemContainer<Group>(
				Group.class, groupDao.getGroups());

		// Here creationDate and closingDate will be formated and returned as
		// labels
		tableGroup.addGeneratedColumn("creationDate", new ColumnGenerator() {

			public Component generateCell(Table source, Object itemId,
					Object columnId) {

				Property prop = source.getItem(itemId)
				.getItemProperty(columnId);

				Date date = (Date) prop.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				Label label = new Label(sdf.format(date));

				return label;
			}
		});

		tableGroup.addGeneratedColumn("closingDate", new ColumnGenerator() {

			public Component generateCell(Table source, Object itemId,
					Object columnId) {

				Property prop = source.getItem(itemId)
				.getItemProperty(columnId);

				Date date = (Date) prop.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				Label label = new Label(sdf.format(date));

				return label;
			}
		});

		tableGroup.setContainerDataSource(bic);

		// set visible columns and their order
		tableGroup.setVisibleColumns(new Object[] { "name", "creationDate",
		"closingDate" });
		// set column headers caption
		tableGroup.setColumnHeaders(new String[] { "NOM DU GROUP",
				"DATE DE CREATION", "DATE DE CLOTURE" });

		// It will contain the group table with the buttons on the bottom
		// (create/delete/modify)
		VerticalLayout tableButtonLayout = new VerticalLayout();
		tableButtonLayout.setSpacing(true);

		tableButtonLayout.addComponent(tableGroup);
		tableButtonLayout.addComponent(buttonsLayout);

		leftSide.addComponent(tableButtonLayout);

		// It will contain fields to be filled for the buttons create/modify
		final VerticalLayout formVerticalLayout = new VerticalLayout();
		formVerticalLayout.setSpacing(true);

		leftSide.addComponent(formVerticalLayout);

		/*
		 * It will be displayed on the right VerticalLayout It is positioned
		 * here so it can be accessed from the modify button without being a
		 * instance varible
		 */
		final Table tableUser = TableUtilities.createUserTable();
		tableUser.addStyleName("big strong");
		tableUser.setSelectable(true);
		tableUser.setImmediate(true);

		// Name of group
		final TextField name = new TextField("Nom du Groupe");

		final DateField creationDate = new DateField("Date de création");
		creationDate.setImmediate(true);
		creationDate.setResolution(DateField.RESOLUTION_DAY);
		final DateField closingDate = new DateField("Date de clotûre");
		closingDate.setImmediate(true);
		closingDate.setResolution(DateField.RESOLUTION_DAY);

		// Number of studens
		final TextField students = new TextField("Nombres d'étudiants");
		students.addValidator(new IntegerValidator(
		"Vous devez mettre un nombre"));
		students.setImmediate(true);

		// Code of group
		final Label codeGroup = new Label();
		codeGroup.addStyleName("h1 color");

		// Button for creating a group
		final Button saveCreate = new Button("Valider");
		saveCreate.addStyleName("big");

		// Button for modifying a group
		final Button saveModify = new Button("Valider");
		saveModify.addStyleName("big");

		// Button for canceling operation create/modify
		final Button cancle = new Button("Annuler");
		cancle.addStyleName("big");

		saveCreate.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				if (students.isValid()) {// check if user entered a number on
					// students field

					Group group = new Group();

					// fill group fields
					group.setName((String) name.getValue());
					group.setCreationDate((Date) creationDate.getValue());
					group.setClosingDate((Date) closingDate.getValue());
					String std = (String) students.getValue();
					Integer i = Integer.valueOf(std);

					group.setStudents(i);

					List<Group> gL = groupDao.getGroupByName(group.getName());
					if (gL.size() == 0) {// If there is no other group with the
						// given name, create it

						groupDao.createGroup(group);
						tableGroup.addItem(group);

						groupService.generatedCode(group);

						formVerticalLayout.removeAllComponents();

					} else {// A group already exist with the name entered
						Application myApp = (MyApplication) NavigableApplication
						.getCurrent();
						myApp.getMainWindow().showNotification(
						"Ce nom du groupe existe dèjà");
					}
				} else {// The user a wrong input in the students field
					Application myApp = (MyApplication) NavigableApplication
					.getCurrent();
					myApp.getMainWindow().showNotification(
					"Veulliez entrer le nombre d'étudiants");
				}

			}
		});

		saveModify.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				//Fill the fields with the date from table.getValue() and fire event with Property.setValue() to refresh table
				Item i = tableGroup.getItem(tableGroup.getValue());
				i.getItemProperty("name").setValue((String) name.getValue());
				i.getItemProperty("creationDate").setValue(
						(String) creationDate.getValue());
				i.getItemProperty("closingDate").setValue(
						(String) closingDate.getValue());
				i.getItemProperty("students").setValue(
						Integer.valueOf((String) students.getValue()));

				Group g = (Group) tableGroup.getValue();

				//update group with the new modification
				groupDao.upDateGroup(g);

				tableGroup.select(i);

				//remove fields when button saveModify is clicked
				formVerticalLayout.removeAllComponents();

			}
		});

		cancle.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//End create/delete operation
				formVerticalLayout.removeAllComponents();
			}
		});

		createGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				formVerticalLayout.removeAllComponents();

				//set field values to empty and current date for first display
				name.setValue("");
				students.setValue("12");
				creationDate.setValue(new Date());
				closingDate.setValue(new Date());

				//add fields for create operation
				formVerticalLayout.addComponent(name);
				formVerticalLayout.addComponent(creationDate);
				formVerticalLayout.addComponent(closingDate);
				formVerticalLayout.addComponent(students);

				//display buttons at bottom of fields 
				formVerticalLayout.addComponent(createOpertationAndCancel(saveCreate, cancle));

			}
		});

		modifyGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				Item item = tableGroup.getItem(tableGroup.getValue());

				if (item == null) {

					Application myApp = (MyApplication) NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Vous devez selectionner un élément dans la table");

				} else {

					Group group = (Group) tableGroup.getValue();


					name.setValue(group.getName());
					creationDate.setValue(group.getCreationDate());
					closingDate.setValue(group.getClosingDate());
					students.setValue(group.getStudents());

					formVerticalLayout.removeAllComponents();

					formVerticalLayout.addComponent(name);
					formVerticalLayout.addComponent(creationDate);
					formVerticalLayout.addComponent(closingDate);
					formVerticalLayout.addComponent(students);

					formVerticalLayout.addComponent(createOpertationAndCancel(saveModify, cancle));

				}

			}
		});

		deletGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				formVerticalLayout.removeAllComponents();

				final Group g = (Group) tableGroup.getValue();
				final Application myApp = (MyApplication) NavigableApplication
				.getCurrent();

				if (g != null) {//a group is in selected state in the table

					final Window window = new Window();

					window.center();

					Button button = new Button("Supprimer");

					window.addComponent(button);

					myApp.getMainWindow().addWindow(window);

					//when clicked on button delete group form table and database
					button.addListener(new Button.ClickListener() {

						public void buttonClick(ClickEvent event) {
							myApp.getMainWindow().removeWindow(window);

							groupService.deleteGroup(g);
							tableGroup.removeItem(g);

							rightSide.removeAllComponents();

						}
					});

				} else {
					myApp.getMainWindow().showNotification(//there is no item selected in the table
					"Vous devez selectioner un élément dans la table");
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


		//Each time we click on a item of the group table on the right side there will be a user table displayed with the users of the given group(item)
		tableGroup.addListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				Group g = (Group) tableGroup.getValue();

				if (g != null) {

					tableUser.removeAllItems();
					tableUser.setPageLength(g.getStudents());

					rightSide.removeAllComponents();

					codeGroup.setValue("Code : " + g.getCode());
					rightSide.addComponent(codeGroup);

					rightSide.addComponent(tableUser);

					fillUserTable(g, tableUser);

				}

			}

		});

	}

	/**
	 * It is used only for buttons create/modify to avoid copy paste on their listeners;
	 * Its contains buttons validate and cancel on the bottom of the fields(form)
	 */
	public HorizontalLayout createOpertationAndCancel(Button operation, Button cancle){

		HorizontalLayout h = new HorizontalLayout();
		h.setSpacing(true);
		h.addComponent(operation);
		h.addComponent(cancle);

		return h;
	}

	/**
	 * It fills the user table with TextFields. There is only one instance of UserEntryListner and it is added to the TextFields of the row;
	 */
	public void fillUserTable(Group g, Table table) {

		List<User> userList = userDao.getUsersByGroupName(g.getName());

		int i = 0;

		while (i < g.getStudents()) {

			CustomTextField firstName = new CustomTextField();
			CustomTextField lastName = new CustomTextField();

			if (userList.size() > i) {
				firstName.setValue(userList.get(i).getFirstName());
				lastName.setValue(userList.get(i).getLastName());
			}

			table.addItem(new Object[] { lastName, firstName }, new Integer(i));

			i++;

			UserEntryListner uE = new UserEntryListner(firstName, lastName, g);

			firstName.addListener(uE);
			lastName.addListener(uE);

		}

	}

	/**
	 *It contains logic for persisting or updating a user each time a TextField loses focus 
	 *
	 *		if current fields are not empty
	 *			if previous fields are empty
	 *				than create user if it isn't in table
	 *				else update user with modification
	 *
	 *
	 */
	public class UserEntryListner implements ValueChangeListener {

		final CustomTextField firstName;
		final CustomTextField lastName;
		final Group group;

		public UserEntryListner(final CustomTextField fN,
				final CustomTextField lN, final Group g) {

			this.firstName = fN;
			this.lastName = lN;
			this.group = g;

			firstName.setPreviousValue((String) firstName.getValue());
			lastName.setPreviousValue((String) lastName.getValue());

		}

		public void valueChange(ValueChangeEvent event) {

			String fN = (String) firstName.getValue();
			String lN = (String) lastName.getValue();

			if ((!"".equals(fN) && (!"".equals(lN)))) {// the current values of the TextFields in a table row are not empty

				//check if there is a user with the previous values of the TextFields in a table row
				List<User> uLPr = userDao.getUserByFirstNameAndLastName(
						firstName.getPreviousValue().trim(), lastName
						.getPreviousValue().trim());
				//check if there is a user with the current values of the TextFields in a table row
				List<User> uLCr = userDao.getUserByFirstNameAndLastName(
						((String) firstName.getValue()).trim(),
						((String) lastName.getValue()).trim());

				if ((firstName.getPreviousValue().isEmpty())
						&& (lastName.getPreviousValue().isEmpty())) {//previous values are empty

					if ((uLCr.size() == 0)) {//The user with current values doesn't exist. Create user

						User u = new User();
						u.setFirstName(fN);
						u.setLastName(lN);
						u.setGroup(group);

						userDao.addUser(u);

						//update previous values
						firstName.setPreviousValue((String) firstName
								.getValue());
						lastName.setPreviousValue((String) lastName.getValue());

					} else {//The user with current values does exist. You are trying to copy a user

						firstName.setValue("");
						lastName.setValue("");
						Application myApp = (MyApplication) NavigableApplication
						.getCurrent();
						myApp.getMainWindow().showNotification(
								"Cet étudiant est déja dans la table");

					}

				} else {//previous values are not empty

					if ((uLPr.size() != 0) && (uLCr.size() == 0)) {//if the user with the previous values exists and with current not than update
						User u = uLPr.get(0);
						u.setFirstName(fN);
						u.setLastName(lN);

						userDao.updateUser(u);

						firstName.setPreviousValue(fN);
						lastName.setPreviousValue(lN);

					} else if ((uLCr.size() != 0) && (uLCr.size() != 0)) {//if the user with the previous values exists and with current also: you can not have users with similar names

						firstName.setValue(firstName.getPreviousValue());
						lastName.setValue(lastName.getPreviousValue());

						Application myApp = (MyApplication) NavigableApplication
						.getCurrent();
						myApp.getMainWindow()
						.showNotification(
								"Ces informations correspondent à un autre étudiant dans la table");

					} 

					//					else if ((uLPr.size() == 0) && (uLCr.size() != 0)) {//Can't copy user,
					//
					//						firstName.setValue("");
					//						lastName.setValue("");
					//						Application myApp = (MyApplication) NavigableApplication
					//								.getCurrent();
					//						myApp.getMainWindow().showNotification(
					//								"Cet étudiant est déja dans la table");
					//
					//					}

				}
			}
		}
	}

	/**
	 * It a field to stock previous value.
	 */
	public class CustomTextField extends TextField {

		private String previousValue;

		public CustomTextField() {
			super();
			setImmediate(true);
		}

		public String getPreviousValue() {
			return this.previousValue;
		}

		public void setPreviousValue(String previousValue) {
			this.previousValue = previousValue;
		}

	}

}
