package application;

import java.io.BufferedReader;
import java.io.FileReader;







import org.omg.CORBA.FloatSeqHelper;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ButtonCalculate extends Button{
	
	public static boolean status1 = false;
	public static boolean status2 = false;
	public static boolean status3 = false;
	public static boolean flagFinsh = false;
	
	public ButtonCalculate(String string, Stage stage, Scene scene) {
		super(string);
		this.setOnAction((ActionEvent e)->{
			if(this.getText().equals("Next")){
				stage.setScene(scene);
				stage.sizeToScene();
			}
			else{
				Task<Void> task = new Task<Void>() {
					@Override
					public Void call(){
						int i = 0;
						status1 = true;
						Scene_Choose.btnPrev.setDisable(true);
						Scene_Choose.btnReset.setDisable(true);
						Scene_Choose.playBack.setDisable(true);
						Scene_Choose.btnNext.setDisable(true);
						while (true){
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							updateProgress(i, 100);
							if(status3 == true && status2 == true && status3 == true){
								i++;
								updateProgress(i, 100);
								if(i==90){
									break;
								}
							}
							else if(status2 == true && status1 == true && status3 == false){
								i+= 3;
								updateProgress(i, 100);
								if(i == 30){
									status3 = true;
								}
							}
							else if(status1 == true && status2 == false && status3 == false){
								i += 2;
								updateProgress(i, 100);
								if(i == 10){
									status2 = true;
								}
							}
							while(true){
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(flagFinsh == true){
									updateProgress(100, 100);
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Scene_Choose.btnPrev.setDisable(false);
									Scene_Choose.btnReset.setDisable(false);
									Scene_Choose.playBack.setDisable(false);
									Scene_Choose.btnNext.setDisable(false);
									Scene_Choose.progressBar.progressProperty().set(1);
									break;
								}
							}
						}
						return null;
					}
				};
				
				
				
				Scene_Choose.progressBar = new ProgressBar(0);
				Scene_Choose.progressBar.setPrefWidth(400);
				Scene_Choose.progressBar.progressProperty().bind(task.progressProperty());
				Scene_Choose.allStuff.getChildren().add(Scene_Choose.progressBar);
				Thread th = new Thread(task);
				th.setDaemon(true);
				th.start();
				   
				
				Scene_Choose.btnPrev.setDisable(true);
				Scene_Choose.btnReset.setDisable(true);
				Scene_Choose.playBack.setDisable(true);
				Scene_Choose.btnNext.setDisable(true);
				
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
				flagFinsh = true;
			}
		});
		this.setDefaultButton(true);
	}
}
