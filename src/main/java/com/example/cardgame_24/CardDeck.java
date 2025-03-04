package com.example.cardgame_24;

import java.util.*;

public class CardDeck {
    private static final String[] SUITS = {"hearts", "diamonds", "clubs", "spades"}; // Collection of possible suits
    private static final int[] VALUES = {1,2,3,4,5,6,7,8,9,10,11,12,13}; // Collection of possible values
    // Face Card Values: {ACE = 1}, {JACK = 11}, {QUEEN = 12}, {KING = 13}

    private final Map<String, Card> deckMap = new HashMap<>(); //HashMap to store cards

    /**
     *
     */
    public CardDeck(){
        loadDeck();
    }

    private void loadDeck(){
        for(String suit : SUITS){
            for(int value : VALUES){
                String key = value + "_" + suit; //HashMap Key Creation (2_hearts)
                String fileName = "/cards/" + value + "_of_" + suit + ".png"; // Creating filePath
                deckMap.put(key, new Card(value, suit, fileName)); // Store card in HashMap
            }
        }
    }

    public List<Card> getRandomCards(){
        List<String> keys = new ArrayList<>(deckMap.keySet()); //Retrieve all keys from HashMap
        Collections.shuffle(keys); //Shuffle deck of cards
        List<Card> selectedCards = new ArrayList<>();

        //Select the first four keys (cards) from the shuffled list
        for(int i = 0; i < 4; i++){
            selectedCards.add(deckMap.get(keys.get(i))); //Retrieve Card(object) via it's key
        }

        return selectedCards;

    }

}
