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

    private Population selection(Population pop) {
        Population newPopulation = new Population();
        int addedIndividualsCounter = 0;
        List<Individual> listofIndividuals = pop.getListofIndividuals();

        while(Data.pop > addedIndividualsCounter) {
            int randomIndexA = (int) (Math.random() * Data.n);
            int randomIndexB = (int) (Math.random() * Data.n);
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

    private void crossover(Population crossPopulation){
        int i = 0;
        while (Data.pop - 2 > i) {
            if(Data.pc >= Math.random()) {
                Individual individualA = crossPopulation.getListofIndividuals().get(i);
                Individual individualB = crossPopulation.getListofIndividuals().get(i + 1);
                cross(individualA, individualB);
            }
            i += 2;
        }
    }

    private void cross(Individual individualA, Individual individualB){
        RandomPoints randomPoints = generateRandomStartStopPoints();

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

    private void mutation(Population population) {
        int index = 0;
        while(Data.pop > index) {
            if(Data.pm >= Math.random()) {
                mutate(population.getListofIndividuals().get(index));
            }
            index+= 1;
        }
    }

    private void mutate(Individual individual) {
        RandomPoints randomPoints = generateRandomStartStopPoints();
        int tmp;

        int individualA = individual.getListOfHetmans().get(randomPoints.randomPointFirst());
        int individualB = individual.getListOfHetmans().get(randomPoints.randomPointSec());
        tmp = individualA;
        individual.getListOfHetmans().set(randomPoints.randomPointFirst(),individualB);
        individual.getListOfHetmans().set(randomPoints.randomPointSec(),tmp);
    }

    private RandomPoints generateRandomStartStopPoints() {
        int randomPointFirst = (int) (Math.random() * Data.n);
        int randomPointSec   = (int) (Math.random() * Data.n);

        while(randomPointFirst == randomPointSec)
            randomPointSec   = (int) (Math.random() * Data.n);

        if(randomPointFirst > randomPointSec) {
            int tmp = randomPointSec;
            randomPointSec = randomPointFirst;
            randomPointFirst = tmp;
        }

        return new RandomPoints(randomPointFirst,randomPointSec);
    }

    public double[] start() {
        List<Double> resultForChart = new ArrayList<>();

        Population population = new Population();;
        population.createPopulation();
        population.evaluatePopulation();


        int indexOfBestIndividual = findBestIndividual(population);

        int generationCounter = 0;
        while( (Data.genMax > generationCounter) && (Data.ffXax < population.getListofIndividuals().get(indexOfBestIndividual).evaluate()) ) {
            Population newPopulation = selection(population);
            crossover(newPopulation);
            mutation(newPopulation);
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
