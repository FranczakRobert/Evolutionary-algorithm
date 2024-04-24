package Utils;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;

public class ChessboardPlotter {
    public void start(double[] y) throws IOException {
        XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(1200).height(800).build();
        chart.getStyler().setYAxisMin((double) 0);
        chart.getStyler().setYAxisMax(35.0);
        XYSeries series = chart.addSeries("Zmiennosc\n wartosci \nfunkcji\n przystosowania\n najlepszego\n osobnika", null, y);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper<XYChart>(chart).displayChart();


    }
}
