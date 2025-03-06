package com.example.cardgame_24;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
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

    }

    @FXML
    public void dealCards(){
        cards = new int[4];
        if(deck.getDeck().size() >=4){
            for (int i = 0; i < 4; i++) {
                Card c = deck.deal();
                cardViews[i].setImage(c.getImage());
                cards[i] = c.getValue();
            }
        }

    }


}