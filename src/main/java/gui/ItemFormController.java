package gui;

import java.net.URL;
import java.util.ResourceBundle;

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
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class ItemFormController implements Initializable {
	
	private Item entity;
	
	private ItemService service;
	
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
		if(entity == null) {
			throw new IllegalStateException("Entidade nula");
		}
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		
		try {
		entity = getFormData();
		service.saveOrUpdate(entity);
		Utils.currentStage(event).close();
		}
		catch(RuntimeException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Item getFormData() {
		
		Item obj = new Item();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		obj.setQuantidade(Utils.tryParseToInt(txtQuantidade.getText()));
		obj.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
		
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
	
	public void updateFormData() {
		if(entity==null) {
			throw new IllegalStateException("entidade está nula");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtQuantidade.setText(String.valueOf(entity.getQuantidade()));
		txtPreco.setText(String.valueOf(entity.getPreco()));
	}
	
}
