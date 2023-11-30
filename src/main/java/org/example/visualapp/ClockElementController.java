package org.example.visualapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import org.example.sources.ClockShop;
import org.example.sources.IClock;
import javafx.scene.control.Label;

import java.awt.*;

public class ClockElementController {
    ClockShop cs = BClockShop.build();
    MainController mainController;
    ClockElementController(MainController mainController) { this.mainController = mainController; }
    IClock clock;
    @FXML
    Label name;
    @FXML
    Label price;
    @FXML
    Label time;
    @FXML
    void removeClock() {
        cs.removeClock(clock);
    }
    public void setElement(IClock clock) {
        this.clock = clock;
        name.setText(clock.getName());
        price.setText(String.valueOf(clock.getPrice()));
        time.setText(clock.getTime());
    }
    public void contextMenu(ContextMenuEvent contextMenuEvent) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem setTime = new MenuItem("Задать время");
        MenuItem moveTime = new MenuItem("Перевести время");

        setTime.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    clock.setStartTime(MainController.enterTime());
                    mainController.cs.syncTime(clock);
                    mainController.event(mainController.cs);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        moveTime.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    clock.timeShift(MainController.enterTime());
                    mainController.cs.syncTime(clock);
                    mainController.event(mainController.cs);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        contextMenu.getItems().addAll(setTime, moveTime);
        Point p = MouseInfo.getPointerInfo().getLocation();

        contextMenu.show((Node) contextMenuEvent.getSource(), p.getX(), p.getY());
    }

}
