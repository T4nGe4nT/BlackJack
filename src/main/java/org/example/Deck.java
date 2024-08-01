package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements DeckActions {
    private List<Card> myCards;
    private int numCards;

    public Deck() {
        List<String> values = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace");
        List<String> suits = List.of("Hearts", "Diamonds", "Clubs", "Spades");

        myCards = new ArrayList<>();

        for (String value : values) {
            for (String suit : suits) {
                myCards.add(new Card(value, suit));
            }
        }
        numCards = myCards.size();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(myCards);
    }

    @Override
    public Card dealNextCard() {
        if (numCards == 0) {
            return null; // No more cards to deal
        }
        Card nextCard = myCards.remove(myCards.size() - 1);
        numCards--;
        return nextCard;
    }

    public void FiftyTwoPickUp() {
        Collections.shuffle(myCards);
        System.out.println("Have fun lil bro....");

        try {
            Thread.sleep(1000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Card card : myCards) {
            System.out.println(card);
        }
    }
}
