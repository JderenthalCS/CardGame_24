package com.example.cardgame_24;

import javafx.scene.image.Image;

/**
 * Represents an object playing card with a value, image and suit
 */
public class Card {
    private final int value;
    private final Image image;

    public Card(){
        value = 0;
        image = null;
    }

    /**
     * Constructs the Card object
     * Takes the numerical value from the file name and loads the corresponding image
     * @param file
     */
    public Card(String file){
        this.value = getCardValue(file);
        this.image = new Image(getClass().getResource("cards/"+file).toExternalForm());
    }

    public int getValue(){ return this.value;}

    public Image getImage(){return this.image;}

    /**
     * Determines the value of a card from its filename
     * @param file
     * @return
     */
    private int getCardValue(String file){
        String[] data = file.split("_");
        String val = data[0];
        return switch(val){
            case "ace":
                yield 1;
            case "jack":
                yield 11;
            case "queen":
                yield 12;
            case "king":
                yield 13;
            default:
                if(val.matches("\\d+")){
                    yield Integer.parseInt(val);
                } else throw new IllegalArgumentException("Error: Invalid File Type");
        };
    }

}
