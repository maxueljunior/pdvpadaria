package gui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.excpetions.ValidationException;
import pdv.model.entities.Cliente;
import pdv.model.services.ClienteService;

public class ClientListController implements Initializable {

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
	
	@FXML
	private Label lbNomeError;

	@FXML
	private Label lbTelefoneError;

	private ObservableList<Cliente> obsList;

	public void setService(ClienteService service) {
		this.service = service;
	}

	@FXML
	public void btnSalvarAction() {
		try {
			Cliente c = new Cliente();
			lbNomeError.setText("");
			lbTelefoneError.setText("");
			c = getFormData();

			service.saveOrUpdate(c);
			updateTableView();
			tableViewCliente.refresh();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (RuntimeException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	public void btnAtualizarAction() {
		try {
			Cliente c = new Cliente();
			lbNomeError.setText("");
			lbTelefoneError.setText("");
			c = getFormData();
			
			c.setId(Long.parseLong(txtId.getText()));
			service.saveOrUpdate(c);
			updateTableView();
			tableViewCliente.refresh();

			txtId.setText("");
			txtNome.setText("");
			txtTelefone.setText("");
			tableViewCliente.getSelectionModel().clearSelection();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (RuntimeException e) {
			Alerts.showAlert("Erro ao Atualizar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	public void btnDeletarAction() {
		Cliente c = new Cliente();

		c.setId(Long.parseLong(txtId.getText()));
		c.setName(txtNome.getText());
		c.setTelefone(txtTelefone.getText());

		service.remove(c);
		updateTableView();
		tableViewCliente.refresh();

		txtId.setText("");
		txtNome.setText("");
		txtTelefone.setText("");
		tableViewCliente.getSelectionModel().clearSelection();
	}

	@FXML
	public void onClickCliente(MouseEvent e) {

		int i = tableViewCliente.getSelectionModel().getSelectedIndex();

		Cliente cliente = (Cliente) tableViewCliente.getItems().get(i);

		txtId.setText(String.valueOf(cliente.getId()));
		txtNome.setText(cliente.getName());
		txtTelefone.setText(cliente.getTelefone());
		
		lbNomeError.setText("");
		lbTelefoneError.setText("");

	}

	public void updateTableView() {
		if (service == null) {
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

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {
			lbNomeError.setText(errors.get("name"));
		}
		if (fields.contains("telefone")) {
			lbTelefoneError.setText(errors.get("telefone"));
		}
	}

	private Cliente getFormData() {

		Cliente obj = new Cliente();

		ValidationException exception = new ValidationException("Validation error");

		if (txtNome.getText() == "" || txtNome.getText().trim().equals("")) {
			exception.addError("name", "O Nome está vazio");
		}
		obj.setName(txtNome.getText().toUpperCase());
		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "O Telefone está vazio");
		}
		obj.setTelefone(txtTelefone.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}
}
