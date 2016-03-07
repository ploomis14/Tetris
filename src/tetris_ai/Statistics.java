package tetris_ai;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Peter Loomis
 */
public class Statistics extends VBox {

    public static final int LINE_SCORE_1 = 40;
    public static final int LINE_SCORE_2 = 100;
    public static final int LINE_SCORE_3 = 300;
    public static final int LINE_SCORE_4 = 1200;

    private static final double LABEL_WIDTH = 100;

    private int lines;
    private int score;

    private Label linesText;
    private Label scoreText;
    private Label endGameText;

    public Statistics() {
        lines = 0;
        score = 0;

        endGameText = new Label();

        Label scoreLabel = new Label();
        scoreLabel.setText("Score:");
        scoreText = new Label();
        scoreText.setText("" + score);
        scoreText.setMinWidth(LABEL_WIDTH);
        HBox scoreBox = new HBox();
        scoreBox.setSpacing(10);
        scoreBox.getChildren().addAll(scoreLabel, scoreText);

        Label linesLabel = new Label();
        linesLabel.setText("Lines:");
        linesText = new Label();
        linesText.setText("" + lines);
        linesText.setMinWidth(LABEL_WIDTH);
        HBox linesBox = new HBox();
        linesBox.setSpacing(10);
        linesBox.getChildren().addAll(linesLabel, linesText);

        this.setSpacing(20);
        this.getChildren().addAll(scoreBox, linesBox, endGameText);
    }

    public void update(int linesCleared) {
        updateLines(linesCleared);
        switch (linesCleared) {
            case 1:
                updateScore(LINE_SCORE_1);
                break;

            case 2:
                updateScore(LINE_SCORE_2);
                break;

            case 3:
                updateScore(LINE_SCORE_3);
                break;

            case 4:
                updateScore(LINE_SCORE_4);
                break;

            default:
                break;
        }
    }

    public void showEndgameMessage() {
        endGameText.setText("GAME OVER");
    }

    private void updateScore(int score) {
        this.score += score;
        scoreText.setText("" + this.score);
    }

    private void updateLines(int lines) {
        this.lines += lines;
        linesText.setText("" + this.lines);
    }
}