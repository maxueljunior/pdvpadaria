package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pdv.model.entities.Vendas;
import pdv.model.services.VendasService;

public class VendasListController implements Initializable, DataChangeListener{
	
	private VendasService service;
	
	@FXML
	private Label lbVenda;
	
	public void setService(VendasService service) {
		this.service = service;
	}
	
	@Override
	public void onDataChanged() {
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("SERVIÇO NULLO");
		}
		Vendas venda = new Vendas();
		service.saveOrUpdate(venda);
		lbVenda.setText("Nº da Venda: " + String.valueOf(venda.getId()));
	}

	
	
}
