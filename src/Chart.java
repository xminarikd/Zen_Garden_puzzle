import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Chart extends Application  {
    final int WINDOW_SIZE = 10;
    boolean tmp = false;
    private ScheduledExecutorService scheduledExecutorService;
    BarChart<String, Number> lineChart = null;
    XYChart.Series<String, Number> series1;
    XYChart.Series<String, Number> series2;
    XYChart.Series<String, Number> series3;
    XYChart.Series<String, Number> series4;
    HashMap<Number,Number> samples = new HashMap<>(1000);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Realtime Chart");
        Chart chart = this;
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
       // xAxis.setAnimated(false);
        yAxis.setLabel("Fitness");
      //  yAxis.setAnimated(true);

        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(
                "avgfitness", "pocetgeneracii")));
        xAxis.setLabel("category");


        lineChart = new BarChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime JavaFX Charts");
       // lineChart.setAnimated(true); // disable animations
      //  lineChart.setCreateSymbols(false);
        //defining a series1 to display data
        series1 = new XYChart.Series<>();
        series1.setName("turnaj/2point");

        series2 = new XYChart.Series<>();
        series2.setName("ruleta/1point");

        series3 = new XYChart.Series<>();
        series3.setName("ruleta/2point");

        series4 = new XYChart.Series<>();
        series4.setName("turnaj/1point");


        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            Platform.runLater(() -> {

                if(tmp){
                        // yAxis.setLowerBound(60);
                        lineChart.getData().add(series1);
                        lineChart.getData().add(series2);
                        lineChart.getData().add(series3);
                        lineChart.getData().add(series4);
                        yAxis.setAutoRanging(true);
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

//   //public void setSeries(int a, int b) {
//       samples.put((Number) a,(Number) b);
//    }

    public void show(ArrayList<Results> arrayList){
        double tmp1 = 0;
        double tmp2 = 0;
        for(int i = 0; i < arrayList.size(); i++){
            if(i < 10){
                tmp1 += arrayList.get(i).avgFitt;
                tmp2 += arrayList.get(i).pocetgeneracii;
                if(i == 9) {
                    series1.getData().add(new XYChart.Data("avgfitness", tmp1 / 10));
                    series1.getData().add(new XYChart.Data("pocetgeneracii", tmp2 / 10));
                    tmp1 = tmp2 = 0;
                }
            }
            else if(i < 20){
                tmp1 += arrayList.get(i).avgFitt;
                tmp2 += arrayList.get(i).pocetgeneracii;
                if(i == 19) {
                    series2.getData().add(new XYChart.Data("avgfitness", tmp1 / 10));
                    series2.getData().add(new XYChart.Data("pocetgeneracii", tmp2 / 10));
                    tmp1 = tmp2 = 0;
                }
            }
            else if(i < 30){
                tmp1 += arrayList.get(i).avgFitt;
                tmp2 += arrayList.get(i).pocetgeneracii;
                if(i == 29) {
                    series3.getData().add(new XYChart.Data("avgfitness", tmp1 / 10));
                    series3.getData().add(new XYChart.Data("pocetgeneracii", tmp2 / 10));
                    tmp1 = tmp2 = 0;
                }
            }
            else{
                tmp1 += arrayList.get(i).avgFitt;
                tmp2 += arrayList.get(i).pocetgeneracii;
                if(i == 39) {
                    series4.getData().add(new XYChart.Data("avgfitness", tmp1 / 10));
                    series4.getData().add(new XYChart.Data("pocetgeneracii", tmp2 / 10));
                    tmp1 = tmp2 = 0;
                }
            }
        }
        tmp = true;
    }
}

