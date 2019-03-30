
import java.util.*;

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
        ArrayList<Integer> chromosome = new ArrayList<>();

        for(int i = 0; i < garden.getPolObvod() * 2; i++){
            chromosome.add(i);
        }

        Collections.shuffle(chromosome);

       // while(chromosome.size() > garden.getMaxGene()){
        //    chromosome.remove(chromosome.size()-1);
            chromosome.subList(garden.getMaxGene(),chromosome.size()).clear();
      //  }

        System.out.println(chromosome.toString());
        System.out.println(chromosome.size());
        return chromosome;
    }



}
