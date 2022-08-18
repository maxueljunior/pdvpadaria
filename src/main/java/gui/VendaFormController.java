package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdv.model.entities.Cliente;
import pdv.model.entities.Item;
import pdv.model.entities.Vendas;
import pdv.model.services.ClienteService;
import pdv.model.services.VendasService;

public class VendaFormController implements Initializable, DataChangeListener{
	
	private Long numeroVendas;
	
	private Cliente cliente;
	
	@FXML
	private Label lbCliente;
	
	@FXML
	private Button btnPesquisarCliente;
	
	@FXML
	private Button btnVendaSemEmissao;
	
	@FXML
	private Button btnVendaComEmissao;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TextField txtCpfCliente;
	
	public Long getNumeroVendas() {
		return numeroVendas;
	}

	public void setNumeroVendas(Long numeroVendas) {
		this.numeroVendas = numeroVendas;
	}

	@FXML
	public void onBtnPesquisarClienteAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		
		createDialogForm("/gui/PesquisarClientVendaList.fxml", parentStage);
		
	}

	@FXML
	public void onBtnVendaSemEmissaoAction() {
		
		VendasService service = new VendasService();
		Vendas v = new Vendas();
		
		v = service.findVendasById(numeroVendas);
		v.setCliente(cliente);
		
		service.saveOrUpdate(v);
		
		Alerts.showAlert("Venda Concluida", null, "A Venda de numero " + numeroVendas + " foi concluida com sucesso!!", AlertType.CONFIRMATION);
	}
	
	@FXML
	public void onBtnVendaComEmissaoAction() {
		System.out.println("venda com emissão de nfc-e");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			PesquisarClientVendaListController controller = loader.getController();
			controller.setService(new ClienteService());
			controller.updateTableView();
			controller.subscribeDataChangeListener(this);
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Painel");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(true);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void initializeFormVendas() {
		lbCliente.setText("Conclusão de Venda nº " + this.numeroVendas);
	}

	@Override
	public void onDataChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelect(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelectCliente(Cliente cliente) {
		this.cliente = cliente;
		txtNomeCliente.setText(cliente.getName());
		txtTelefoneCliente.setText(cliente.getTelefone());
	}
	
}
