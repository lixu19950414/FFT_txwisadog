package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Scene_Choose extends Scene {
	public static BorderPane backGround;
	public static Label lbl;
	public static Label lbl2;
	public static HBox buttons;
	public static HBox fileChooserHBox;
	public static VBox allStuff;
	public static ButtonCalculate btnNext;
	public static ButtonNext btnPrev;
	public static TextField tf;
	public static FileChooser fc;
	public static Button btn;
	public static Button btnReset;
	public static RecorderButton playBack;
	public static HBox controls;
	public static RecorderButton recorderButton;
	
	public Scene_Choose(){
		super(backGround, Main.WIDTH, Main.HEIGHT);
	}
	public static void initElements(){
		Scene_Choose.backGround = new BorderPane();
		Scene_Choose.lbl = new Label("Now, you need to choose a file providing the sourse data.");
		Scene_Choose.lbl2 = new Label("Or just click \"Record\" to capture your voice as the input.");
		Scene_Choose.buttons = new HBox(3);
		Scene_Choose.fileChooserHBox = new HBox(10);
		Scene_Choose.allStuff = new VBox(20);
		Scene_Choose.tf = new TextField();
		Scene_Choose.fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Text(*.txt)", "*.txt"));
		Scene_Choose.btn = new Button("Browse");
		Scene_Choose.btnReset = new Button("Reset");

		
		
		//录音按钮的设置
		Scene_Choose.recorderButton = new RecorderButton();
		recorderButton.setText("Record");
		Scene_Choose.playBack = new RecorderButton();
		playBack.setText("PlayBack");
		playBack.setDisable(true);
		Scene_Choose.controls = new HBox(10);
		Scene_Choose.controls.getChildren().addAll(Scene_Choose.recorderButton);
		Scene_Choose.controls.setAlignment(Pos.BOTTOM_CENTER);
		playBack.setPlayBack();
		
		Scene_Choose.btn.setOnAction((ActionEvent e)->{
			Main.choosenFile = fc.showOpenDialog(Main.mainStage);
			if(Main.choosenFile!=null){
				btnNext.setDisable(false);
				tf.setText(Main.choosenFile.getAbsolutePath());
				Scene_Choose.recorderButton.setDisable(true);
			}
			else{
				btnNext.setDisable(true);
				tf.setText("");
			}
		});
		Scene_Choose.btnReset.setOnAction((ActionEvent e)->{
			Scene_Choose.btn.setDisable(false);
			Main.filePath = null;
			Scene_Choose.tf.setText("");
			Main.inRealArray = null;
			Main.inImagArray = null;
			Main.outImagArray = null;
			Main.outRealArray = null;
			Scene_Choose.btnNext.setText("Caculate");
			Scene_Choose.btnNext.setDisable(true);
			Scene_Choose.btnPrev.setDisable(false);
			Scene_Choose.controls.getChildren().remove(playBack);
			Scene_Choose.recorderButton.setDisable(false);
			Scene_Choose.tf.setDisable(false);
		});
		
		
		Scene_Choose.tf.setPrefSize(500, TextField.USE_COMPUTED_SIZE);
		tf.setEditable(false);
		Scene_Choose.buttons.setAlignment(Pos.BOTTOM_RIGHT);
		Scene_Choose.fileChooserHBox.getChildren().addAll(tf,btn);
		Scene_Choose.allStuff.getChildren().addAll(lbl,fileChooserHBox,lbl2,controls);
		Scene_Choose.fileChooserHBox.setAlignment(Pos.BOTTOM_CENTER);
    	Scene_Choose.backGround.setCenter(Scene_Choose.allStuff);
		Scene_Choose.allStuff.setAlignment(Pos.CENTER);
    	Main.scene_Choose = new Scene_Choose();
    	Main.scene_Choose.getStylesheets().add(Main.backGroundCSS);
	}
	public static void initButtons(){
		Scene_Choose.btnPrev = new ButtonNext("Prev", Main.mainStage, Main.scene_Introduction);
		Scene_Choose.btnNext = new ButtonCalculate("Calculate", Main.mainStage, Main.scene_Finish);
		Scene_Choose.btnNext.setDisable(true);
		Scene_Choose.buttons.getChildren().addAll(Scene_Choose.btnReset,Scene_Choose.btnPrev, Scene_Choose.btnNext);
		Scene_Choose.backGround.setBottom(Scene_Choose.buttons);
	}
}
