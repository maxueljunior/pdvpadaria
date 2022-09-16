package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class PesquisaItemVendaListController implements Initializable{
	
	private ItemService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	@FXML
	public void onBtnPesquisar() {
		List<Item> list = service.findByDescricao(txtPesquisar.getText().toUpperCase());
		obsList = FXCollections.observableArrayList(list);
		tableViewItem.setItems(obsList);
		tableViewItem.refresh();
	}
	
	public void setItemService(ItemService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
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
		notifyDataChangeListeners(v);
		
	}
	
	@FXML
	public void EnterIdProduto(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER) {
			Utils.currentStage(event).close();
		}
	}

	private void notifyDataChangeListeners(Item item) {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onSelect(item);
		}
	}
	
	@FXML
	public void mouseEntraPesquisar() {
		btnPesquisar.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiPesquisar() {
		btnPesquisar.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	
}
