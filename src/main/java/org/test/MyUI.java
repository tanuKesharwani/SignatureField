package org.test;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.Connect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import eu.maxschuster.vaadin.signaturefield.SignatureField;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@Widgetset("MyAppWidgetset")
@Connect(SignatureField.class)
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		VerticalLayout layout = new VerticalLayout();

		// Label and TextFields for user input
		Label instructionLabel = new Label("Sign below:");
		TextField firstNameField = new TextField("First Name");
		TextField lastNameField = new TextField("Last Name");

		// Create a SignatureField
		SignatureField signatureField = new SignatureField();
		signatureField.setWidth("400px");
		signatureField.setHeight("200px");

		// Button to save the signature as an image
		Button saveButton = new Button("Save Signature");
		saveButton.addClickListener(e -> {
			String signatureData = signatureField.getValue();
			if (signatureData != null && !signatureData.isEmpty()) {
				// Show the signature image (Base64 encoded string)
				System.out.println("signature field" + signatureData);
				Notification.show("Signature saved successfully!");
				Image signatureImage = new Image("Your Signature", new StreamResource(() -> {
					return new java.io.ByteArrayInputStream(signatureData.getBytes());
				}, "signature.png"));
				layout.addComponent(signatureImage);
			} else {
				Notification.show("Please provide a signature.");
			}
		});

		// Layout components
		layout.addComponents(instructionLabel, firstNameField, lastNameField, signatureField, saveButton);

		setContent(layout);
	}

	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	public static class MyUIServlet extends VaadinServlet {
	}
}


