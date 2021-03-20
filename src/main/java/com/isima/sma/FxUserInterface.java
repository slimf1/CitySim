package com.isima.sma;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FxUserInterface extends Application {

    private static final int SQUARE_LENGTH = 20;

    private City city;
    private BorderPane root;
    private Canvas cityCanvas;

    public FxUserInterface() {
        this.city = new City(10, 10);
        this.root = new BorderPane();
        this.cityCanvas = new Canvas(city.getWidth() * SQUARE_LENGTH, city.getHeight() * SQUARE_LENGTH);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Group controlGroup = new Group();
        Label titleLabel = new Label("CitySim");
        titleLabel.setFont(new Font("Arial", 30));
        titleLabel.setTextFill(Color.web("#0076a3"));
        titleLabel.setPadding(new Insets(5)); //top, right, bottom, left
        controlGroup.getChildren().add(titleLabel);
        root.setRight(controlGroup);

        // + hover => info
        // Menu bar
        MenuBar menuBar = new MenuBar();
        Menu viewMenu = new Menu("View");
        RadioMenuItem zoneItem = new RadioMenuItem("Zones");
        //zoneItem.setOnAction(e -> setView(View.ZONES));
        RadioMenuItem vehiclesItem = new RadioMenuItem("Vehicles");
        //vehiclesItem.setOnAction(e -> setView(View.VEHICLES));


        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(zoneItem);
        toggleGroup.getToggles().add(vehiclesItem);

        viewMenu.getItems().add(zoneItem);
        viewMenu.getItems().add(vehiclesItem);
        menuBar.getMenus().add(viewMenu);
        HBox hbox = new HBox(menuBar);
        //root.setTop(hbox);

        primaryStage.show();
    }

    private void handleMouseClicked(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        if (city.addRoad(column, row)) {
            drawCity(cityCanvas.getGraphicsContext2D());
            System.out.println("DEBUG: canvas redrawn");
        }
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        Label infoLabel = new Label(String.format("(%d,%d) %s", row, column, city.getEntityAt(column, row)));
        infoLabel.setPadding(new Insets(3, 0, 0, 0));
        root.setBottom(infoLabel);
    }

    public void drawCity(GraphicsContext gc) {
        int x = 0;
        int y;

        for(int i = 0; i < city.getHeight(); ++i) {
            y = 0;
            for(int j = 0; j < city.getWidth(); ++j) {
                Paint squarePaint = Color.BLACK; // By default
                if (city.isRoad(i, j)) {
                    squarePaint = city.getEntityAt(i, j).fxRepresentation();
                } else if (city.getEntityAt(i, j) != null) {
                    squarePaint = city.getEntityAt(i, j).fxRepresentation();
                }
                gc.setFill(squarePaint);
                gc.fillRect(x, y, SQUARE_LENGTH, SQUARE_LENGTH);
                y += SQUARE_LENGTH;
            }
            x += SQUARE_LENGTH;
        }
    }
}
