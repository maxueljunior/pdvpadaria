package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class PesquisaItemVendaListController implements Initializable{
	
	private ItemService service;
	
	private Integer idItem;
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private TextField txtPesquisar;
	
	@FXML
	private TableView<Item> tableViewItem;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Item, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<Item, Double> tableColumnPreco;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnQnt;
	
	private ObservableList<Item> obsList;
	
	public void onBtnPesquisar() {
		System.out.println("pesquisar...");
	}
	
	public void setItemService(ItemService service) {
		this.service = service;
	}
	
	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço nullo");
		}

		List<Item> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewItem.setItems(obsList);
	}
	
	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnQnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

	}
	
	@FXML
	public void itensVendasClicked(MouseEvent e) {

		int i = tableViewItem.getSelectionModel().getSelectedIndex();

		Item v = (Item) tableViewItem.getItems().get(i);
		
	}
	
}
