import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    Individual[] individuals;
    int popFitness = 0;
    final int pop_size; //?
    Garden garden;

    public Population(Garden garden, int pop_size){
        this.garden = garden;
        this.pop_size = pop_size;
        individuals = new Individual[pop_size];
        for(int i = 0; i < pop_size; i++){
            Individual newindividual = new Individual(garden.walkGarden(genChromosome()));
            individuals[i] = newindividual;
        }

    }

    public Individual getMaxFit(){
        int max = Integer.MIN_VALUE;
        int max_idx = -1;
        for(int i =0;i<individuals.length;i++){
            if(max < individuals[i].fitness){
                max = individuals[i].fitness;
                max_idx = i;
            }
        }
        popFitness = max;
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












}
