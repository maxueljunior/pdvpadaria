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
import pdv.model.entities.Cliente;
import pdv.model.services.ClienteService;

public class PesquisarClientVendaListController implements Initializable{
	
	private ClienteService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private TableView<Cliente> tableViewCliente;
	
	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnName;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	private ObservableList<Cliente> obsList;
	
	public void setService(ClienteService service) {
		this.service = service;
	}
	
	@FXML
	private void onBtnPesquisarAction() {
		if(service == null) {
			throw new IllegalStateException("Serviço nullo");
		}
		List<Cliente> list = service.findByDescricao(txtNome.getText());
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
		tableViewCliente.refresh();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço nullo");
		}

		List<Cliente> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListeners(Cliente cliente) {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onSelectCliente(cliente);
		}
	}
	
	@FXML
	public void itensClientesClicked(MouseEvent e) {

		int i = tableViewCliente.getSelectionModel().getSelectedIndex();

		Cliente v = (Cliente) tableViewCliente.getItems().get(i);
		notifyDataChangeListeners(v);
	}
	
	@FXML
	public void EnterIdCliente(KeyEvent event) {

		if (event.getCode() == KeyCode.ENTER) {
			Utils.currentStage(event).close();
		}
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
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
