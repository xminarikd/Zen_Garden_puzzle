import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
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
        primaryStage.setTitle("JavaFX Realtime Chart");
        Chart chart = this;
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generation");
        xAxis.setAnimated(false);
        yAxis.setLabel("Fitness");
        yAxis.setAnimated(true);



        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
        lineChart.setAnimated(true); // disable animations
        lineChart.setCreateSymbols(false);
        //defining a series to display data
        series = new XYChart.Series<>();
        series.setName("Data Series");

        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            Platform.runLater(() -> {

                if(tmp){
                        // yAxis.setLowerBound(60);
                        lineChart.getData().add(series);
                        tmp = false;
                    }
            });
        }, 10, 10, TimeUnit.MILLISECONDS);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Runner runner = new Runner();
                try {
                    runner.start(chart);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }

    public void setSeries(int a, int b) {
       samples.put((Number) a,(Number) b);
    }

    public void show(){
        for(int i = 1; i <= samples.size(); i += 1){
            series.getData().add(new XYChart.Data(i, samples.get(i)));
            //System.out.println("Generacia " + i + " fitness: " + samples.get(i));
        }
        tmp = true;
    }
}

