package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdv.application.Program;
import pdv.model.entities.Item;
import pdv.model.services.ItemService;

public class EstoqueListController implements Initializable, DataChangeListener {

	private ItemService service;

	@FXML
	private TableView<Item> tableViewItem;

	@FXML
	private TableColumn<Item, Integer> tableColumnId;

	@FXML
	private TableColumn<Item, String> tableColumnName;

	@FXML
	private TableColumn<Item, Integer> tableColumnQuantidade;

	@FXML
	private TableColumn<Item, Double> tableColumnPreco;

	@FXML
	private TableColumn<Item, Item> tableColumnEdit;

	@FXML
	private TableColumn<Item, Item> tableColumnRemove;

	@FXML
	private Button btnSalvar;

	private ObservableList<Item> obsList;

	public void onBtnSalvarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Item obj = new Item();

		createDialogForm(obj, "/gui/ItemForm.fxml", parentStage);
	}

	public void setService(ItemService service) {
		this.service = service;
	}

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

		Stage stage = (Stage) Program.getMainScene().getWindow();
		tableViewItem.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço nullo");
		}

		List<Item> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewItem.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Item obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ItemFormController controller = loader.getController();
			controller.setItem(obj);
			controller.setItemService(new ItemService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastrar Item no Estoque");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(true);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Item, Item>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Item obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ItemForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Item, Item>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Item obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Item obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Deseja mesmo deletar este item?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Serviço nullo");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (RuntimeException e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void onSelect(Item item) {
		// TODO Auto-generated method stub
		
	}

}
