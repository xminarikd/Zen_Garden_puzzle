import java.util.ArrayList;

/**
 * Trieda, ktora reprezentuje jednotlivca, jeho chromozom/geny a taktiez jeho fitnes.
 */
public class Individual {
   private int fitness;
   ArrayList chromosome;
   private int geneLength;


    public Individual(ArrayList chromosome, int fitness ,boolean tmp){
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public ArrayList getChromosome() {
        return chromosome;
    }

    public void setChromosome(ArrayList chromosome) {
        this.chromosome = chromosome;
    }

    public int getGeneLength() {
        return geneLength;
    }

    public void setGeneLength(int geneLength) {
        this.geneLength = geneLength;
    }

    public void setGene(int idx, int cislo){
        this.chromosome.set(idx,cislo);
    }

    public int getGene(int idx){
       return (int) this.chromosome.get(idx);
    }

}
