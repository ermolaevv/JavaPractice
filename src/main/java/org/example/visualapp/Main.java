package org.example.visualapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application{
//    public static void main(String[] args) throws Exception {
//        ClockFactory factory = new ClockFactory();
//        ClockShop cs = new ClockShop();
//
//        int SIZE = 10;
//
//        ArrayList<IClock> clocks = new ArrayList<IClock>();
//        for (int i = 0; i < 10; ++i) {
//            clocks.add(factory.create(EClockTypes.THREE));
//        }
//
//        cs.addAllClocks(clocks);
//
//        System.out.println("Before setAllTime");
//        cs.forEach(System.out::println);
//
//        cs.setAllTime(new Time(10, 11, 12));
//
//        System.out.println("\nAfter setAllTime");
//        cs.forEach(System.out::println);
//
//        System.out.println("\nClock with max price: " + String.valueOf(cs.getInfoMaxPrice()));
//        System.out.println("Most common name: " + cs.getMostCommon());
//        System.out.println(cs.getListOfNames());
//
//        IClock clock = factory.create(EClockTypes.TWO);
//
//        System.out.println("\nAdd clock " + String.valueOf(clock));
//        cs.addClock(clock);
//        cs.forEach(System.out::println);
//
//        System.out.println("\nRemove clock " + String.valueOf(clock));
//        cs.removeClock(clock);
//        cs.forEach(System.out::println);
//    }
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Clock Shop");
            stage.getIcons().add(new Image(
                    Objects.requireNonNull(
                            getClass().getResource("icon.png")
                    ).openStream())
            );
            stage.show();

            loader.<MainController>getController().event(BClockShop.build());
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { launch(args); }

}