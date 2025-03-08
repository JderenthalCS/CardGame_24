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

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class gameController implements Initializable {

    @FXML private ImageView[] cardViews;

    // Card Displays
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

    private cardDeck deck; // Creates a deck of cards

    private int[] cards;

    public void initialize(URL url, ResourceBundle resouceBundle){
        deck = new cardDeck(); // Creates a deck of cards
        cardViews = new ImageView[]{card1, card2, card3, card4};
        dealCards();
        verifyButton.setOnAction(event -> expressionChecker());
        findSolutionButton.setOnAction(event -> findSolution());

    }

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
        }

    @FXML
    public void expressionChecker(){
        String userInput = expressionInput.getText().trim();

        if(!validateExpression(userInput, cards)){
            showAlert("Invalid Input", "Your expression must use each vard value");
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

    private boolean validateExpression(String input, int[] values){
        String[] tokens = input.replaceAll("[^0-9]", " ").trim().split("\\s+");

        if(tokens.length != 4) return false;

        int[] inputValues = Arrays.stream(tokens).mapToInt(Integer::parseInt).sorted().toArray();
        int[] expectedValues = Arrays.stream(values).sorted().toArray();

        return Arrays.equals(inputValues, expectedValues);
    }

    private double evaluateExpression(String input) {
        Expression expression = new ExpressionBuilder(input).build();
        return expression.evaluate();

    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private boolean solutionRevealed = false;
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
    }

    private String maskSolution(String solution){
        return solution.replaceAll("\\d+", "_");
    }

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