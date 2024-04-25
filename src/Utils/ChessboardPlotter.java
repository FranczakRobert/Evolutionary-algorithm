package Utils;

import DTO.GraphData;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessboardPlotter {
    public void start(GraphData data) throws IOException {
        double[] best = data.listOfBestEvaluatedValueFromPop().stream().mapToDouble(Double::doubleValue).toArray();
        double[] avarage = data.listOfAvarageEvaluatedValueFromPop().stream().mapToDouble(Double::doubleValue).toArray();

        List<XYChart> charts = new ArrayList<XYChart>();

        XYChart wykres1 = new XYChartBuilder().xAxisTitle("Liczba populacji").yAxisTitle("Liczba ataków").width(600).height(400).build();
        wykres1.getStyler().setYAxisMin((double) 0);
        wykres1.getStyler().setYAxisMax(20.0);

        XYSeries series = wykres1.addSeries("Zmiennosc\n wartosci \nfunkcji\n przystosowania\n najlepszego\n osobnika", null, best);
        series.setMarker(SeriesMarkers.NONE);

        XYChart wykres2 = new XYChartBuilder().xAxisTitle("Liczba populacji").yAxisTitle("Średnia liczba ataków w populacji").width(600).height(400).build();
        wykres2.getStyler().setYAxisMin((double) 0);
        wykres2.getStyler().setYAxisMax(20.0);

        XYSeries series2 = wykres2.addSeries("Zmiennosc\n średniej \n wartosci \nfunkcji\n przystosowania\n w\n populacji", null, avarage);
        series2.setMarker(SeriesMarkers.NONE);

        charts.add(wykres1);
        charts.add(wykres2);


         new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }
}
