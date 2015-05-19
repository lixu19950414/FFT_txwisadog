package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Scene_Choose extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static HBox buttons;
	public static ButtonNext btnNext;
	public static ButtonNext btnPrev;
	public Scene_Choose(){
		super(backGround, Main.WIDTH, Main.HEIGHT-22);
	}
	public static void initElements(){
		Scene_Choose.backGround = new BorderPane();
		Scene_Choose.lbl = new Label("THis is choose.");
		Scene_Choose.buttons = new HBox(3);
    	Scene_Choose.backGround.setCenter(Scene_Choose.lbl);
    	Main.scene_Choose = new Scene_Choose();
	}
	public static void initButtons(){
		Scene_Choose.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.scene_Introduction);
		Scene_Choose.btnNext = new ButtonNext("Next", Main.mainStage, Main.scene_Finish);
		Scene_Choose.buttons.getChildren().addAll(Scene_Choose.btnPrev, Scene_Choose.btnNext);
		Scene_Choose.backGround.setBottom(Scene_Choose.buttons);
    	
	}
}
