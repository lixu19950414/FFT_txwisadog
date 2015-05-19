package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Scene_Introduction extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static HBox buttons;
	public static ButtonNext btnNext;
	public static ButtonNext btnPrev;
	public Scene_Introduction(){
		super(backGround, Main.WIDTH, Main.HEIGHT-22);
	}
	public static void initElements(){
    	Scene_Introduction.backGround = new BorderPane();
    	Scene_Introduction.lbl = new Label("THis is intruduction.");
    	Scene_Introduction.buttons = new HBox(3);
    	Scene_Introduction.backGround.setCenter(Scene_Introduction.lbl);
    	Main.scene_Introduction = new Scene_Introduction();
	}
	public static void initButtons(){
    	Scene_Introduction.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.welcome_Scene);
    	Scene_Introduction.btnPrev.setDisable(true);
		Scene_Introduction.btnNext = new ButtonNext("Next", Main.mainStage, Main.scene_Choose);
    	Scene_Introduction.buttons.getChildren().addAll(Scene_Introduction.btnPrev, Scene_Introduction.btnNext);
    	Scene_Introduction.backGround.setBottom(Scene_Introduction.buttons);
    	
	}
}
