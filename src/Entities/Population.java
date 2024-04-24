package Entities;

import DTO.Data;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Individual> listofIndividuals;
    private List<Integer> listOfAttacks;

    public Population() {
        listofIndividuals = new ArrayList<>();
        listOfAttacks = new ArrayList<>();
    }

    public Population(Data data) {
        listofIndividuals = new ArrayList<>();
        listOfAttacks = new ArrayList<>();
        createPopulation(data);
    }

    public List<Individual> getListofIndividuals() {
        return listofIndividuals;
    }

    public void createPopulation(Data data) {
        for(int i = 0; i < data.pop(); i++) {
            listofIndividuals.add(new Individual(data.n()));
        }
    }

    public List<Integer> getListOfAttacks() {
        return listOfAttacks;
    }

    public void evaluatePopulation() {
        listOfAttacks.clear();
        for(Individual individual : listofIndividuals) {
            listOfAttacks.add(individual.evaluate());
        }
    }

    @Override
    public String toString() {
        return "Population { \n" +
                "  population X = " + listofIndividuals +
                " } \n";
    }
}
