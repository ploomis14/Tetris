package tetris_ai;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Queue;

public class TetrisDriver extends Application {

    private static final int PIECE_PREDICTION = 1;
    private static final double GAME_SPEED = 0.75;
    private static final boolean AI_MODE = true;

    private TetrisView tetrisView;
    private Statistics statistics;
    private Tetromino currentPiece;
    private TetrisAI ai;
    private Queue<Move> currentMoves;

    @Override
    public void start(Stage primaryStage) throws Exception {
        tetrisView = new TetrisView();
        currentPiece = new Tetromino();
        ai = new TetrisAI(PIECE_PREDICTION);
        if (AI_MODE) {
            currentMoves = ai.getMoveSequence(tetrisView.getOccupiedGrid(), currentPiece);
        }
        tetrisView.addPiece(currentPiece);
        statistics = new Statistics();

        HBox vb = new HBox();
        vb.getChildren().addAll(statistics, tetrisView);
        vb.setSpacing(10);
        vb.setPadding(new Insets(50, 10, 10, 50));

        primaryStage.setTitle("Tetris");
        StackPane root = new StackPane();
        root.getChildren().add(vb);
        Scene scene = new Scene(root);

        // event listeners for manual commands
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (!AI_MODE) {
                if (key.getCode() == KeyCode.RIGHT) {
                    tetrisView.doMove(currentPiece, Move.RIGHT.getValues());
                } else if (key.getCode() == KeyCode.LEFT) {
                    tetrisView.doMove(currentPiece, Move.LEFT.getValues());
                } else if (key.getCode() == KeyCode.UP) {
                    tetrisView.rotatePiece(currentPiece);
                } else if (key.getCode() == KeyCode.DOWN) {
                    tetrisView.doMove(currentPiece, Move.DOWN.getValues());
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

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
            if (tetrisView.gameOver()) {
                statistics.showEndgameMessage();
            } else if (!Grid.isLegalMove(currentPiece, tetrisView.getOccupiedGrid(), Move.DOWN.getValues())) {
                tetrisView.placePiece(currentPiece);
                int lines = tetrisView.clearLines();
                statistics.update(lines);
                currentPiece = new Tetromino();
                if (AI_MODE) {
                    try {
                        currentMoves = ai.getMoveSequence(tetrisView.getOccupiedGrid(), currentPiece.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (AI_MODE && currentMoves != null && !currentMoves.isEmpty()) {
                    Move nextMove = currentMoves.remove();
                    if (nextMove == Move.ROTATE) {
                        tetrisView.rotatePiece(currentPiece);
                    } else {
                        tetrisView.doMove(currentPiece, nextMove.getValues());
                    }
                }
                tetrisView.doMove(currentPiece, Move.DOWN.getValues());
            }
        }
    }
}
