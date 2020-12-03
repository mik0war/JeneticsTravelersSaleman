import io.jenetics.*;
import io.jenetics.engine.*;

public class TravelingSalesman {
    private static final double[][] MATRIX = new double[][]{
            {0, 1, 3, 4, 5},
            {1, 0, 1, 4, 8},
            {3, 1, 0, 5, 1},
            {4, 4, 5, 0, 2},
            {5, 8, 1, 2, 0}
    };

    private static double eval(final int[] var) {
        double sum = 0.0;

        for (int i = 0; i < var.length; i++){
            sum += MATRIX[var[i]][var[(i+1) % var.length]];
        }
        return sum;
    }

    public static void main(String[] args) {

        final Engine<EnumGene<Integer>, Double> engine = Engine
                .builder(
                        TravelingSalesman::eval,
                        Codecs.ofPermutation(MATRIX.length))
			    .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(10)
                .populationSize(500)
                .build();

        final Phenotype<EnumGene<Integer>, Double> best =
                engine.stream()
                        .limit(100)
                        .collect(EvolutionResult.toBestPhenotype());

        System.out.println(best);
    }
}