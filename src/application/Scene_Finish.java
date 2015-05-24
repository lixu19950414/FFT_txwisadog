package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
			CurveFittedAreaChartApp cfaca = new CurveFittedAreaChartApp();
			Parent cfac = cfaca.createContent();
			Slider sliderX = new Slider(0, 10, 1);
			sliderX.setShowTickLabels(true);
			sliderX.setShowTickMarks(true);
			sliderX.setStyle("-fx-base: dodgerblue;");
			
			sliderX.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            		double temp_size = Main.arraySize>Main.graphDistance?Main.graphDistance:Main.arraySize;
	            		cfaca.chart.setPrefWidth((double)((double)new_val*temp_size>1000?(double)new_val*temp_size:1000));
	                    System.out.println("old_val = "+old_val+" new_val = "+new_val);
	            }
	        });
			
			
			
			Slider sliderY = new Slider(1, 10, 1);
			sliderY.setShowTickLabels(true);
			sliderY.setShowTickMarks(true);
			sliderY.setStyle("-fx-base: dodgerblue;");
			
			
			sliderY.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            		cfaca.chart.setPrefHeight((double)new_val*400);
	                    System.out.println("old_val = "+old_val+" new_val = "+new_val);
	            }
	        });
			
			
			
			
			VBox vb = new VBox(20);
			vb.getChildren().addAll(cfac,sliderX,sliderY);
			Scene scene = new Scene(vb);
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.setTitle("Frequency Spectrum "+"Sampling Interval: "+String.valueOf((double)Main.arraySize/(double)Main.graphDistance>1?(double)Main.arraySize/(double)Main.graphDistance:1));
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