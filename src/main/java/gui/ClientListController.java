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
	
	@FXML
	public void btnSalvarAction() {
		System.out.println("Salvar");
	}
	
	@FXML
	public void btnAtualizarAction() {
		System.out.println("Atualizar");
	}
	
	@FXML
	public void btnDeletarAction() {
		System.out.println("Deletar");
	}
	
	public void setService(ClienteService service) {
		this.service = service;
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
