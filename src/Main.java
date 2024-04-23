import DTO.Data;
import Entities.Indiviual;
import Service.Evolutionary;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(6,10,5,0.7,0.2,0);
        Evolutionary evolutionary = new Evolutionary();

        Indiviual result = evolutionary.start(data);
    }
}