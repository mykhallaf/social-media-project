package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
		primaryStage.setTitle("ASU Connect");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
