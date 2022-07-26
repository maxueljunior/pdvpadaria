package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.excpetions.ValidationException;
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class ItemFormController implements Initializable {

	private Item entity;

	private ItemService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtPreco;

	@FXML
	private Label lbErrorId;

	@FXML
	private Label lbErrorName;

	@FXML
	private Label lbErrorQuantidade;

	@FXML
	private Label lbErrorPreco;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	@FXML
	public void onBtnSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entidade nula");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço nulo");
		}

		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (RuntimeException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Item getFormData() {

		Item obj = new Item();

		ValidationException exception = new ValidationException("Validation error");

		if (txtId.getText() == "") {
			exception.addError("id", "O Codigo está vazio");
		}
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O nome do produto está vazio");
		}
		obj.setName(txtName.getText().toUpperCase());

		if (txtQuantidade.getText() == "" || txtQuantidade.getText() == "0") {
			exception.addError("quantidade", "A quantidade está vazia ou zerada");
		}
		obj.setQuantidade(Utils.tryParseToInt(txtQuantidade.getText()));

		if (txtPreco.getText() == "" || txtPreco.getText() == "0.00") {
			exception.addError("preco", "O preco está vazio ou zerado");
		}
		obj.setPreco(Utils.tryParseToDouble(txtPreco.getText()));

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initNodes();
	}

	private void initNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldInteger(txtQuantidade);
		Constraints.setTextFieldDouble(txtPreco);
	}

	public void setItem(Item entity) {
		this.entity = entity;
	}

	public void setItemService(ItemService service) {
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("entidade está nula");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtQuantidade.setText(String.valueOf(entity.getQuantidade()));
		txtPreco.setText(String.valueOf(entity.getPreco()));
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("id")) {
			lbErrorId.setText(errors.get("id"));
		}
		if(fields.contains("name")) {
			lbErrorName.setText(errors.get("name"));
		}
		if(fields.contains("quantidade")) {
			lbErrorQuantidade.setText(errors.get("quantidade"));
		}
		if(fields.contains("preco")) {
			lbErrorPreco.setText(errors.get("preco"));
		}

	}
	
	@FXML
	public void mouseEntraSalvar() {
		btnSalvar.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiSalvar() {
		btnSalvar.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	
	@FXML
	public void mouseEntraCancelar() {
		btnCancelar.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiCancelar() {
		btnCancelar.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}

}
