
public class Genetic {
    private Garden garden;
    private Chart chart;
    final int POP_SIZE = 250;
    final int TOURNAMENT_SIZE = 3;
    final int GENERATIONS = 6000;
    final int CROSS_METODE = 2;
    final int ELITISM_SIZE = 20;
    final int REFRESH_SIZE = 15;
    final int SELECTION_METODE = 2;
    final int MUTATIN_METODE = 2;
    double MUTATE_RATE = 0.25;

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

            if(population.getIndividual(0).getFitness() == garden.getPocetPiesok()){
                System.out.println("Nasiel som riesenie");
                System.out.println("Generacia cislo: " + aktGeneration);
                System.out.println("Toto je vitaz : ");
                garden.walkGarden(population.getIndividual(0),true,false);
                chart.setSeries(aktGeneration, population.getIndividual(0).getFitness());
                chart.show();
                return;
            }

            newpop = new Population(garden,POP_SIZE,false);

            maxIndi = null;
            maxIndi = population.getMaxFit();
            prevMaxFitt = maxFitt;



            population.sort();
            for(int i = 0; i < ELITISM_SIZE; i++) {
                newpop.setIndividuals(i, garden.walkGarden(population.getIndividual(i), false, false));
            }
            if(aktGeneration % 29 == 0){
                for(int i = POP_SIZE - 1; i >= POP_SIZE - REFRESH_SIZE; i--) {
                    population.setIndividuals(i, garden.walkGarden(new Individual(population.genChromosome(),0,false),false,true));
                }
            }
            population.sort();
            population.setSumFitnes();
            if(SELECTION_METODE == 1) {
                for (int i = ELITISM_SIZE; i < POP_SIZE; i++) {
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
                for (int i = ELITISM_SIZE; i < POP_SIZE; i++) {
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

            for(int i = ELITISM_SIZE; i < POP_SIZE; i++){
                if(MUTATIN_METODE == 1) {
                    mutate(newpop.getIndividual(i));
                }
                else if(MUTATIN_METODE == 2){
                    mutate2(newpop.getIndividual(i));
                }
            }


            newpop.walkGarden();
            newpop.sort();

            chart.setSeries(aktGeneration, newpop.getIndividual(0).getFitness());

            population = newpop;
        }
        System.out.println("Riesenie sa nenaslo");
        garden.walkGarden(population.getIndividual(0),true,false);
        chart.show();
    }


    public Individual tournament(Population population){
       Population tournament = new Population(TOURNAMENT_SIZE);

       for(int i = 0; i < TOURNAMENT_SIZE; i++){
           int tmp = (int) (Math.random() * POP_SIZE);
           tournament.setIndividuals(i, new Individual(population.getIndividual(i).getChromosome(),population.getIndividual(i).getFitness(),false));
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
                indi.setGene(i, (int) (tmp * (Math.random() * range)));
            }
        }
    }

    public void mutate2(Individual indi) {
        int l = garden.getMaxGene();
        if(Math.random() < MUTATE_RATE){
            int r1 = (int) (Math.random() * l);
            int r2 = (int) (Math.random() * l);

            if (r2 < r1) {
                int temp = r1;
                r1 = r2;
                r2 = temp;
            }

            int mid = r1 + ((r2 + 1) - r1) / 2;
            int endCount = r2;
            for (int i = r1; i < mid; i++) {
                int tmp = indi.getGene(i);
                indi.setGene(i, indi.getGene(endCount));
                indi.setGene(endCount,tmp);
                endCount--;
            }
        }
    }

}
