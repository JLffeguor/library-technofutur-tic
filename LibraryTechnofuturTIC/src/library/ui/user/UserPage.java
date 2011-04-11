package library.ui.user;

import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Page;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Page
@Configurable(preConstruction = true)

public class UserPage extends HorizontalLayout{
	
	private final VerticalLayout loginUserLayout = new VerticalLayout();
	private final VerticalLayout commandNewBookLayout = new VerticalLayout();
	private final VerticalLayout leftBorderLayout = new VerticalLayout();
	private final VerticalLayout rightBorderLayout = new VerticalLayout();
	
	
	public UserPage(){
		
		
		Label commandBookLabel = new Label("Je connais déjà le livre qui m'intèresse");
		TextField commandBookTitle = new TextField ("Titre de l'ouvrage : ");
		TextField commandBookAuthor = new TextField("Auteur(s) : ");
		TextField commandBookIsbn = new TextField("ISBN : ");
		TextField commandBookPrice = new TextField("Prix : ");
		Button addToCart = new Button("Ajouter au panier");
		
		commandNewBookLayout.addComponent(commandBookLabel);
		commandNewBookLayout.addComponent(commandBookTitle);
		commandNewBookLayout.addComponent(commandBookAuthor);
		commandNewBookLayout.addComponent(commandBookIsbn);
		commandNewBookLayout.addComponent(commandBookPrice);
		commandNewBookLayout.addComponent(addToCart);
		
		addComponent(commandNewBookLayout);
		
	}
}
