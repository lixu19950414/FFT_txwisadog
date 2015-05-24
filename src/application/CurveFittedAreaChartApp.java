/* ....Show License.... */
package application;
 
import javafx.scene.Parent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
 
/**
 * An area chart that demonstrates curve fitting. Styling is done through CSS.
 */
public class CurveFittedAreaChartApp{
	 
	public CurveFittedAreaChart chart;
	public NumberAxis xAxis;
	public NumberAxis yAxis;
	public float[] zhenfu;
	public ScrollPane scrollPane = new ScrollPane();
	public VBox vb = new VBox();
	public double maxY = 0;
	public double aveY = 0;
	public Parent createContent() {
		zhenfu = new float[Main.arraySize];
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		//获得数据
		if(Main.arraySize<=Main.graphDistance){
			for(int i = 0 ; i < Main.arraySize ; i++){
				double a = Math.sqrt(Main.outRealArray[i]*Main.outRealArray[i]+Main.outImagArray[i]*Main.outImagArray[i]);
				zhenfu[i] = (float) a;
				maxY = a>maxY?a:maxY;
				aveY += a; 
				series.getData().add(new XYChart.Data<Number,Number>(i, a));
			}
			aveY = aveY/Main.arraySize*Main.beilv;
			float testMaxY = getM(zhenfu, 1.0f, 1.2f);
			xAxis = new NumberAxis(0, Main.arraySize, Main.arraySize/10);
			yAxis = new NumberAxis(0, testMaxY, testMaxY/10);
			chart = new CurveFittedAreaChart(xAxis, yAxis);
			chart.setPrefSize(Main.arraySize<=800?800:Main.arraySize,400);
		}
		else{
			double jiange = (double)(Main.arraySize)/(double)Main.graphDistance;
			for(int i = 0 ; i < Main.graphDistance ; i++){
				int posX = (int)(i*jiange);
				double a = Math.sqrt(Main.outRealArray[posX]*Main.outRealArray[posX]+Main.outImagArray[posX]*Main.outImagArray[posX]);
				zhenfu[i] = (float) a;
				maxY = a>maxY?a:maxY;
				aveY += a;
				series.getData().add(new XYChart.Data<Number,Number>(posX, a));
			}
			aveY = aveY/Main.graphDistance*Main.beilv;
			float testMaxY = getM(zhenfu, 0.999f, 2f);
			xAxis = new NumberAxis(0, Main.arraySize, Main.arraySize/10);
			yAxis = new NumberAxis(0, testMaxY, testMaxY/10);
			chart = new CurveFittedAreaChart(xAxis, yAxis);
			chart.setPrefSize(Main.graphDistance, 400);
		}
		chart.setLegendVisible(false);
		chart.setHorizontalGridLinesVisible(false);
		chart.setVerticalGridLinesVisible(false);
		chart.setAlternativeColumnFillVisible(false);
		chart.setAlternativeRowFillVisible(false);
		chart.getData().add(series);
		String curveFittedChartCss = CurveFittedAreaChartApp.class.getResource("CurveFittedAreaChart.css").toExternalForm();
		chart.getStylesheets().add(curveFittedChartCss);
		vb.getChildren().addAll(scrollPane,chart);
		VBox.setVgrow(scrollPane,Priority.ALWAYS);
		scrollPane.setVmax(Main.graphDistance);
		scrollPane.setHmax(maxY);
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setPrefSize(Main.arraySize>800?1000:800, 422);
		scrollPane.setVvalue(2048);
		scrollPane.setContent(chart);
		return vb;
	}
	
	public static float getM(float []source, float percentage, float zoom) {
		assert(0 < percentage && percentage < 1);
		assert(zoom >= 0);
		int K = (int) (source.length * (1 - percentage) + 1);
		return zoom * selectKthMaxFromArray(source, K);
	}
	
	private static float selectKthMaxFromArray(float []source, int k) {
		assert( k > 0);
		float []maxArray = new float[k];
		for (int i = 0; i < k; i++) {
			maxArray[i] = source[i];
		}
		float cursor = 0, swap = 0;
		for (int i = k; i < source.length; i++) {
			cursor = source[i];
			for (int j = 0; j < k; j++) {
				if (maxArray[j] < cursor) {
					swap = maxArray[j];
					maxArray[j] = cursor;
					cursor = swap;
				}
			}
		}
		float k_thMax = maxArray[0];
		for (int i = 1; i < k; i++) {
			k_thMax = k_thMax > maxArray[i] ? maxArray[i] : k_thMax;
		}
		return k_thMax;
	}
	
}