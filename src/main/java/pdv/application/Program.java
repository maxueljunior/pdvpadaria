package pdv.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Program extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View.fxml"));
			 ScrollPane scrollPane = loader.load();
			 
			 scrollPane.setFitToHeight(true);
			 scrollPane.setFitToWidth(true);
			 
			 Scene mainScene = new Scene(scrollPane);
			 primaryStage.setScene(mainScene);
			 primaryStage.setTitle("PDV Padaria");
			 primaryStage.show();
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
