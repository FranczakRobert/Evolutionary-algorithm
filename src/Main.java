import DTO.Data;
import Service.Evolutionary;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(4,2,1000,0.7,0.2,0);
        Evolutionary evolutionary = new Evolutionary();
        evolutionary.start(data);
    }


}