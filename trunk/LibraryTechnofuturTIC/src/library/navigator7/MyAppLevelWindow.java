package library.navigator7;

import org.vaadin.navigator7.window.HeaderFooterFluidAppLevelWindow;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class MyAppLevelWindow extends HeaderFooterFluidAppLevelWindow {

	@Override
	protected Component createHeader() {
		// TODO Auto-generated method stub
		return new HorizontalLayout();
	}
	
	@Override
	protected ComponentContainer createComponents() {
		ComponentContainer cc = super.createComponents();
		cc.setHeight("100%");
		
		VerticalLayout v = (VerticalLayout)this.getContent();
		v.setExpandRatio(cc, 1);
		
		return cc;
	}

	@Override
	protected Component createFooter() {
		// TODO Auto-generated method stub
		return new HorizontalLayout();
	}
	

    @Override
    protected Layout createMainLayout(){
    	Layout mainLayout = super.createMainLayout();
    	mainLayout.setHeight("100%");
    	return mainLayout;
    }

	

}
