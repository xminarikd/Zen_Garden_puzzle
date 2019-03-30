
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genetic {
    private Garden garden;

    public Genetic(Garden garden) {
        this.garden = garden;
    }

    public void solve(){
        ArrayList[] chromosomes = new ArrayList[10];
        int[] fitnes = new int[10];
       for(int i = 0; i < 10; i++){
           chromosomes[i] = genChromosome();
       }
       for(int i = 0; i < 10; i++) {
           chromosomes[i] = garden.walkGarden(chromosomes[i]);
           fitnes[i] = (int) chromosomes[i].get(chromosomes[i].size() - 1);
           chromosomes[i].remove(chromosomes[i].size() - 1);
           System.out.println("toto to je: " + i + ". pokus " + fitnes[i]);
       }
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

        System.out.println(chromosome.toString());
        System.out.println(chromosome.size());
        return chromosome;
    }



}
