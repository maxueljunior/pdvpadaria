package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pdv.application.Program;
import pdv.model.entities.Item;

public class PesquisaItemVendaListController implements Initializable{
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private TextField txtPesquisar;
	
	@FXML
	private TableView<Item> tableViewItem;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Item, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<Item, Double> tableColumnPreco;
	
	@FXML
	private TableColumn<Item, Integer> tableColumnQnt;
	
	public void onBtnPesquisar() {
		System.out.println("pesquisar...");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	
	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnQnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

	}
}
