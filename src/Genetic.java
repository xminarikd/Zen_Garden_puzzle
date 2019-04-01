import java.util.Random;

public class Genetic {
    private Garden garden;
    private Chart chart;
    final int POP_SIZE = 150;
    final int TOURNAMENT_SIZE = 4;
    final int GENERATIONS = 5000;
    final int SELECTION_METODE = 1;
    double MUTATE_RATE = 0.01;

    public Genetic(Garden garden, Chart chart) {
        this.garden = garden;
        this.chart = chart;
    }

    public void solve(){
        int aktGeneration = 0;
        int maxFitt = 0;
        int prevMaxFitt = 0;
        Individual result = null;
        Population population = new Population(garden, POP_SIZE,true);
        Population newpop;
        Individual maxIndi;
        System.out.println("tu som teraz");
        while (aktGeneration++ < GENERATIONS) {

            if(population.getMaxFit().getFitness() == garden.getPocetPiesok()){
                System.out.println("Nasiel som riesenie");
                System.out.println("Generacia cislo: " + aktGeneration);
                result = population.getMaxFit();
                System.out.println("Toto je vitaz : " + result.getFitness());
                garden.walkGarden(result.chromosome,true);
                chart.show();
                return;
            }

          //  population.getMaxFit();
            newpop = new Population(garden,POP_SIZE,false);

            maxIndi = population.getMaxFit();

            prevMaxFitt = maxFitt;
            maxFitt = maxIndi.getFitness();

            if(result == null || result.getFitness() < maxFitt){
                result = maxIndi;
            }

            if(prevMaxFitt != maxFitt){
                MUTATE_RATE = 0.01;
            }
            else if(MUTATE_RATE < 0.5){
                MUTATE_RATE += 0.01;
            }

            newpop.setIndividuals(0,new Individual(maxIndi.chromosome, maxFitt,false));
            population.setSumFitnes();
            if(SELECTION_METODE == 1) {
                for (int i = 1; i < POP_SIZE; i++) {
                    Individual indi1 = tournament(population);
                    Individual indi2 = tournament(population);
                    Individual newIndi = cross(indi1, indi2);
                    newpop.setIndividuals(i, newIndi);
                }
            }
            else if(SELECTION_METODE == 2){
                for (int i = 1; i < POP_SIZE; i++) {
                    Individual indi1 = roulet(population);
                    Individual indi2 = roulet(population);
                    Individual newIndi = cross(indi1, indi2);
                    newpop.setIndividuals(i, newIndi);
                }
            }
            for(int i = 1; i < POP_SIZE; i++){
                mutate(newpop.getIndividual(i));
            }
            newpop.walkGarden();
            //newpop.getMaxFit();

            chart.setSeries(aktGeneration, population.getPopFitness());
            population = newpop;
        }
        System.out.println("Riesenie sa nenaslo");
        System.out.println("Toto je najlepsi doteraz : " + result.getFitness());
        garden.walkGarden(result.chromosome,true);
        chart.show();
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


    public Individual roulet(Population population){
        double point = Math.random() * population.getSumFitnes() -1 ;

        for(int i = 0; i < POP_SIZE; i++){
            point -= population.getIndividuals(i).getFitness();
            if(point < 0) {
                return population.getIndividual(i);
            }
        }

        return population.getIndividual(0);
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
