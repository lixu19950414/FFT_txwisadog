package application;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ButtonCalculate extends Button{
	public ButtonCalculate(String string, Stage stage, Scene scene) {
		super(string);
		this.setOnAction((ActionEvent e)->{
			if(this.getText().equals("Next")){
				stage.setScene(scene);
				stage.sizeToScene();
			}
			else{
				boolean needRead = true;
				
				if(Scene_Choose.btn.isDisabled()){
					needRead = false;
				}
				Scene_Choose.btnPrev.setDisable(true);
				Scene_Choose.btn.setDisable(true);
				Scene_Choose.tf.setDisable(true);
				Scene_Choose.recorderButton.setDisable(true);
				
				if(needRead){
					//开始读文件
					int size = 0;
					try {
						FileReader fr = new FileReader(Main.choosenFile);
						BufferedReader br = new BufferedReader(fr);
						size = Integer.parseInt(br.readLine());
						Main.arraySize = size;
						Main.inRealArray = new float[size];
						Main.inImagArray = new float[size];
						Main.outRealArray = new float[size];
						Main.outImagArray = new float[size];
						for(int i = 0 ; i < size ; i++){
							String tt[] = br.readLine().split(",");
							Main.inRealArray[i] = Float.parseFloat(tt[0]);
							Main.inImagArray[i] = Float.parseFloat(tt[1]);
						}
						br.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else{
					//说明是声音文件已经读取好了
					//实部已经完成了
					//初始化虚部都是0
					while (!Scene_Choose.recorderButton.doneRecord) {
						try {
							Thread.sleep(100);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					Main.arraySize = Main.inRealArray.length;
					Main.inImagArray = new float[Main.arraySize];
					Main.outImagArray = new float[Main.arraySize];
					Main.outRealArray = new float[Main.arraySize];
					
				}
				//开始计算
				InteractWithC.FFT(Main.arraySize, false, Main.inRealArray, Main.inImagArray, Main.outRealArray, Main.outImagArray);
				this.setText("Next");
			}
		});
		this.setDefaultButton(true);
	}
}
