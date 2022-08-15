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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pdv.model.entities.Cliente;
import pdv.model.services.ClienteService;

public class ClientListController implements Initializable{
	
	private ClienteService service;
	
	@FXML
	private TableView<Cliente> tableViewCliente;
	
	@FXML
	private TableColumn<Cliente, Long> tableColumnId;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnNome;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtTelefone;

	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnAtualizar;
	
	@FXML
	private Button btnDeletar;
	
	private ObservableList<Cliente> obsList;
	
	public void setService(ClienteService service) {
		this.service = service;
	}
	
	@FXML
	public void btnSalvarAction() {
		Cliente c = new Cliente();

		c.setName(txtNome.getText());
		c.setTelefone(txtTelefone.getText());
		
		service.saveOrUpdate(c);
		updateTableView();
		tableViewCliente.refresh();
	}
	
	@FXML
	public void btnAtualizarAction() {
		
		Cliente c = new Cliente();
		
		c.setId(Long.parseLong(txtId.getText()));
		c.setName(txtNome.getText());
		c.setTelefone(txtTelefone.getText());
		
		service.saveOrUpdate(c);
		updateTableView();
		tableViewCliente.refresh();
	}
	
	@FXML
	public void btnDeletarAction() {
		System.out.println("Deletar");
	}
	
	@FXML
	public void onClickCliente(MouseEvent e) {

		int i = tableViewCliente.getSelectionModel().getSelectedIndex();
		
		Cliente cliente = (Cliente) tableViewCliente.getItems().get(i);
		
		txtId.setText(String.valueOf(cliente.getId()));
		txtNome.setText(cliente.getName());
		txtTelefone.setText(cliente.getTelefone());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço nullo");
		}
		
		List<Cliente> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewCliente.setItems(obsList);
	}
	
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

}
