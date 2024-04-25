package DTO;

import java.util.List;

public record GraphData (
        List<Double> listOfBestEvaluatedValueFromPop,
        List<Double> listOfAvarageEvaluatedValueFromPop
){}
