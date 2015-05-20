/* ....Show License.... */
package application;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
/**
 * An area chart that demonstrates curve fitting. Styling is done through CSS.
 */
public class CurveFittedAreaChartApp{
     
    private CurveFittedAreaChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
 
	Integer index = 0;
	double maxY = 0;
	double aveY = 0;
    public Parent createContent() {
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        try {
			FileReader fr = new FileReader("/Users/lixu-mac/Desktop/result.txt");
			BufferedReader br = new BufferedReader(fr);
			String temp;
			try {
				temp = br.readLine();
				temp = br.readLine();
				while((temp = br.readLine()) != null){
					String tt[] = temp.split(",");
					Float tmp1 = Float.parseFloat(tt[0]);
					Float tmp2 = Float.parseFloat(tt[1]);
					double mod = Math.sqrt(tmp1*tmp1 + tmp2*tmp2);
					series.getData().add(new XYChart.Data<Number,Number>(index, mod));
					maxY = mod>maxY?mod:maxY;
					aveY = aveY+mod;
					index++;
					//System.out.println(index);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        aveY = aveY/index*3;
        xAxis = new NumberAxis(0, index, index/10);
        yAxis = new NumberAxis(0, maxY+1, maxY+1/10);
        chart = new CurveFittedAreaChart(xAxis, yAxis);
        chart.setPrefSize(1200, 400);
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        chart.getData().add(series);
        String curveFittedChartCss = CurveFittedAreaChartApp.class.getResource("CurveFittedAreaChart.css").toExternalForm();
        chart.getStylesheets().add(curveFittedChartCss);
        return chart;
    }
}