package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public int calculateHandValue() {
        int totalValue = 0;
        int numAces = 0;

        for (Card card : hand) {
            String value = card.getValue();

            if ("Ace".equals(value)) {
                numAces++;
                totalValue += 11; // Initially count ace as 11
            } else if ("King".equals(value) || "Queen".equals(value) || "Jack".equals(value)) {
                totalValue += 10;
            } else {
                totalValue += Integer.parseInt(value);
            }
        }


        while (totalValue > 21 && numAces > 0) {  // Adjust for Aces being 1 or 11
            totalValue -= 10; // Convert an Ace from 11 to 1
            numAces--;
        }

        return totalValue;
    }

    public void printHand() {
        System.out.println(name + "'s hand:");
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Total value: " + calculateHandValue());
    }


    public List<Card> getHand() { // Add this method to access the player's hand
        return hand;
    }
}
