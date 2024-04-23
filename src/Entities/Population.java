package Entities;

import DTO.Data;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Individual> population;
    private List<Integer> evaluatedPopulation;
    public Population() {
        population = new ArrayList<>();
        evaluatedPopulation = new ArrayList<>();
    }

    public Population(Data data) {
        population = new ArrayList<>();
        evaluatedPopulation = new ArrayList<>();
        createPopulation(data);
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public void createPopulation(Data data) {
        for(int i = 0; i < data.pop(); i++) {
            population.add(new Individual(data.n()));
        }
    }

    public List<Integer> getEvaluatedPopulation() {
        return evaluatedPopulation;
    }

    public void evaluatePopulation() {
        for(Individual individual : population) {
            evaluatedPopulation.add(individual.evaluate());
        }
    }

    @Override
    public String toString() {
        return "Population { \n" +
                "  population X = " + population +
                " } \n";
    }
}
