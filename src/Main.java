import Service.Evolutionary;
import Utils.ChessboardPlotter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Evolutionary evolutionary = new Evolutionary();

        double[] results = evolutionary.start();
        ChessboardPlotter chessboardPlotter = new ChessboardPlotter();

        try {
            chessboardPlotter.start(results);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}