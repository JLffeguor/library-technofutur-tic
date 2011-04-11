package library.ui.admin;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
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

		final Table table  = new Table();
		table.addStyleName("big strong");
		table.setSelectable(true);
		table.setImmediate(true);

		BeanItemContainer<Group> bic = new BeanItemContainer<Group>(Group.class, groupDao.getGroups());


		table.setContainerDataSource(bic);
		table.setVisibleColumns(new Object[]{"name", "creationDate", "closingDate"});
		table.setColumnHeaders(new String[]{"NOM DU GROUP", "DATE DE CREATION", "DATE DE CLOTURE"});


		VerticalLayout tableButtonLayout = new VerticalLayout();
		tableButtonLayout.setSpacing(true);

		tableButtonLayout.addComponent(table);
		tableButtonLayout.addComponent(buttonsLayout);

		dynamicLayout.addComponent(tableButtonLayout);

		final VerticalLayout fieldValidateLayout = new VerticalLayout();
		fieldValidateLayout.setSpacing(true);

		dynamicLayout.addComponent(fieldValidateLayout);
		dynamicLayout.setExpandRatio(fieldValidateLayout, 1);

		final TextField name = new TextField("Nom du Group");
		final TextField creationDate = new TextField("Date de creation");
		final TextField closingDate = new TextField("Date de cloture");

		final Button saveCreate = new Button("Valider");
		saveCreate.addStyleName("big");;
		final Button saveModify = new Button("Valider");
		saveModify.addStyleName("big");

		saveCreate.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				Group group = new Group();
				group.setName((String)name.getValue());
				group.setCreationDate((String)creationDate.getValue());
				group.setClosingDate((String)closingDate.getValue());

				groupDao.createGroup(group);
				table.addItem(group);



			}
		});

		saveModify.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				Group group = new Group();
				group.setName((String)name.getValue());
				group.setCreationDate((String)creationDate.getValue());
				group.setClosingDate((String)closingDate.getValue());

				Item i = table.getItem(table.getValue());
				i.getItemProperty("name").setValue((String)name.getValue());
				i.getItemProperty("creationDate").setValue((String)creationDate.getValue());
				i.getItemProperty("closingDate").setValue((String)closingDate.getValue());

				groupDao.updateGroup(group);

			}
		});

		createGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				name.setValue("");
				creationDate.setValue("");
				closingDate.setValue("");

				fieldValidateLayout.addComponent(name);
				fieldValidateLayout.addComponent(creationDate);
				fieldValidateLayout.addComponent(closingDate);
				fieldValidateLayout.addComponent(saveCreate);

			}
		});

		modifyGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				Item item = table.getItem(table.getValue());

				if(item == null){
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Vous devez selectionner un element dans la table");
				}else{

					Group group = (Group)table.getValue();

					name.setValue(group.getName());
					creationDate.setValue(group.getCreationDate());
					closingDate.setValue(group.getClosingDate());

					fieldValidateLayout.removeAllComponents();

					fieldValidateLayout.addComponent(name);
					fieldValidateLayout.addComponent(creationDate);
					fieldValidateLayout.addComponent(closingDate);
					fieldValidateLayout.addComponent(saveModify);


				}


			}
		});

		deletGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				final Group g = (Group)table.getValue();
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
							table.removeItem(g);

						}
					});


				}else{
					myApp.getMainWindow().showNotification("Vous devez selectioner un element dans le tableau");
				}
			}

		});




	}
}
