package com.isima.sma.ui;

import com.isima.sma.city.City;
import com.isima.sma.entities.Zone;
import com.isima.sma.entities.ZoneType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class FxUserInterface extends Application {

    private static final int SQUARE_LENGTH = 20;

    private City city;
    private BorderPane root;
    private Canvas cityCanvas;
    private ZoneType selectedZoneType;
    private Timer autoPlayTimer;

    public FxUserInterface() {
        this.city = new City(30, 20);
        this.root = new BorderPane();
        this.cityCanvas = new Canvas(city.getWidth() * SQUARE_LENGTH, city.getHeight() * SQUARE_LENGTH);
        this.selectedZoneType = null;
        this.autoPlayTimer = null; // Methodes pour "stepAndRedraw()" et infos (labels attr)
        // + methodes (getRandomHouseStartingPoint) (GetRandomStartingPoint (ZoneType Type) (appeler forall?) idk
        // Dijsktra ?
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(root));

        // City canvas | class RoadNetwork (grid etc)
        //Canvas canvas = new Canvas(city.getWidth() * SQUARE_LENGTH, city.getHeight() * SQUARE_LENGTH);
        cityCanvas.setOnMouseClicked(this::handleMouseClicked);
        cityCanvas.setOnMouseMoved(this::handleMouseMoved);
        GraphicsContext gc = cityCanvas.getGraphicsContext2D();
        //gc.fillRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());

        drawCity(gc);

        root.setStyle("-fx-padding: 30;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        root.setCenter(cityCanvas);

        // Group
        VBox controlPanel = new VBox();
        Label titleLabel = new Label("CitySim");
        titleLabel.setFont(new Font("Arial", 30));
        titleLabel.setTextFill(Color.web("#0076a3"));
        titleLabel.setPadding(new Insets(5)); //top, right, bottom, left
        controlPanel.getChildren().add(titleLabel);

        Label zonesLabel = new Label("Zones");
        zonesLabel.setPadding(new Insets(0, 0, 0, 5));
        controlPanel.getChildren().add(zonesLabel);
        HBox zoneButtonsBox = new HBox();
        zoneButtonsBox.setSpacing(5.);

        Button roadButton = new Button("Road");
        roadButton.setOnAction(e -> selectedZoneType = null);
        zoneButtonsBox.getChildren().add(roadButton);

        for(ZoneType zoneType : ZoneType.values()) {
            Button zoneButton = new Button(new Zone(zoneType, 0, 0).toString());
            zoneButton.setOnAction(e -> selectedZoneType = zoneType);
            zoneButtonsBox.getChildren().add(zoneButton);
        }
        zoneButtonsBox.setPadding(new Insets(10));
        controlPanel.getChildren().add(zoneButtonsBox);

        Label stepsLabel = new Label("Simulation");
        stepsLabel.setPadding(new Insets(0, 0, 0, 5));
        controlPanel.getChildren().add(stepsLabel);

        HBox stepButtonsBox = new HBox();
        stepButtonsBox.setSpacing(5.);
        stepButtonsBox.setPadding(new Insets(0, 0, 0, 5));
        Button oneStepButton = new Button("1 Step");
        oneStepButton.setOnAction(e -> {
            city.step();
            drawCity(cityCanvas.getGraphicsContext2D());
        });
        stepButtonsBox.getChildren().add(oneStepButton);

        Button autoStepButton = new Button("Start/Stop auto steps");
        autoStepButton.setOnAction(e -> {
            if (autoPlayTimer == null) {
                autoPlayTimer = new Timer();
                autoPlayTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        city.step();
                        drawCity(cityCanvas.getGraphicsContext2D());
                    }
                }, 0, 80); // Set speed
            } else {
                autoPlayTimer.cancel();
                autoPlayTimer = null;
            }
        });
        stepButtonsBox.getChildren().add(autoStepButton);

        controlPanel.getChildren().add(stepButtonsBox);

        controlPanel.setPadding(new Insets(10));
        root.setRight(controlPanel);

        // + hover => info
        // Menu bar
        MenuBar menuBar = new MenuBar();
        Menu viewMenu = new Menu("File");
        RadioMenuItem zoneItem = new RadioMenuItem("Opt1");
        zoneItem.setOnAction(e -> System.out.println("temp"));
        RadioMenuItem vehiclesItem = new RadioMenuItem("Opt2");
        vehiclesItem.setOnAction(e -> System.out.println("temp"));

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(zoneItem);
        toggleGroup.getToggles().add(vehiclesItem);

        viewMenu.getItems().add(zoneItem);
        viewMenu.getItems().add(vehiclesItem);
        menuBar.getMenus().add(viewMenu);
        HBox hbox = new HBox(menuBar);
        root.setTop(hbox);

        primaryStage.show();
    }

    private void handleMouseClicked(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        boolean redraw = false;

        if (selectedZoneType == null) {
            if (city.addRoad(column, row)) {
                redraw = true;
            }
        } else {
            if (city.addZone(column, row, selectedZoneType)) {
                redraw = true;
            }
        }

        if (redraw) {
            GraphicsContext gc = cityCanvas.getGraphicsContext2D();
            gc.setFill(city.getEntityAt(column, row).fxRepresentation());
            gc.fillRect(column * SQUARE_LENGTH, row * SQUARE_LENGTH, SQUARE_LENGTH, SQUARE_LENGTH);
        }
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        Label infoLabel = new Label("(" + column + ", " + row + ") " + city.getEntityAt(column, row));
        infoLabel.setPadding(new Insets(3, 0, 0, 0));
        root.setBottom(infoLabel);
    }

    public void drawCity(GraphicsContext gc) {
        int x = 0;
        int y;

        for(int i = 0; i < city.getWidth(); ++i) {
            y = 0;
            for(int j = 0; j < city.getHeight(); ++j) {
                Paint squarePaint = Color.BLACK; // By default
                if (city.getEntityAt(i, j) != null) {
                    squarePaint = city.getEntityAt(i, j).fxRepresentation();
                }
                gc.setFill(squarePaint);
                gc.fillRect(x, y, SQUARE_LENGTH, SQUARE_LENGTH);
                y += SQUARE_LENGTH;
            }
            x += SQUARE_LENGTH;
        }
        //System.out.println("[DEBUG] City redrawn");
    }
}
