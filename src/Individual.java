import java.lang.reflect.Array;
import java.util.ArrayList;

public class Individual {
    int fitness;
    ArrayList chromosome;
    int geneLength;

    public Individual(ArrayList chromosome) {
        this.fitness = (int) chromosome.get(chromosome.size() - 1);
        chromosome.remove(chromosome.size() - 1);
        this.chromosome = chromosome;
        this.geneLength = this.chromosome.size();
    }
}
