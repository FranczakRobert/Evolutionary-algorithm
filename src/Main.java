import DTO.GraphData;
import Service.Evolutionary;
import Utils.ChessboardPlotter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Evolutionary evolutionary = new Evolutionary();

        GraphData results = evolutionary.start();


        if(null != results) {
            ChessboardPlotter chessboardPlotter = new ChessboardPlotter();
                try {
                    chessboardPlotter.start(results);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}