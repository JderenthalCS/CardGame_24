package com.example.cardgame_24;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

public class gameController {

    // Card Displays
    @FXML
    private ImageView card1; //Card 1
    @FXML
    private ImageView card2; //Card 2
    @FXML
    private ImageView card3; //Card 3
    @FXML
    private ImageView card4; //Card 4

    private final CardDeck deck = new CardDeck(); // Creates a deck of cards

    public void loadCards(){
        List<Card> selectedCards = deck.getRandomCards(); //Gets four random cards

        // Retrieves images for each card
        card1.setImage(selectedCards.get(0).getImage());
        card2.setImage(selectedCards.get(1).getImage());
        card3.setImage(selectedCards.get(2).getImage());
        card4.setImage(selectedCards.get(3).getImage());

    }


}