package Service;

import DTO.Data;
import DTO.RandomPoints;
import Entities.Individual;
import Entities.Population;

import java.util.*;

public class Evolutionary {
    private int findBestIndividual(Population pop) {
        List<Integer> population = pop.getEvaluatedPopulation();
        int best = Integer.MAX_VALUE;
        int bestIndividual = -1;

        for (Integer individual : population) {
            if(best > individual) {
                best = individual;
            }
        }

        bestIndividual = population.indexOf(best);
        return bestIndividual;
    }

    private Population selection(Population pop, Data data) {
        Population newPopulation = new Population();
        int index = 0;
        List<Individual> population = pop.getPopulation();

        while(index < data.pop()) {
            int randoma = (int) (Math.random() * data.n());
            int randomb = (int) (Math.random() * data.n());
            Individual a = population.get(randoma);
            Individual b = population.get(randomb);

            if(randoma != randomb) {
                if(a.evaluate() <= b.evaluate()) {
                    newPopulation.getPopulation().add(a);
                }
                else {
                    newPopulation.getPopulation().add(b);
                }
                index++;
            }
        }
        return newPopulation;
    }

    private void crossover(Population crossPopulation,Data data){
        int i = 0;
        while (i < data.pop() - 2) {
            if(Math.random() <= data.pc()) {
                Individual a = crossPopulation.getPopulation().get(i);
                Individual b = crossPopulation.getPopulation().get(i + 1);
                cross(a, b,data);
            }
            i += 2;
        }
    }

    private void cross(Individual a, Individual b, Data data){
        RandomPoints randomPoints = generateRandomStartStopPoints(data);

        List<Integer> mappingSectionA = new ArrayList<>(a.getRepresentIndividual().subList(randomPoints.randomPointFirst(),randomPoints.randomPointSec() + 1));
        List<Integer> mappingSectionB = new ArrayList<>(b.getRepresentIndividual().subList(randomPoints.randomPointFirst(),randomPoints.randomPointSec() + 1));

        Map<Integer,Integer> mapForA = new HashMap<>();
        Map<Integer,Integer> mapForB = new HashMap<>();
        for (int i = 0; i < mappingSectionA.size(); i++) {
            mapForA.put(mappingSectionA.get(i),mappingSectionB.get(i));
            mapForB.put(mappingSectionB.get(i),mappingSectionA.get(i));
        }
        applyMapping(a, mapForA, randomPoints.randomPointSec() + 1,a.getRepresentIndividual().size());
        applyMapping(b, mapForB, randomPoints.randomPointSec() + 1,b.getRepresentIndividual().size());
        applyMapping(a, mapForA, 0,randomPoints.randomPointFirst());
        applyMapping(b, mapForB, 0,randomPoints.randomPointFirst());

        for(int index = randomPoints.randomPointFirst(); index <= randomPoints.randomPointSec();  index++) {
            a.getRepresentIndividual().set(index,mappingSectionB.get(index - randomPoints.randomPointFirst()));
            b.getRepresentIndividual().set(index,mappingSectionA.get(index - randomPoints.randomPointFirst()));
        }
    }

    private void applyMapping(Individual individual, Map<Integer,Integer> mapping, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            while (mapping.containsValue(individual.getRepresentIndividual().get(i))) {
                for (Integer swap : mapping.keySet()) {
                    if (individual.getRepresentIndividual().get(i) == (int)mapping.get(swap)) {
                        individual.getRepresentIndividual().set(i, swap);
                    }
                }
            }
        }
    }

    private void mutation(Population population, Data data) {
        int i = 0;
        while(i < data.pop()) {
            if(Math.random() <= data.pm()) {
                mutate(population.getPopulation().get(i),data);
            }
            i+= 1;
        }
    }

    private void mutate(Individual individual, Data data) {
        RandomPoints randomPoints = generateRandomStartStopPoints(data);
        int tmp;

        int a = individual.getRepresentIndividual().get(randomPoints.randomPointFirst());
        int b = individual.getRepresentIndividual().get(randomPoints.randomPointSec());
        tmp = a;
        individual.getRepresentIndividual().set(randomPoints.randomPointFirst(),b);
        individual.getRepresentIndividual().set(randomPoints.randomPointSec(),tmp);
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
        Population population;
        List<Double> result = new ArrayList<>();

        population = new Population(data);
        population.evaluatePopulation();

        int best = findBestIndividual(population);

        int gen = 0;
        while( (gen < data.genMax()) && (population.getPopulation().get(best).evaluate() > data.ffXax()) ) {
            Population newPopulation = selection(population,data);
            crossover(newPopulation,data);
            mutation(newPopulation,data);
            newPopulation.evaluatePopulation();
            best = findBestIndividual(newPopulation);
            population = newPopulation;
            gen++;

            result.add((double)newPopulation.getPopulation().get(best).evaluate());
        }
        return result.stream().mapToDouble(Double::doubleValue).toArray();
//        return population.getPopulation().get(best);
    }
}
