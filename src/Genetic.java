
import java.util.*;

public class Genetic {
    private Garden garden;

    public Genetic(Garden garden) {
        this.garden = garden;
    }

    public void Solve(){

    }


    public List genChromosome(){
        List<Integer> chromosome = new ArrayList<>();

        for(int i = 0; i < garden.getPolObvod() * 2; i++){
            chromosome.add(i);
        }

        Collections.shuffle(chromosome);

        while(chromosome.size() > garden.getMaxGene()){
            chromosome.remove(chromosome.size()-1);
        }

        System.out.println(chromosome.toString());
        System.out.println(chromosome.size());
        return chromosome;
    }



}
