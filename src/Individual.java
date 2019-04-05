import java.lang.reflect.Array;
import java.util.ArrayList;

public class Individual {
   private int fitness;
   ArrayList chromosome;
   private int geneLength;

//    public Individual(ArrayList chromosome, boolean tmp) {
//        if(tmp) {
//            this.fitness = (int) chromosome.get(chromosome.size() - 1);
//            chromosome.remove(chromosome.size() - 1);
//            this.chromosome = chromosome;
//            this.geneLength = this.chromosome.size();
//        }
//        else{
//            this.fitness = 0;
//            this.chromosome = chromosome;
//        }
//    }

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
