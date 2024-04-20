package Service;

import DTO.Data;
import Entities.Indiviual;
import Entities.Population;

import java.util.ArrayList;
import java.util.List;

public class Evolutionary {
    private int findBestIndividual(Population pop) {
        List<Indiviual> population = pop.getPopulation();
        int best = Integer.MAX_VALUE;
        int bestIndividual = -1;
        for (Indiviual indiviual : population) {
            int diagonalAttack = indiviual.evaluate();
            if(best > diagonalAttack) {
                best = diagonalAttack;
                bestIndividual = population.indexOf(indiviual);
            }
        }
        return bestIndividual;
    }

    private Population selection(Population pop, Data data) {
        Population newPopulation = new Population();
        int index = 0;
        List<Indiviual> population = pop.getPopulation();

        while(index < data.pop()) {
            Indiviual a = population.get((int) (Math.random() * (data.n() - 1)));
            Indiviual b = population.get((int) (Math.random() * (data.n() - 1)));

            if(a != b) {
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

    private Population crossover(Data data, Population population){
        Population crossedPopulation = new Population();
        int i = 0;
        while (i < data.pop() - 2) {
            if(Math.random() <= data.pc()) {
                Indiviual a = population.getPopulation().get(i);
                Indiviual b = population.getPopulation().get(i + 1);
                cross(a, b,data);
            }
            i += 2;
        }
        return crossedPopulation;
    }

    /*
     PMX https://www.baeldung.com/cs/ga-pmx-operator
    */
    private void cross(Indiviual a, Indiviual b, Data data){

        int randomPointFirst = (int) (Math.random() * data.n());
        int randomPointSec   = (int) (Math.random() * data.n());

        while(randomPointFirst == randomPointSec)
            randomPointSec   = (int) (Math.random() * data.n());

        if(randomPointFirst > randomPointSec) {
            int tmp = randomPointSec;
            randomPointSec = randomPointFirst;
            randomPointFirst = tmp;
        }


        // Todo CONFLICT DETECTION!!!!
        // Selection of Crossover
        /*In the case of PMX, one starts by randomly selecting two crossing points. These points divide the parents’ chromosomes into three segments: Left, middle, and right segmentation.*/
        List<Integer> mappingSectionA = new ArrayList<>(a.getRepresentIndividual().subList(randomPointFirst,randomPointSec));
        List<Integer> mappingSectionB = new ArrayList<>(b.getRepresentIndividual().subList(randomPointFirst,randomPointSec));

        for(int index = randomPointFirst; index < randomPointSec;  index++) {
            a.getRepresentIndividual().set(index,mappingSectionB.get(index - randomPointFirst));
            b.getRepresentIndividual().set(index,mappingSectionA.get(index - randomPointFirst));
        }
    }

    public void start(Data data) {
        Population population0 = new Population();
        population0.createPopulation(data);
        population0.evaluate();
        int best = findBestIndividual(population0);
        int gen = 0;
        while(gen < data.genMax() & population0.getPopulation().get(best).evaluate() > data.ffXax()) {
            Population newPopulation = selection(population0,data);
        }

    }
}
