package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdv.application.Program;
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class EstoqueListController implements Initializable{
	
	private ItemService service;
	
	@FXML
	private TableView<Item> tableViewItem;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Item, String> tableColumnName;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnQuantidade;
	
	@FXML
	private TableColumn<Item, Double> tableColumnPreco;
	
	@FXML
	private Button btnSalvar;
	
	private ObservableList<Item> obsList;
	
	public void onBtnSalvarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Item obj = new Item();
		
		createDialogForm(obj, "/gui/ItemForm.fxml", parentStage);
	}
	
	public void setService(ItemService service) {
		this.service = service;
	}
	
	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		
		Stage stage = (Stage) Program.getMainScene().getWindow();
		tableViewItem.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if(service==null) {
			throw new IllegalStateException("Serviço nullo");
		}
		
		List<Item> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewItem.setItems(obsList);
	}
	
	private void createDialogForm(Item obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ItemFormController controller = loader.getController();
			controller.setItem(obj);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastrar Item no Estoque");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(true);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	

	
	
}
