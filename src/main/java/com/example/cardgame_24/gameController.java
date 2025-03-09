package com.example.cardgame_24;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller class for Card Game 24
 * Game Logic, UI interaction, sound effects
 */
public class gameController implements Initializable {

    // Card Displays
    @FXML private ImageView[] cardViews;
    @FXML private ImageView card1; //Card 1
    @FXML private ImageView card2; //Card 2
    @FXML private ImageView card3; //Card 3
    @FXML private ImageView card4; //Card 4

    // Buttons
    @FXML private Button findSolutionButton;
    @FXML private Button refreshButton;
    @FXML private Button verifyButton;

    // TextFields
    @FXML private TextField expressionInput;
    @FXML private TextField solutionDisplay;

    // cardDeck
    private cardDeck deck; // Creates a deck of cards

    // cards
    private int[] cards;

    /**
     * Initializes the game by setting up event handles, dealing initial cards
     * @param url
     * @param resouceBundle
     */
    public void initialize(URL url, ResourceBundle resouceBundle){
        deck = new cardDeck(); // Creates a deck of cards
        cardViews = new ImageView[]{card1, card2, card3, card4};
        dealCards();
        verifyButton.setOnAction(event -> expressionChecker());
        findSolutionButton.setOnAction(event -> findSolution());

        expressionInput.requestFocus();

    }

    //Sounds

    /**
     * Prepares shuffle.mp3 to play when 'deal' button is clicked.
     */
    private void playShuffleSound(){
        URL url = getClass().getResource("/com/example/cardgame_24/sounds/shuffle.mp3");

        if(url == null){
            System.out.println("Error: shuffle.mp3 not found");
            return;
        }

        String soundPath = url.toExternalForm();
        AudioClip shuffleSound = new AudioClip(soundPath);
        shuffleSound.play();
    }

    /**
     * Prepares hint-chime.mp3 to play when 'deal' button is clicked.
     */
    private void playHintChime(){
        URL url = getClass().getResource("/com/example/cardgame_24/sounds/hint-chime.mp3");
        if(url == null){
            System.out.println("Error: hint-chime.mp3 not found");
            return;
        }

        String soundPath = url.toExternalForm();
        AudioClip hintSound = new AudioClip(soundPath);
        hintSound.play();
    }


    /**
     * Deals four random cards from the deck, updates UI, and plays shuffle sound effect.
     */
    @FXML
    public void dealCards(){
        cards = new int[4];
        if(deck.getDeck().size() >=4) {
            deck = new cardDeck();
        }

        cards = new int[4];
        for (int i = 0; i < 4; i++) {
            Card c = deck.deal();
            cardViews[i].setImage(c.getImage());
            cards[i] = c.getValue();
            }

        solutionRevealed = false;
        solutionDisplay.setText("");
        playShuffleSound();
        }

    /**
     * Checks if userInput correctly evalutes to 24
     */
    @FXML
    public void expressionChecker(){
        String userInput = expressionInput.getText().trim();

        if(!validateExpression(userInput, cards)){
            showAlert("Invalid Input", "Your expression must use each card value");
            return;
        }
    try{
        double result = evaluateExpression(userInput);

        if(Math.abs(result - 24) < 0.0001){
            showAlert("Success!", "Correct! Your expression equals 24.");
        } else {
            showAlert("Incorrect", "Your expression does not equal 24.");
        }
    } catch (Exception e){
        showAlert("Error", "Invalid mathematical expression.");
    }
    }

    /**
     * Validates userInput contains all four provided cards
     * @param input
     * @param values
     * @return
     */
    private boolean validateExpression(String input, int[] values){
        String[] tokens = input.replaceAll("[^0-9]", " ").trim().split("\\s+");

        if(tokens.length != 4) return false;

        int[] inputValues = Arrays.stream(tokens).mapToInt(Integer::parseInt).sorted().toArray();
        int[] expectedValues = Arrays.stream(values).sorted().toArray();

        return Arrays.equals(inputValues, expectedValues);
    }

    /**
     * Evaluates mathematical expression
     * @param input
     * @return
     */
    private double evaluateExpression(String input) {
        Expression expression = new ExpressionBuilder(input).build();
        return expression.evaluate();

    }

    /**
     * Displays alerts (pop-up) with a given title or message
     * @param title
     * @param message
     */
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private boolean solutionRevealed = false;

    /**
     * Finds and reveals a solution to 24 using provided cards
     * If clicked once, shows a masked version (_ for numbers), if clicked again reveals full equation
     */
    @FXML
    public void findSolution(){
        String solution = solve24(cards);
        if(solution == null){
            solutionDisplay.setText("No solution found.");
            return;
        }

        if (!solutionRevealed) {
            solutionDisplay.setText(maskSolution(solution));
            solutionRevealed = true;
        } else {
            solutionDisplay.setText(solution);
            solutionRevealed = false;
        }

        expressionInput.requestFocus();
        playHintChime();
    }

    /**
     * Replaces numbers in the solution with underscores (_), providing a hint
     * @param solution
     * @return
     */
    private String maskSolution(String solution){
        return solution.replaceAll("\\d+", "_");
    }

    /**
     * Attempts to find a valid equation that results in 24 using provided cards.
     * @param values
     * @return
     */
    private String solve24(int[] values) {
        String[] operators = {"+", "-", "*", "/"};

        for (int a : values) {
            for (int b : values) {
                if (b == a) continue;
                for (int c : values) {
                    if (c == a || c == b) continue;
                    for (int d : values) {
                        if (d == a || d == b || d == c) continue;

                        for (String op1 : operators) {
                            for (String op2 : operators) {
                                for (String op3 : operators) {

                                    String[] expressions = {
                                            "(" + a + op1 + b + ")" + op2 + "(" + c + op3 + d + ")",
                                            "((" + a + op1 + b + ")" + op2 + c + ")" + op3 + d,
                                            "(" + a + op1 + "(" + b + op2 + c + "))" + op3 + d,
                                            a + op1 + "(" + b + op2 + "(" + c + op3 + d + "))",
                                            "(" + a + op1 + b + ")" + op2 + c + op3 + d
                                    };

                                    for (String expr : expressions) {
                                        try {
                                            if (Math.abs(evaluateExpression(expr) - 24) < 0.0001) {
                                                return expr;
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // No solution found
    }


    @FXML
    private void onRefreshClicked(){
        dealCards();
        expressionInput.clear();
        solutionDisplay.clear();

        expressionInput.requestFocus();
    }

    @FXML
    private void onVerifyClicked(){
        expressionChecker();
    }

}