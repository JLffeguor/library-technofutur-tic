package com.example.librarytechnofuturtic;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class LibrarytechnofuturticApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Librarytechnofuturtic Application");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
