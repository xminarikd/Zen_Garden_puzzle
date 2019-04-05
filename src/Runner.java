import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Runner {



    public void start(Chart chart) throws IOException {

        Garden garden = new Garden(new File("tests/test02.txt"));
        Genetic genetic = new Genetic(garden, chart);
        genetic.solve();

    }
}
