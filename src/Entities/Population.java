package Entities;

import DTO.Data;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Indiviual> population;
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

    public List<Indiviual> getPopulation() {
        return population;
    }

    public void createPopulation(Data data) {
        for(int i = 0; i < data.pop(); i++) {
            population.add(new Indiviual(data.n()));
        }
    }

    public List<Integer> getEvaluatedPopulation() {
        return evaluatedPopulation;
    }

    public void evaluatePopulation() {
        for(Indiviual indiviual : population) {
            evaluatedPopulation.add(indiviual.evaluate());
        }
    }

    @Override
    public String toString() {
        return "Population { \n" +
                "  population X = " + population +
                " } \n";
    }
}
