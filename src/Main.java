import DTO.Data;
import Entities.Individual;
import Service.Evolutionary;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(14,100,1000,0.7,0.2,0);
        Evolutionary evolutionary = new Evolutionary();

        Individual result = evolutionary.start(data);
        System.out.println(result);
    }
}