package Service;

import DTO.Data;
import DTO.RandomPoints;
import Entities.Individual;
import Entities.Population;

import java.util.*;

public class Evolutionary {

    private int findBestIndividual(Population population) {
        int lowestNumberAttack = Collections.min(population.getListOfAttacks());
        return population.getListOfAttacks().indexOf(lowestNumberAttack);
    }

    private Population selection(Population pop, Data data) {
        Population newPopulation = new Population();
        int addedIndividualsCounter = 0;
        List<Individual> listofIndividuals = pop.getListofIndividuals();

        while(addedIndividualsCounter < data.pop()) {
            int randomIndexA = (int) (Math.random() * data.n());
            int randomIndexB = (int) (Math.random() * data.n());
            Individual individualA = listofIndividuals.get(randomIndexA);
            Individual individualB = listofIndividuals.get(randomIndexB);

            if(randomIndexA != randomIndexB) {
                if(individualA.evaluate() <= individualB.evaluate()) {
                    newPopulation.getListofIndividuals().add(individualA);
                }
                else {
                    newPopulation.getListofIndividuals().add(individualB);
                }
                addedIndividualsCounter++;
            }
        }
        return newPopulation;
    }

    private void crossover(Population crossPopulation,Data data){
        int i = 0;
        while (i < data.pop() - 2) {
            if(Math.random() <= data.pc()) {
                Individual individualA = crossPopulation.getListofIndividuals().get(i);
                Individual individualB = crossPopulation.getListofIndividuals().get(i + 1);
                cross(individualA, individualB,data);
            }
            i += 2;
        }
    }

    private void cross(Individual individualA, Individual individualB, Data data){
        RandomPoints randomPoints = generateRandomStartStopPoints(data);

        List<Integer> mappingMiddleSectionA = new ArrayList<>(individualA.getListOfHetmans().subList(randomPoints.randomPointFirst(),randomPoints.randomPointSec() + 1));
        List<Integer> mappingMiddleSectionB = new ArrayList<>(individualB.getListOfHetmans().subList(randomPoints.randomPointFirst(),randomPoints.randomPointSec() + 1));

        Map<Integer,Integer> mappedValuesForA = new HashMap<>();
        Map<Integer,Integer> mappedValuesForB = new HashMap<>();

        for (int value = 0; value < mappingMiddleSectionA.size(); value++) {
            mappedValuesForA.put(mappingMiddleSectionA.get(value),mappingMiddleSectionB.get(value));
            mappedValuesForB.put(mappingMiddleSectionB.get(value),mappingMiddleSectionA.get(value));
        }
        applyMapping(individualA, mappedValuesForA, randomPoints.randomPointSec() + 1,individualA.getListOfHetmans().size());
        applyMapping(individualB, mappedValuesForB, randomPoints.randomPointSec() + 1,individualB.getListOfHetmans().size());
        applyMapping(individualA, mappedValuesForA, 0,randomPoints.randomPointFirst());
        applyMapping(individualB, mappedValuesForB, 0,randomPoints.randomPointFirst());

        for(int index = randomPoints.randomPointFirst(); index <= randomPoints.randomPointSec();  index++) {
            individualA.getListOfHetmans().set(index,mappingMiddleSectionB.get(index - randomPoints.randomPointFirst()));
            individualB.getListOfHetmans().set(index,mappingMiddleSectionA.get(index - randomPoints.randomPointFirst()));
        }
    }

    private void applyMapping(Individual individual, Map<Integer,Integer> mapping, int startIndex, int endIndex) {
        for (int hetmanIndex = startIndex; hetmanIndex < endIndex; hetmanIndex++) {
            while (mapping.containsValue(individual.getListOfHetmans().get(hetmanIndex))) {
                for (int key : mapping.keySet()) {
                    if (individual.getListOfHetmans().get(hetmanIndex) == mapping.get(key)) {
                        individual.getListOfHetmans().set(hetmanIndex, key);
                    }
                }
            }
        }
    }

    private void mutation(Population population, Data data) {
        int index = 0;
        while(index < data.pop()) {
            if(Math.random() <= data.pm()) {
                mutate(population.getListofIndividuals().get(index),data);
            }
            index+= 1;
        }
    }

    private void mutate(Individual individual, Data data) {
        RandomPoints randomPoints = generateRandomStartStopPoints(data);
        int tmp;

        int individualA = individual.getListOfHetmans().get(randomPoints.randomPointFirst());
        int individualB = individual.getListOfHetmans().get(randomPoints.randomPointSec());
        tmp = individualA;
        individual.getListOfHetmans().set(randomPoints.randomPointFirst(),individualB);
        individual.getListOfHetmans().set(randomPoints.randomPointSec(),tmp);
    }

    private RandomPoints generateRandomStartStopPoints(Data data) {
        int randomPointFirst = (int) (Math.random() * data.n());
        int randomPointSec   = (int) (Math.random() * data.n());

        while(randomPointFirst == randomPointSec)
            randomPointSec   = (int) (Math.random() * data.n());

        if(randomPointFirst > randomPointSec) {
            int tmp = randomPointSec;
            randomPointSec = randomPointFirst;
            randomPointFirst = tmp;
        }

        return new RandomPoints(randomPointFirst,randomPointSec);
    }

    public double[] start(Data data) {
        List<Double> resultForChart = new ArrayList<>();

        Population population = new Population(data);;
        population.evaluatePopulation();

        int indexOfBestIndividual = findBestIndividual(population);

        int generationCounter = 0;
        while( (generationCounter < data.genMax()) && (population.getListofIndividuals().get(indexOfBestIndividual).evaluate() > data.ffXax()) ) {
            Population newPopulation = selection(population,data);
            crossover(newPopulation,data);
            mutation(newPopulation,data);
            newPopulation.evaluatePopulation();
            indexOfBestIndividual = findBestIndividual(newPopulation);
            population = newPopulation;
            generationCounter++;

            resultForChart.add((double)newPopulation.getListofIndividuals().get(indexOfBestIndividual).evaluate());
        }
        return resultForChart.stream().mapToDouble(Double::doubleValue).toArray();
//        return population.getPopulation().get(best);
    }
}
