package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdv.application.Program;
import pdv.model.entities.Cliente;
import pdv.model.entities.Item;
import pdv.model.entities.Vendas;
import pdv.model.services.ClienteService;
import pdv.model.services.VendaItemService;
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
		
		onMenuItemVendas();
		
		Stage stage = (Stage) btnVendaSemEmissao.getScene().getWindow();
		stage.close();
		
		
	}
	
	private void onMenuItemVendas() {
		loadView("/gui/VendasList.fxml", (VendasListController controller) -> {
			controller.setService(new VendasService());
			controller.setServiceItem(new VendaItemService());
			controller.initializeVendas();
		});
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Program.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	public void onBtnVendaComEmissaoAction() {
		Alerts.showAlert("Information", "Venda com emissão da nota fiscal do consumindor ainda em desenvolvimento.",null, AlertType.INFORMATION);
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
	
	@FXML
	public void mouseEntraPesquisar() {
		btnPesquisarCliente.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiPesquisar() {
		btnPesquisarCliente.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	
	@FXML
	public void mouseEntraSemNota() {
		btnVendaSemEmissao.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiSemNota() {
		btnVendaSemEmissao.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	
	@FXML
	public void mouseEntraComNota() {
		btnVendaComEmissao.setStyle("-fx-background-color: white;"
				+"-fx-text-fill:#0000CD;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	@FXML
	public void mouseSaiComNota() {
		btnVendaComEmissao.setStyle("-fx-background-color: #0000CD;"
				+"-fx-text-fill:white;"
				+"-fx-font-size:12px;"
				+"-fx-font-weight: bold");
	}
	
}
