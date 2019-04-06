import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Trieda, ktora reprezentuje populaciu jedincou.
 * Jednotlivci su uchovavani v poli jedincov
 */
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
                Individual newindividual = new Individual(genChromosome(),0,false);
                newindividual = garden.walkGarden(newindividual,false,true);
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
            if(max < individuals[i].getFitness()){
                max = individuals[i].getFitness();
                max_idx = i;
            }
        }
        this.popFitness = max;
        return individuals[max_idx];
    }


    public Individual getIndividual(int index){
        return this.individuals[index];
    }

    /**
     * Generovanie nahodnoho chromozomu.
     * Polovica tychto prvkov su zaporne, aby sa zabezpecilo nahodne zmenenie smeru.
     * @return ArrayList obsahujuci zaciatky hladania.
     */
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
        return chromosome;
    }

    /**
     * Ziskanie fitness pre jednotlivych jednincov
     */
    public void walkGarden(){
        for(int i = 0; i < POP_SIZE; i++) {
           this.individuals[i] = garden.walkGarden(individuals[i],false,true);
        }
        for(int i = 0; i < POP_SIZE; i++) {
            this.individuals[i] = garden.walkGarden(individuals[i],false,false);
        }
    }


    public Individual getIndividuals(int index) {
        return this.individuals[index];
    }

    public void setIndividuals(int index, Individual newIndividual) {
        this.individuals[index] = newIndividual;
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

    public double avgFitness(){
        return (double) (sumFitnes) / POP_SIZE;
    }

    /**
     * Metoda na usporiadanie jedincov na zaklade ich fitness
     */
    public void sort(){
        Collections.sort(Arrays.asList(individuals), new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.getFitness() > o2.getFitness())
                    return -1;
                else if(o1.getFitness() < o2.getFitness())
                    return 1;
                else
                    return 0;
            }
        });


    }

}
