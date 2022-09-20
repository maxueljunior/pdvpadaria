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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pdv.application.Program;
import pdv.model.services.ClienteService;
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
	private MenuItem menuItemClientes;
	
	@FXML
	private MenuItem menuItemRelatorioVendasClientes;
	
	@FXML
	private Button btnEstoque;
	
	@FXML
	private Button btnVendas;
	
	@FXML
	private Button btnClientes;
	
	@FXML
	private Button btnRelatorio;
	
	@FXML
	private Button btnAjuda;
	
	@FXML
	private ImageView imgEstoque;
	
	@FXML
	private ImageView imgVendas;
	
	@FXML
	private ImageView imgClientes;
	
	@FXML
	private ImageView imgRelatorio;
	
	@FXML
	private ImageView imgAjuda;
	
	@FXML
	public void onBtnEstoqueAction() {
		onMenuItemEstoque();
	}
	
	@FXML
	public void onBtnVendasAction() {
		onMenuItemVendas();
	}
	
	@FXML
	public void onBtnClientesAction() {
		onMenuItemClientes();
	}
	
	@FXML
	public void onBtnRelatorioAction() {
		onMenuItemRelatorioVendasClientes();
	}
	
	@FXML
	public void onBtnAjudaAction() {
		onMenuItemAbout();
	}
	
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
			controller.initializeVendas();
		});
	}
	
	@FXML
	public void onMenuItemAbout() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@FXML
	public void onMenuItemClientes() {
		loadView("/gui/ClientList.fxml", (ClientListController controller) ->{
			controller.setService(new ClienteService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemRelatorioVendasClientes() {
		loadView("/gui/ClientVendasRelatorio.fxml", (ClientVendasRelatorioController controller) -> {
			controller.setService(new VendasService());
			controller.loadAssociatedObjects();
		});
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
	
	@FXML
	public void mouseEntraEstoque() {
		btnEstoque.setStyle("-fx-background-color: white");
	}
	@FXML
	public void mouseSaiEstoque() {
		btnEstoque.setStyle("-fx-background-color: #4169E1");
	}
	
	@FXML
	public void mouseEntraVendas() {
		btnVendas.setStyle("-fx-background-color: white");
	}
	@FXML
	public void mouseSaiVendas() {
		btnVendas.setStyle("-fx-background-color: #4169E1");
	}
	
	@FXML
	public void mouseEntraClientes() {
		btnClientes.setStyle("-fx-background-color: white");
	}
	@FXML
	public void mouseSaiClientes() {
		btnClientes.setStyle("-fx-background-color: #4169E1");
	}
	
	@FXML
	public void mouseEntraRelatorio() {
		btnRelatorio.setStyle("-fx-background-color: white");
	}
	@FXML
	public void mouseSaiRelatorio() {
		btnRelatorio.setStyle("-fx-background-color: #4169E1");
	}
	
	@FXML
	public void mouseEntraAjuda() {
		btnAjuda.setStyle("-fx-background-color: white");
	}
	@FXML
	public void mouseSaiAjuda() {
		btnAjuda.setStyle("-fx-background-color: #4169E1");
	}

}
