package com.example.cardgame_24;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a deck object, holding card objects
 */
public class cardDeck {
    private List<Card> deck = new ArrayList<>();
    private Random rnd = new Random();

    /**
     * Constructs a deck of playing cards
     * Generates cards with suits and values ranging from 2-10 as well as face cards (1, 11-13)
     */
    public cardDeck(){
        String[] SUITS = {"clubs", "diamonds", "hearts", "spades"};
        String[] FACES = {"jack", "queen", "king", "ace"};

        // Creates numCard fileName
        for (int i = 2; i < 11; i++) {
            for (String suit: SUITS) {
                String fileName = i + "_of_" + suit + ".png";
                Card c = new Card(fileName);
                deck.add(c);
            }
        }
        // Creates faceCard fileName
        for (String face: FACES){
            for (String suit: SUITS){
                String fileName = face + "_of_" + suit + ".png";
                Card c = new Card(fileName);
                deck.add(c);
            }
        }
    }

    public List<Card> getDeck() {return deck;}

    /**
     * Removes and returns a random card from the deck
     * @return
     */
    public Card deal(){
        int index = rnd.nextInt(deck.size());
        return deck.remove(index);
    }
}
