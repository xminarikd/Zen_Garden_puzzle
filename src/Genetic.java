import java.util.Random;

public class Genetic {
    private Garden garden;
    final int POP_SIZE = 50;
    final int TOURNAMENT_SIZE = 3;
    final int GENERATIONS = 5000;
    final double CROSS_RATE = 0.6;
    final double MUTATE_RATE = 0.1;

    public Genetic(Garden garden) {
        this.garden = garden;
    }

    public void solve(){
        int aktGeneration = 0;
        Individual result = null;
        Population population = new Population(garden, POP_SIZE,true);
        System.out.println("tu som teraz");
        while (aktGeneration++ < GENERATIONS) {

            if(population.getMaxFit().getFitness() == garden.getPocetPiesok()){
                System.out.println("Nasiel som riesenie");
                result = population.getMaxFit();
                break;
            }

            Population newpop = new Population(garden,POP_SIZE,false);
            for(int i = 0; i < POP_SIZE; i++){
                Individual indi1 = tournament(population);
                Individual indi2 = tournament(population);
                Individual newIndi = cross(indi1,indi2);
                newpop.setIndividuals(i,newIndi);
            }
            for(int i = 0; i < POP_SIZE; i++){
                mutate(newpop.getIndividual(i));
            }
            newpop.walkGarden();
            newpop.getMaxFit();

            population = newpop;

        }
    }


    public Individual tournament(Population population){
       Population tournament = new Population(TOURNAMENT_SIZE);

       for(int i = 0; i < TOURNAMENT_SIZE; i++){
           int tmp = (int) (Math.random() * POP_SIZE);
           tournament.setIndividuals(i, population.getIndividual(tmp));
       }
        return tournament.getMaxFit();
    }

    private Individual cross(Individual indi1, Individual indi2) {
        Individual newIndi = new Individual(indi1.getChromosome(),false);
        int rndPoint = (int) Math.random() * (garden.getMaxGene() - 1);
        for (int i = 0; i < rndPoint; i++) {
            newIndi.setGene(i, indi2.getGene(i));
        }

        return newIndi;
    }


    public void mutate(Individual indi){
        int range = (2 * garden.getPolObvod());
        int maxGene = garden.getMaxGene();
        int tmp = 1;
        for(int i = 0; i < maxGene; i++) {
            tmp = 1;
            if(Math.random() < 0.5) {
                tmp = -1;
            }
            if (Math.random() < MUTATE_RATE) {
                indi.setGene(i, (tmp * (int) (Math.random() * range - 1)));
            }
        }
    }


}
