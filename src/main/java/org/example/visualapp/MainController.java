package org.example.visualapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.sources.*;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class MainController implements IOserver {

    ClockShop cs = BClockShop.build();

    public MainController(){

        cs.sub(this);

    }

    @FXML
    public TextField name;
    @FXML
    TextField price;
    @FXML
    GridPane allClocks;
    @FXML
    Label MaxPrice;
    @FXML
    Label MostCommon;
    @FXML
    CheckBox withSeconds;
    @FXML
    public void addClock(ActionEvent actionEvent) {
        IClock clock;

        if (withSeconds.isSelected()) {
            clock = new Clock_s(name.getText(), Double.parseDouble(price.getText()));
        }
        else {
            clock = new Clock(name.getText(), Double.parseDouble(price.getText()));
        }

        cs.addClock(clock);
        MaxPrice.setText(String.valueOf(cs.getInfoMaxPrice().getPrice()));
        MostCommon.setText(cs.getMostCommon());
    }
    @FXML
    public void timeShift(ActionEvent actionEvent) throws Exception {
        cs.moveAllTime(enterTime());
    }

    @FXML
    public void setTime(ActionEvent actionEvent) throws Exception {
        cs.setAllTime(enterTime());
    }
    @FXML
    public void clear(ActionEvent actionEvent) {
        cs.clear();
        MaxPrice.setText("н/д");
        MostCommon.setText("н/д");
    }
    @Override

    public void event(ClockShop cs) {

        allClocks.getChildren().clear();

        for (IClock p: cs) {

            try {

                ClockElementController cec = new ClockElementController(this);

                FXMLLoader fxmlLoader = new FXMLLoader(ClockElementController.class.getResource("ClockElement.fxml"));
                fxmlLoader.setController(cec);

                Parent root = fxmlLoader.load();
                cec.setElement(p);



                allClocks.addColumn(0, root);
            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        }

    }

    public static Time enterTime() throws Exception {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Введите время");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        Optional<String> response = dialog.showAndWait();
        if (response.isPresent()) {

            int hours = 0, minutes = 0, seconds = 0;

            String sresponse = response.get();
            var tmp = Arrays.stream(sresponse.split(":")).mapToInt(Integer::parseInt).toArray();
            switch (tmp.length) {
                case 3:
                    seconds = tmp[2];
                case 2:
                    hours = tmp[0];
                    minutes = tmp[1];
            }
            return new Time(hours, minutes, seconds);
        }
        return new Time();
    }
}


