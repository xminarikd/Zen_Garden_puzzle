import java.util.Random;

public class Genetic {
    private Garden garden;
    private Chart chart;
    final int POP_SIZE = 200;
    final int TOURNAMENT_SIZE = 3;
    final int GENERATIONS = 6000;
    final int CROSS_METODE = 2;
    final int SELECTION_METODE = 2;
    double MUTATE_RATE = 0.30;

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
        System.out.println("Working on it ....");
        while (aktGeneration++ < GENERATIONS) {

            if(population.getPopFitness() == garden.getPocetPiesok()){
                System.out.println("Nasiel som riesenie");
                System.out.println("Generacia cislo: " + aktGeneration);
                result = population.getMaxFit();
                System.out.println("Toto je vitaz : " + result.getFitness());
                garden.walkGarden(result,true,false);
                chart.setSeries(aktGeneration, population.getPopFitness());
                chart.show();
                return;
            }

            //population.getMaxFit();
            newpop = new Population(garden,POP_SIZE,false);

//            maxIndi = null;
//            maxIndi = population.getMaxFit();
//            prevMaxFitt = maxFitt;
//
////            int tmp = maxIndi.getFitness();
//            for(int i = 0 ; i < 50 ; i++) {
//                Individual maxindi2 = garden.walkGarden(maxIndi, false);
//                System.out.println(maxindi2.getFitness());
//                if(maxindi2.getFitness() != tmp && i != 0){
//                    System.out.println("errrorororo");
//                }
//                tmp = maxindi2.getFitness();
//            }



//            if(result == null || result.getFitness() < maxFitt){
//                result = maxIndi;
//            }

//            if(prevMaxFitt != maxFitt){
//                MUTATE_RATE = 0.01;
//            }
//            else if(MUTATE_RATE < 0.6){
//                MUTATE_RATE += 0.005;
//            }

            int tmp1 = population.getMaxFit().getFitness();
           // System.out.println("Generacia: " + aktGeneration + "fitness " + tmp1);

            newpop.setIndividuals(0,garden.walkGarden(population.getMaxFit(),false,false));
            population.setSumFitnes();
            if(SELECTION_METODE == 1) {
                for (int i = 1; i < POP_SIZE; i++) {
                    Individual indi1 = tournament(population);
                    Individual indi2 = tournament(population);
                    Individual newIndi;
                    if(CROSS_METODE == 1) {
                        newIndi = cross(indi1, indi2);
                    }
                    else if(CROSS_METODE == 2){
                        newIndi = cross2(indi1, indi2);
                    }
                    newpop.setIndividuals(i, newIndi);
                }
            }
            else if(SELECTION_METODE == 2){
                for (int i = 1; i < POP_SIZE; i++) {
                    Individual indi1 = roulet(population);
                    Individual indi2 = roulet(population);
                    Individual newIndi;
                    if(CROSS_METODE == 1) {
                        newIndi = cross(indi1, indi2);
                    }
                    else if(CROSS_METODE == 2){
                        newIndi = cross2(indi1, indi2);
                    }
                    newpop.setIndividuals(i, newIndi);
                }
            }
            for(int i = 1; i < POP_SIZE; i++){
                mutate(newpop.getIndividual(i));
            }


//            int tmp2 = population.getMaxFit().getFitness();
//
//            if(tmp1 != tmp2){
//                System.out.println("erorooror");
//            }

            newpop.walkGarden();
            newpop.getMaxFit();
            population.getMaxFit();

            chart.setSeries(aktGeneration, (int) population.getPopFitness());

            int tmp2 = population.getMaxFit().getFitness();

            if(tmp1 != tmp2){
                System.out.println("erorooror");
            }
            population = newpop;
        }
        System.out.println("Riesenie sa nenaslo");
        garden.walkGarden(population.getMaxFit(),true,false);
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
        Individual newIndi = new Individual(indi1.getChromosome(),0,false);
        int rndPoint = (int) (Math.random() * garden.getMaxGene());
        for (int i = 0; i < rndPoint; i++) {
            newIndi.setGene(i, indi2.getGene(i));
        }
        return newIndi;
    }

    private Individual cross2(Individual indi1, Individual indi2) {
        Individual newIndi = new Individual(indi1.getChromosome(),0,false);

        int lenght = garden.getMaxGene();

        int p1 = (int) (Math.random() * lenght);
        int p2 = (int) (Math.random() * lenght);

        if (p1 == p2){
            if(p1 == 0){
                p2++;
            }
            else {
                p1--;
            }
        }

        if (p2 < p1) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }

        for (int i = 0; i < lenght; i++) {
            if (i < p1 || i > p2) {
                newIndi.setGene(i, indi2.getGene(i));
            }
        }
        return newIndi;
    }


    public Individual roulet(Population population){
        double point = Math.random() * population.getSumFitnes();

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
                indi.setGene(i, (tmp * (int) (Math.random() * range)));
            }
        }
    }


}
