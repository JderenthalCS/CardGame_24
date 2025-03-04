package com.example.cardgame_24;

import javafx.scene.image.Image;

/**
 *  Object representation of playing card image.
 *  Numerical Value, Suit and Image.
 */
public class Card {
    private int value; //Numerical Value of Card
    private String suit; //Suit of Card
    private Image image; //Image of card

    /**
     * Constructor to initialize a card with its correct value, suit, and image.
     * @param value
     * @param suit
     * @param imagePath
     */
    public Card(int value, String suit, String imagePath){
        this.value = value;
        this.suit = suit;
        this.image = new Image(getClass().getResourceAsStream(imagePath)); //Loads image from resource
    }

    /**
     * Gets the numerical value of the card.
     * @return
     */
    public int getValue(){
        return value;
    }

    /**
     * Gets the Suit of the card.
     * @return
     */
    public String getSuit(){
        return suit;
    }

    /**
     * Gets the image of the card.
     * @return
     */
    public Image getImage(){
        return image;
    }
}
