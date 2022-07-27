package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	public void onBtnSalvarAction() {
		System.out.println("salvar...");
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	

	
	
}
