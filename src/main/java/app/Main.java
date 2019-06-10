package app;

import app.logic.LogicModel;
import app.logic.loader.DeviceFactory;
import app.logic.loader.LXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.*;

public class Main extends Application {
    private Timer timer;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init(){
        timer = new Timer();
    }


    @Override
    public void start(Stage primaryStage)throws Exception {
        FileInputStream fxmlFile = new FileInputStream("new.fxml");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = new Scene(loader.load(fxmlFile));
        FileInputStream lxmlFile = new FileInputStream("logic.xml");
        LXMLLoader lxmlLoader = new LXMLLoader();
        lxmlLoader.setDeviceFactory(new DeviceFactory(scene));
        LogicModel logicModel = lxmlLoader.load(lxmlFile);

        TimerTask businessTask = new TimerTask() {
            @Override
            public void run() {
                logicModel.run();
            }
        };
        timer.scheduleAtFixedRate(businessTask,0,100);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.cancel();
    }
}

