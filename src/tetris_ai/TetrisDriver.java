package tetris_ai;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisDriver extends Application {

    private static final double GAME_SPEED = 1.0;

    private static final int[] RIGHT_MOVE = new int[] {1, 0};
    private static final int[] LEFT_MOVE = new int[] {-1, 0};
    private static final int[] DOWN_MOVE = new int[] {0, 1};

    private TetrisView tetrisView = new TetrisView();
    private Tetromino currentPiece;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        StackPane root = new StackPane();
        root.getChildren().add(tetrisView);
        Scene scene = new Scene(root);

        // event listeners for manual commands
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                tetrisView.doMove(currentPiece, RIGHT_MOVE);
            } else if (key.getCode() == KeyCode.LEFT) {
                tetrisView.doMove(currentPiece, LEFT_MOVE);
            } else if (key.getCode() == KeyCode.UP) {
                tetrisView.rotatePiece(currentPiece);
            } else if (key.getCode() == KeyCode.DOWN) {
                tetrisView.doMove(currentPiece, DOWN_MOVE);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        currentPiece = new Tetromino();
        tetrisView.addPiece(currentPiece);

        // game loop
        Timeline timeline = new Timeline(1.0);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(GAME_SPEED), new GameHandler()));
        timeline.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class GameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!tetrisView.isLegal(currentPiece, DOWN_MOVE)) {
                tetrisView.placePiece(currentPiece);
                tetrisView.clearLines();
                currentPiece = new Tetromino();
            } else {
                tetrisView.doMove(currentPiece, DOWN_MOVE);
            }
        }
    }
}
