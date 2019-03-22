public class Runner {



    public static void main(String[] args){
        Garden garden = new Garden(8,12,2);
        Genetic genetic = new Genetic(garden);

        genetic.genChromosome();

    }
}
