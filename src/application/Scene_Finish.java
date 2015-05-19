package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Scene_Finish extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static HBox buttons;
	public static ButtonNext btnNext;
	public static ButtonNext btnPrev;
	public Scene_Finish(){
		super(backGround, Main.WIDTH, Main.HEIGHT-22);
	}
	public static void initElements(){
		Scene_Finish.backGround = new BorderPane();
		Scene_Finish.lbl = new Label("THis is choose.");
		Scene_Finish.buttons = new HBox(3);
    	Scene_Finish.backGround.setCenter(Scene_Finish.lbl);
    	Main.scene_Finish = new Scene_Finish();
	}
	public static void initButtons(){
		Scene_Finish.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.scene_Choose);
		Scene_Finish.btnNext = new ButtonNext("Next", Main.mainStage, Main.scene_Choose);
		Scene_Finish.btnNext.setDisable(true);
		Scene_Finish.buttons.getChildren().addAll(Scene_Finish.btnPrev, Scene_Finish.btnNext);
		Scene_Finish.backGround.setBottom(Scene_Finish.buttons);
	}
}