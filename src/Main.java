import DTO.Data;
import Service.Evolutionary;
import Utils.ChessboardPlotter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(8,10,100,0.7,0.2,0);
        Evolutionary evolutionary = new Evolutionary();

        double[] results = evolutionary.start(data);
//        System.out.println(result);
        ChessboardPlotter chessboardPlotter = new ChessboardPlotter();

        try {
            chessboardPlotter.start(results);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}