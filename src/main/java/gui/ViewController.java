package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import pdv.application.Program;

public class ViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemEstoque;
	
	@FXML
	private MenuItem menuItemVendas;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemEstoque() {
		loadView("/gui/EstoqueList.fxml");
	}
	
	@FXML
	public void onMenuItemVendas() {
		System.out.println("vendas");
	}
	
	@FXML
	public void onMenuItemAbout() {
		loadView("/gui/About.fxml");
	}
	
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Program.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
