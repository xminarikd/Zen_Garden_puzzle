import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
   private Individual[] individuals;
   private int popFitness;
   protected final int POP_SIZE;
   private Garden garden;
   private int sumFitnes;

    public Population(Garden garden, int pop_size, boolean genrate){
        this.garden = garden;
        this.POP_SIZE = pop_size;
        individuals = new Individual[pop_size];
        if(genrate) {
            for (int i = 0; i < pop_size; i++) {
                Individual newindividual = new Individual(garden.walkGarden(genChromosome(),false), true);
                individuals[i] = newindividual;
            }
        }

    }

    public Population(int POP_SIZE) {
        this.POP_SIZE = POP_SIZE;
        individuals = new Individual[POP_SIZE];
    }

    public Individual getMaxFit(){
        int max = -1;
        int max_idx = -1;
        for(int i = 0; i < POP_SIZE; i++){
            if(max <= individuals[i].getFitness()){
                max = individuals[i].getFitness();
                max_idx = i;
            }
        }
        this.popFitness = max;
        return individuals[max_idx];
    }


    public Individual getIndividual(int index){
        return individuals[index];
    }


    public ArrayList genChromosome(){
        ArrayList<Integer> chromosome;
        int range = (2 * garden.getPolObvod());
        chromosome = IntStream.range(0, range).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chromosome);
        chromosome.subList(garden.getMaxGene(),chromosome.size()).clear();

        for(int i = 0; i < chromosome.size() / 2; i++){
            chromosome.set(i,chromosome.get(i) * -1);
        }
        Collections.shuffle(chromosome);
//        System.out.println(chromosome.toString());
//        System.out.println(chromosome.size());
        return chromosome;
    }

    public void walkGarden(){
        for(int i = 1; i < POP_SIZE; i++) {
           Individual indi = new Individual(garden.walkGarden(individuals[i].chromosome,false),true);
           this.individuals[i] = indi;
        }
        getMaxFit();
    }


    public Individual getIndividuals(int index) {
        return individuals[index];
    }

    public void setIndividuals(int index, Individual newIndividual) {
        this.individuals[index] = newIndividual;
    }

    public int getPopFitness() {
        return popFitness;
    }

    public void setPopFitness(int popFitness) {
        this.popFitness = popFitness;
    }

    public int getSumFitnes() {
        return sumFitnes;
    }

    public void setSumFitnes() {
        int sum = 0;

        for (int i = 0; i < individuals.length; i++) {
          sum += individuals[i].getFitness();
        }
        this.sumFitnes = sum;
    }

}
