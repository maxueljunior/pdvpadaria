package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pdv.model.entities.Item;

public class ItemFormController implements Initializable {
	
	private Item entity;
	
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
	public void onBtnSalvarAction() {
		System.out.println("salvar...");
	}
	
	@FXML
	public void onBtnCancelAction() {
		System.out.println("cancelar...");
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
