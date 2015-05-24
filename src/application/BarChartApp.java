/* ....Show License.... */
package application;
 
 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
 
 
/**
 * A chart that displays rectangular bars with heights indicating data values
 * for categories. Used for displaying information when at least one axis has
 * discontinuous or discrete data.
 */
class BarChartApp{
     
    @SuppressWarnings("rawtypes")
	private BarChart chart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Parent createContent() {
        String[] years = {"2^11", "2^12", "2^13", "2^14" , "2^15" , "2^16" , "2^17" , "2^18" , "2^19" , "2^20"};
        xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(years));
        yAxis = new NumberAxis("Average Duration", 0.0d, 0.06d, 0.0030d);
        @SuppressWarnings({ })
		ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(
            new BarChart.Series("Our FFT", FXCollections.observableArrayList(
               new BarChart.Data(years[0], 0.000037d),
               new BarChart.Data(years[1], 0.000129d),
               new BarChart.Data(years[2], 0.000182d),
               new BarChart.Data(years[3], 0.000406d),
               new BarChart.Data(years[4], 0.000865d),
               new BarChart.Data(years[5], 0.002011d),
               new BarChart.Data(years[6], 0.004130d),
               new BarChart.Data(years[7], 0.008955d),
               new BarChart.Data(years[8], 0.023946d),
               new BarChart.Data(years[9], 0.057953d)
            )),
            new BarChart.Series("Mathlab FFT", FXCollections.observableArrayList(
            		new BarChart.Data(years[0], 0.000036d),
                    new BarChart.Data(years[1], 0.000050d),
                    new BarChart.Data(years[2], 0.000088d),
                    new BarChart.Data(years[3], 0.000590d),
                    new BarChart.Data(years[4], 0.000612d),
                    new BarChart.Data(years[5], 0.000707d),
                    new BarChart.Data(years[6], 0.001269d),
                    new BarChart.Data(years[7], 0.003557d),
                    new BarChart.Data(years[8], 0.009530d),
                    new BarChart.Data(years[9], 0.021820d)
            ))
        );
        chart = new BarChart(xAxis, yAxis, barChartData, 5.0d);
        return chart;
    }
}