package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scene_Introduction extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static VBox speedText;
	public static HBox buttons;
	public static ButtonNext btnNext;
	public static ButtonNext btnPrev;
	public static Button showSpeed;
	public Scene_Introduction(){
		super(backGround, Main.WIDTH, Main.HEIGHT);
	}
	public static void initElements(){
    	Scene_Introduction.backGround = new BorderPane();
    	Scene_Introduction.lbl = new Label("A fast Fourier transform (FFT) is an algorithm to compute the\n"
    			+ 							"discrete Fourier transform (DFT) and its inverse. Fourier a-\n"
    			+ 							"nalysis converts time (or space) to frequency (or wavenumber)\n"
    			+ 							"and vice versa; an FFT rapidly computes such transformations\n"
    			+ 							"by factorizing the DFT matrix into a product of sparse factors.");
    	Scene_Introduction.buttons = new HBox(3);
    	Scene_Introduction.speedText = new VBox(20);
    	Scene_Introduction.showSpeed = new Button("Performance");
    	Scene_Introduction.showSpeed.setOnAction((ActionEvent e)->{
    		Stage stage = new Stage();
    		BorderPane bp = new BorderPane();
    		bp.setCenter(new BarChartApp().createContent());
    		Scene scene = new Scene(bp);
    		stage.centerOnScreen();
    		stage.setResizable(false);
    		stage.setTitle("Comparation");
    		stage.setScene(scene);
    		stage.show();
    	});
    	Scene_Introduction.speedText.getChildren().addAll(lbl,showSpeed);
    	Scene_Introduction.speedText.setAlignment(Pos.CENTER);
    	Scene_Introduction.buttons.setAlignment(Pos.BOTTOM_RIGHT);
    	Scene_Introduction.backGround.setCenter(Scene_Introduction.speedText);
    	Main.scene_Introduction = new Scene_Introduction();
    	Main.scene_Introduction.getStylesheets().add(Main.backGroundCSS);
	}
	public static void initButtons(){
    	Scene_Introduction.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.welcome_Scene);
    	Scene_Introduction.btnPrev.setDisable(true);
		Scene_Introduction.btnNext = new ButtonNext("Next", Main.mainStage, Main.scene_Choose);
    	Scene_Introduction.buttons.getChildren().addAll(Scene_Introduction.btnPrev, Scene_Introduction.btnNext);
    	Scene_Introduction.backGround.setBottom(Scene_Introduction.buttons);
	}
}
