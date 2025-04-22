package generalities.concurrency.locks.cryptocurrencyApp;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class CryptoCurrencyApp extends Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Data
        PricesContainer pricesContainer = new PricesContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);

        // set up the UI
        int width = 300;
        int height = 250;
        Map<String, Label> currencyLabels = createCurrencyLabels();
        GridPane gridPane = createGrid();
        addLabelsToGrid(currencyLabels, gridPane);
        Rectangle background = createBackBackgroundRectangleWithAnimation(width, height);

        // StackPane ( root )
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(background);
        stackPane.getChildren().add(gridPane);

        // update UI data
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pricesContainer.getLock().tryLock()) {
                    // got the lock to shared data, let's read it
                    try {
                        currencyLabels.get("BTC").setText(String.valueOf(pricesContainer.getBitcoin()));
                        currencyLabels.get("ETH").setText(String.valueOf(pricesContainer.getEtherium()));
                        currencyLabels.get("LTC").setText(String.valueOf(pricesContainer.getLightcoin()));
                        currencyLabels.get("XRP").setText(String.valueOf(pricesContainer.getRipples()));

                    } finally {
                        pricesContainer.getLock().unlock();
                    }
                }
            }
        };
        animationTimer.start();
        priceUpdater.start();

        // Stage ( frame )
        stage.setTitle("Cryptocurrency Prices");
        stage.setScene(new Scene(stackPane, width, height));
        stage.show();
//        stage.getOnCloseRequest().handle(WindowEvent.WINDOW_CLOSE_REQUEST);
    }

    private Map<String, Label> createCurrencyLabels() {
        Label bitcoinPrice = new Label("0");
        bitcoinPrice.setId("BTC");

        Label etherumPrice = new Label("0");
        etherumPrice.setId("ETH");

        Label lightcoinPrice = new Label("0");
        lightcoinPrice.setId("LTC");

        Label ripplePrice = new Label("0");
        ripplePrice.setId("XRP");

        Map<String, Label> currencyLabelsMap = new HashMap<>();
        currencyLabelsMap.put(bitcoinPrice.getId(), bitcoinPrice);
        currencyLabelsMap.put(etherumPrice.getId(), etherumPrice);
        currencyLabelsMap.put(lightcoinPrice.getId(), lightcoinPrice);
        currencyLabelsMap.put(ripplePrice.getId(), ripplePrice);
        return currencyLabelsMap;
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private void addLabelsToGrid(Map<String, Label> currencyLabels, GridPane gridPane) {
        int row = 0;

        for (Map.Entry<String, Label> currency : currencyLabels.entrySet()) {

            Label nameLabel = new Label(currency.getKey());
            nameLabel.setTextFill(Color.BLUE);
            nameLabel.setOnMousePressed(mouseEvent -> nameLabel.setTextFill(Color.RED));
            nameLabel.setOnMouseReleased(mouseEvent -> nameLabel.setTextFill(Color.BLUE));

            gridPane.add(nameLabel, 0, row);
            gridPane.add(currency.getValue(), 1, row);
            row++;
        }

    }

    private Rectangle createBackBackgroundRectangleWithAnimation(int width, int height) {
        Rectangle background = new Rectangle(width, height);
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return background;
    }
}
