package Entities;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private List<Integer> representIndividual;
    static int IndividualIndex;

    public Individual(int hetmansCounter) {
        generateIndividual(hetmansCounter);
        IndividualIndex++;
    }

    public List<Integer> getRepresentIndividual() {
        return representIndividual;
    }

    public void setRepresentIndividual(List<Integer> representIndividual) {
        this.representIndividual = representIndividual;
    }

    private void generateIndividual(int hetmansCounter){
        representIndividual = new ArrayList<>();

        while (representIndividual.size() != hetmansCounter) {
            int random = (int) (1 + Math.random() * hetmansCounter);
            if(representIndividual.contains(random)){
                continue;
            }
            representIndividual.add(random);
        }
    }


    public int evaluate() {
        int diagonalAttack = 0;

        for(int checkingHetman = 0; checkingHetman < representIndividual.size(); checkingHetman++) {
            int checkingRowA = representIndividual.get(checkingHetman);

            for(int hetman = checkingHetman + 1; hetman < representIndividual.size(); hetman++) {
                int checkingRowB = representIndividual.get(hetman);

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
                "     representIndividual=" + representIndividual +
                "\n }";
    }
}
