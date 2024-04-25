package Entities;

import DTO.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Population {
    private List<Individual> listofIndividuals;
    private List<Integer> listOfAttacks;

    public Population() {
        listofIndividuals = new ArrayList<>();
        listOfAttacks = new ArrayList<>();
    }

    public List<Individual> getListofIndividuals() {
        return listofIndividuals;
    }

    public void createPopulation() {
        for(int i = 0; Data.pop > i; i++) {
            listofIndividuals.add(new Individual(Data.n));
        }
    }

    public List<Integer> getListOfAttacks() {
        return listOfAttacks;
    }

    public double evaluatePopulation() {
        listOfAttacks.clear();
        for(Individual individual : listofIndividuals) {
            listOfAttacks.add(individual.evaluate());
        }

        OptionalDouble avarage = listOfAttacks
                .stream()
                .mapToDouble(a -> a).
                average();

        return avarage.isPresent() ? avarage.getAsDouble() : 0;
    }

    @Override
    public String toString() {
        return "Population { \n" +
                "  population X = " + listofIndividuals +
                " } \n";
    }
}
