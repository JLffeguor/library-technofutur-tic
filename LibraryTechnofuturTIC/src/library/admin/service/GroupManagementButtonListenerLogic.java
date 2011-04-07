package library.admin.service;

import java.util.List;

import library.dao.GroupDao;
import library.domain.Group;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Service
public class GroupManagementButtonListenerLogic {
	
	@Autowired	GroupDao groupDao;
	@Autowired	GroupService groupService;
	
	public void execute(final Layout menu, final Layout dynamicLayout){
		Button createGroup = new Button("Créer un group");
		Button modifyGroup = new Button("Modifier un group");
		Button deletGroup = new Button("Supprimer un group");

		VerticalLayout menuWrapper = new VerticalLayout();
		menuWrapper.setSpacing(true);
		menuWrapper.setWidth("100%");
		
		menuWrapper.addComponent(createGroup);
		menuWrapper.addComponent(modifyGroup);
		menuWrapper.addComponent(deletGroup);
		
		menu.addComponent(menuWrapper);

		createGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
			
				dynamicLayout.removeAllComponents();
				
				final TextField creationDate = new TextField("Date de creation");
				final TextField closingDate = new TextField("Date de fin de commande");
				final TextField name = new TextField("Nom");
				final Label code = new Label();
				code.setCaption("...");
				
				
				Button save = new Button("Valider");
				save.addListener(new Button.ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						Group group = new Group();
						group.setName((String)name.getValue());
						group.setCreationDate((String)closingDate.getValue());
						group.setClosingDate((String)closingDate.getValue());
						group.setClosed(false);
						
						System.out.println("start");
						groupDao.createGroup(group);
						System.out.println("end");
						
						code.setValue(groupService.generatedCode(group));

					}
				});
				
				VerticalLayout dynamicLayoutWrapper = new VerticalLayout();
				dynamicLayoutWrapper.setSpacing(true);
				dynamicLayoutWrapper.setWidth("100%");
				
				dynamicLayoutWrapper.addComponent(creationDate);
				dynamicLayoutWrapper.addComponent(closingDate);
				dynamicLayoutWrapper.addComponent(name);
				dynamicLayoutWrapper.addComponent(code);
				dynamicLayoutWrapper.addComponent(save);
				
				dynamicLayout.addComponent(dynamicLayoutWrapper);
				

			}
		});
		
		modifyGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				

			}
		});

		deletGroup.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
			dynamicLayout.removeAllComponents();
			final Table table = new Table();
			
			table.setSelectable(true);
			table.setImmediate(true);
			
			table.addListener(new Property.ValueChangeListener() {
			    public void valueChange(ValueChangeEvent event) {
			       Group g = (Group)table.getValue();
			 
			    }
			});
			
			
			List<Group> groupList = groupDao.getGroups();
			table.setContainerDataSource( new BeanItemContainer<Group>(Group.class, groupList) );
			table.setPageLength(groupList.size());
			dynamicLayout.addComponent(table);
			}
		});


	}
		
}
	

