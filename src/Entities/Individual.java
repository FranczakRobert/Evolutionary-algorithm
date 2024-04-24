package Entities;

import DTO.Data;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private List<Integer> listOfHetmans;

    public Individual(int hetmansCounter) {
        generateIndividual(hetmansCounter);
    }

    public Individual(Individual individual) {
        this(Data.n);
        listOfHetmans = new ArrayList<>(individual.getListOfHetmans());
    }

    public List<Integer> getListOfHetmans() {
        return listOfHetmans;
    }

    public void setListOfHetmans(List<Integer> listOfHetmans) {
        this.listOfHetmans = listOfHetmans;
    }

    private void generateIndividual(int hetmansCounter){
        listOfHetmans = new ArrayList<>();

        while (listOfHetmans.size() != hetmansCounter) {
            int random = (int) (1 + Math.random() * hetmansCounter);
            if(listOfHetmans.contains(random)){
                continue;
            }
            listOfHetmans.add(random);
        }
    }


    public int evaluate() {
        int diagonalAttack = 0;

        for(int checkingHetman = 0; checkingHetman < listOfHetmans.size(); checkingHetman++) {
            int checkingRowA = listOfHetmans.get(checkingHetman);

            for(int hetman = checkingHetman + 1; hetman < listOfHetmans.size(); hetman++) {
                int checkingRowB = listOfHetmans.get(hetman);

                if(Math.abs(checkingRowA - checkingRowB) == Math.abs(checkingHetman - hetman)) {
                    diagonalAttack++;
                }
            }
        }
        return diagonalAttack;
    }


    @Override
    public String toString() {
        return " \n    Individual {\n" +
                "     representIndividual=" + listOfHetmans +
                "\n }";
    }
}
