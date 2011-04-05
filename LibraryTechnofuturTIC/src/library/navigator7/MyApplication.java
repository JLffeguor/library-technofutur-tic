package library.navigator7;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

public class MyApplication extends NavigableApplication {

	 public MyApplication() {
	        setTheme("librarytechnofuturtictheme");
	    }
	
	@Override
	public NavigableAppLevelWindow createNewNavigableAppLevelWindow() {
		return new MyAppLevelWindow();
	}

}
