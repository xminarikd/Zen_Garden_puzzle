import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trieda, z ktorej sa definuju testovacie subory
 */
public class Runner {

    ArrayList<Results> arrayList = new ArrayList<>();

    public void start(Chart chart) throws IOException {

        Garden garden = new Garden(new File("tests/test01.txt"));
        Genetic genetic = new Genetic(garden, chart, this);
        genetic.solve();

//        for(int i = 0 ; i < 40; i++) {
//            Garden garden = new Garden(new File("tests/test01.txt"));
//            Genetic genetic = new Genetic(garden, chart, this);
//            if(i < 10){
//                genetic.CROSS_METODE = 2;
//                genetic.SELECTION_METODE = 1;
//            }
//            else if(i < 20){
//                genetic.CROSS_METODE = 1;
//                genetic.SELECTION_METODE = 2;
//            }
//            else if(i < 30){
//                genetic.CROSS_METODE = 2;
//                genetic.SELECTION_METODE = 2;
//            }
//            else{
//                genetic.CROSS_METODE = 1;
//                genetic.SELECTION_METODE = 1;
//            }
//            genetic.solve();
//            arrayList.get(i).setCisloriesenia(i);
//        }
//        chart.show(arrayList);


    }
}
