package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import pdv.model.services.ItemService;
import pdv.model.services.VendaItemService;
import pdv.model.services.VendasService;

public class ViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemEstoque;
	
	@FXML
	private MenuItem menuItemVendas;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemEstoque() {
		loadView("/gui/EstoqueList.fxml", (EstoqueListController controller) -> {
			controller.setService(new ItemService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemVendas() {
		loadView("/gui/VendasList.fxml", (VendasListController controller) -> {
			controller.setService(new VendasService());
			controller.setServiceItem(new VendaItemService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAbout() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Program.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
