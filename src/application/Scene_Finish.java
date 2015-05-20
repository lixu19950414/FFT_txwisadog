package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Scene_Finish extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static HBox buttons;
	public static Button btnNext;
	public static ButtonNext btnPrev;
	public static Button drawGraph;
	public Scene_Finish(){
		super(backGround, Main.WIDTH, Main.HEIGHT);
	}
	public static void initElements(){
		Scene_Finish.backGround = new BorderPane();
		Scene_Finish.lbl = new Label("THis is finish.");
		Scene_Finish.buttons = new HBox(3);
		Scene_Finish.drawGraph = new Button("CreateGraph");
		drawGraph.setOnAction((ActionEvent e)->{
			Stage stage = new Stage();
			Scene scene = new Scene(new CurveFittedAreaChartApp().createContent());
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.show();
		});
		Scene_Finish.buttons.setAlignment(Pos.BOTTOM_RIGHT);
    	Scene_Finish.backGround.setCenter(drawGraph);
    	Main.scene_Finish = new Scene_Finish();
    	Main.scene_Finish.getStylesheets().add(Main.backGroundCSS);
	}
	public static void initButtons(){
		Scene_Finish.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.scene_Choose);
		Scene_Finish.btnNext = new Button("Finish");
		Scene_Finish.btnNext.setOnAction((ActionEvent e)->{
			System.exit(0);
		});
		Scene_Finish.btnNext.setDefaultButton(true);
		Scene_Finish.buttons.getChildren().addAll(Scene_Finish.btnPrev, Scene_Finish.btnNext);
		Scene_Finish.backGround.setBottom(Scene_Finish.buttons);
	}
}