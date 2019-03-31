import java.util.Random;

public class Genetic {
    private Garden garden;
    final int POP_SIZE = 10;
    final int TOURNAMENT_SIZE = 2;
    final int GENERATIONS = 1000;
    final double CROSS_RATE = 0.5;
    final double MUTATE_RATE = 0.1;

    public Genetic(Garden garden) {
        this.garden = garden;
    }

    public void solve(){
        int aktGeneration = 0;
        Population population = new Population(garden, POP_SIZE,true);
        System.out.println("tu som teraz");
        while (aktGeneration++ < GENERATIONS) {

            Population newpop = new Population(garden,POP_SIZE,false);
            for(int i = 0; i < POP_SIZE; i++){
                Individual indi1 = tournament(population);
                Individual indi2 = tournament(population);
                Individual newIndi = cross(indi1,indi2);
                newpop.setIndividuals(i,newIndi);
            }



        }
    }


    public Individual tournament(Population population){
       Population tournament = new Population(POP_SIZE);

       for(int i = 0; i < TOURNAMENT_SIZE; i++){
           int tmp = (int) (Math.random() * POP_SIZE);
           tournament.setIndividuals(i,population.getIndividual(tmp));
       }

        return tournament.getMaxFit();
    }

    private Individual cross(Individual indi1, Individual indi2) {
        Individual newIndi = new Individual(indi1.getChromosome(),false);
        for (int i = 0; i < indi1.getGeneLength(); i++) {
            if (Math.random() <= CROSS_RATE)
                newIndi.setGene(i, indi2.getGene(i));
        }

        return newIndi;
    }


    public void mutate(Individual indi){
        if(Math.random() < MUTATE_RATE){

        }
    }


}
