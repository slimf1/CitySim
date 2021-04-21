package com.isima.sma.ui;

import com.isima.sma.city.City;
import com.isima.sma.entities.Road;
import com.isima.sma.entities.Zone;
import com.isima.sma.entities.ZoneType;
import com.isima.sma.utils.Clock;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class FxUserInterface extends Application {

    private static final int SQUARE_LENGTH = 20;
    private static final int CLOCK_X = 200;
    private static final int CLOCK_Y = 0;
    private static final int CLOCK_R = 25;

    private City city;
    private BorderPane root;
    private Canvas cityCanvas;
    private ZoneType selectedZoneType;
    private Timer autoPlayTimer;
    private Label hoverLabel;
    private boolean removeOnClick;
    private VBox controlPanel;
    private Group clockGroup;

    public FxUserInterface() {
        this.city = new City(30, 20);
        this.root = new BorderPane();
        this.cityCanvas = new Canvas(city.getWidth() * SQUARE_LENGTH, city.getHeight() * SQUARE_LENGTH);
        this.selectedZoneType = null;
        this.autoPlayTimer = null;
        this.hoverLabel = new Label();
        this.removeOnClick = false;
        this.controlPanel = new VBox();
        hoverLabel.setPadding(new Insets(3, 0, 0, 0));
        root.setBottom(hoverLabel);

        // Methodes pour "stepAndRedraw()" et infos (labels attr)
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

        Label titleLabel = new Label("CitySim");
        titleLabel.setFont(new Font("Arial", 30));
        titleLabel.setTextFill(Color.web("#0076a3"));
        titleLabel.setPadding(new Insets(5)); //top, right, bottom, left
        controlPanel.getChildren().add(titleLabel);

        // Clock
        Circle clockCircle = new Circle();
        clockCircle.setCenterX(CLOCK_X);
        clockCircle.setCenterY(CLOCK_Y);
        clockCircle.setRadius(CLOCK_R);
        clockCircle.setFill(Color.GREY);
        clockCircle.setOnMouseMoved(this::onClockMouseMoved);

        Line clockTick = new Line(CLOCK_X, CLOCK_Y, CLOCK_X, CLOCK_Y - CLOCK_R);
        clockTick.setFill(Color.BEIGE);
        clockGroup = new Group();
        clockGroup.getChildren().add(clockCircle);
        clockGroup.getChildren().add(clockTick);
        controlPanel.getChildren().add(clockGroup);

        // End Clock

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
        oneStepButton.setOnAction(e -> stepAndRedraw());
        stepButtonsBox.getChildren().add(oneStepButton);

        Button autoStepButton = new Button("Start/Stop auto steps");
        autoStepButton.setOnAction(e -> {
            if (autoPlayTimer == null) {
                autoPlayTimer = new Timer();
                autoPlayTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        stepAndRedraw();
                    }
                }, 0, 20); // Set speed
            } else {
                autoPlayTimer.cancel();
                autoPlayTimer = null;
            }
        });
        stepButtonsBox.getChildren().add(autoStepButton);
        controlPanel.getChildren().add(stepButtonsBox);

        Button deleteButton = new Button("Remove");
        deleteButton.setOnAction(e -> {
            removeOnClick = !removeOnClick;
            System.out.println("x = " + removeOnClick);
        });
        controlPanel.getChildren().add(deleteButton);

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

    /**
     * Met à jour le label d'information quand on
     * survole l'horloge
     * @author Slimane F.
     * @param event L'évènement de la souris
     */
    private void onClockMouseMoved(MouseEvent event) {
        int ticks = Clock.getInstance().getTime();
        String timestamp = Clock.formatTime(ticks);
        hoverLabel.setText(timestamp);
    }

    private void stepAndRedraw() {
        city.step();
        drawCity(cityCanvas.getGraphicsContext2D());
        Clock.getInstance().incrementTime(1);
        city.setTimeOfDay();
        drawClock();
    }

    private void handleMouseClicked(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        boolean redraw = false;

        if (removeOnClick && city.removeAt(column, row)) {
            redraw = true;
        } else if (selectedZoneType == null) {
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
            if (city.getEntityAt(column, row) != null)
                gc.setFill(city.getEntityAt(column, row).fxRepresentation());
            else
                gc.setFill(Color.BLACK);
            gc.fillRect(column * SQUARE_LENGTH, row * SQUARE_LENGTH, SQUARE_LENGTH, SQUARE_LENGTH);
            if(city.getEntityAt(column, row) instanceof Road){
                drawConnectedRoads(column, row, gc);
                drawConnectedRoads(column, row-1, gc);
                drawConnectedRoads(column, row+1, gc);
                drawConnectedRoads(column-1, row, gc);
                drawConnectedRoads(column+1, row, gc);
            }
        }
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        int column = (int)mouseEvent.getX() / SQUARE_LENGTH;
        int row = (int)mouseEvent.getY() / SQUARE_LENGTH;
        hoverLabel.setText("(" + column + ", " + row + ") " + city.getEntityAt(column, row));
    }

    public void drawCity(GraphicsContext gc) {
        int x = 0;
        int y;

        for(int i = 0; i < city.getWidth(); ++i) {
            y = 0;
            for(int j = 0; j < city.getHeight(); ++j) {
                Paint squarePaint = Color.WHITE; // By default
                if (city.getEntityAt(i, j) != null) {
                    squarePaint = city.getEntityAt(i, j).fxRepresentation();
                }
                gc.setFill(squarePaint);
                gc.fillRect(x, y, SQUARE_LENGTH, SQUARE_LENGTH);

                // If drawing a road
                if(city.getEntityAt(i, j) instanceof Road){
                    // Connecting roads
                    drawConnectedRoads(i, j, gc);
                }
                y += SQUARE_LENGTH;
            }
            x += SQUARE_LENGTH;
        }
    }

    public void drawClock(){
        double angle = - 2 * Math.PI * ((double)Clock.getInstance().getTime() / Clock.TICK_MAX);
        int x = CLOCK_X - (int)(CLOCK_R * Math.sin(angle));
        int y = CLOCK_Y - (int)(CLOCK_R * Math.cos(angle));
        int index = controlPanel.getChildren().indexOf(clockGroup);
        for(Node n : ((Group)controlPanel.getChildren().get(index)).getChildren()){
            if(n instanceof Line){
                ((Line)n).setEndX(x);
                ((Line)n).setEndY(y);
            }
        }
    }

    private void drawConnectedRoads(int i, int j, GraphicsContext gc) {
        boolean connected = false;
        gc.setFill(Color.WHITE);
        double roadPaint = SQUARE_LENGTH / 9;
        int x = i * SQUARE_LENGTH;
        int y = j * SQUARE_LENGTH;

        // Road up
        if(j-1 >= 0 && city.getEntityAt(i,j-1) instanceof Road){
            connected = true;
            gc.fillRect(x + 4 * roadPaint, y + 2 * roadPaint, 2 * roadPaint, 2 * roadPaint);
        }
        // Road down
        if(j+1 < city.getHeight() && city.getEntityAt(i,j+1) instanceof Road){
            connected = true;
            gc.fillRect(x + 4 * roadPaint, y + 6 * roadPaint, 2 * roadPaint, 2 * roadPaint);
        }
        // Road left
        if(i-1 >= 0 && city.getEntityAt(i-1,j) instanceof Road){
            connected = true;
            gc.fillRect(x + 2 * roadPaint, y + 4 * roadPaint, 2 * roadPaint, 2 * roadPaint);
        }
        // Road right
        if(i+1 < city.getWidth() && city.getEntityAt(i+1,j) instanceof Road){
            connected = true;
            gc.fillRect(x + 6 * roadPaint, y + 4 * roadPaint, 2 * roadPaint, 2 * roadPaint);
        }

        if(connected){
            gc.fillRect(x + 4 * roadPaint, y + 4 * roadPaint, 2 * roadPaint, 2 * roadPaint);
        }
    }

}
