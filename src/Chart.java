import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Chart extends Application  {
    final int WINDOW_SIZE = 10;
    boolean tmp = false;
    private ScheduledExecutorService scheduledExecutorService;
    LineChart<Number, Number> lineChart = null;
    XYChart.Series<Number, Number> series;
    HashMap<Number,Number> samples = new HashMap<>(1000);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Realtime Chart Demo");
        Chart chart = this;
        //defining the axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generation");
        xAxis.setAnimated(false);
        yAxis.setLabel("Fitness");
        yAxis.setAnimated(false);



        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
//        lineChart.getData().add(series);

        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

//        // this is used to display time in HH:mm:ss format
//        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
           // Integer random = ThreadLocalRandom.current().nextInt(20);

            // Update the chart
            Platform.runLater(() -> {

                if(tmp){
                    lineChart.getData().add(series);
                    tmp = false;
                }
               // series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));

//                if (series.getData().size() > WINDOW_SIZE)
//                    series.getData().remove(0);
            });
        }, 10, 10, TimeUnit.MILLISECONDS);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Garden garden = new Garden(8, 12, 2);
                Genetic genetic = new Genetic(garden, chart);
                genetic.solve();
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }

    public void setSeries(int a, int b) {
//        XYChart.Data<Integer,Integer> sample = new XYChart.Data<Integer,Integer>(a, b);
//        samples.getData().add(sample);
       samples.put((Number) a,(Number) b);
    }

    public void show(){
        int iter = 1 + samples.size() % 200;
        for(int i = 0; i < samples.size(); i += iter){
            series.getData().add(new XYChart.Data(i, samples.get(i)));
        }
        series.getData().remove(0,1);
        tmp = true;
    }
}

