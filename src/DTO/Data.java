package DTO;

public record Data(
        int n,    // numberOfHetmans
        int pop,  // populationLimit
        int genMax, // Maxymalna liczba kroków algorytmu
        double pc,    // prawdopodobienstwo krzyzowania 1/0
        double pm,
        int ffXax // wartość oczekiwaną/ użyteczśnci dla rozwiązania

) {
}
