package org.example;

import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome");
        System.out.println("What should I call you?");
        String playerName = sc.nextLine().trim();

        System.out.println("Hello " + playerName + ", would you like to play Blackjack or 52 Pick Up?");
        System.out.println("Enter 'b' for Blackjack or 'p' for 52 Pick Up:");
        String gameChoice = sc.nextLine().trim().toLowerCase();

        Deck deck = new Deck(); // Instantiate the deck

        if (gameChoice.equals("p")) {
            deck.FiftyTwoPickUp();
        } else {
            deck.shuffle();
            int playerBalance = 100; // Starting balance for player to bet
            boolean playAgain = true; // Setting up for the coming play loop

            while (playAgain) {
                System.out.println("Your current balance: $" + playerBalance); // Displaying current balance
                int bet = getPlayerBet(sc, playerBalance);

                Player player = new Player(playerName); // Instantiating our player & dealer
                Player dealer = new Player("Dealer");

                player.receiveCard(deck.dealNextCard()); // Dealing 2 cards to each for start
                player.receiveCard(deck.dealNextCard());
                dealer.receiveCard(deck.dealNextCard());
                dealer.receiveCard(deck.dealNextCard());

                System.out.println("\nInitial hands:"); // Player's hand
                player.printHand();
                System.out.println("\nDealer's visible card: " + dealer.getHand().get(0)); // Dealer's hand with only one card shown

                boolean playerTurn = true; // Starting player's turn, setting a state basically
                boolean doubledDown = false;

                while (playerTurn) {
                    System.out.println("\nYour current hand value is: " + player.calculateHandValue());
                    System.out.println("Would you like to hit, stand, or double down? (h/s/d)");
                    String action = sc.nextLine().trim().toLowerCase();

                    if (action.equals("h")) { // Hit sequence
                        player.receiveCard(deck.dealNextCard());
                        System.out.println("\nPlayer hits:");
                        player.printHand();

                        if (player.calculateHandValue() > 21) { // Bust check
                            System.out.println("Bust! Your hand value is over 21.");
                            playerTurn = false;
                        }
                    } else if (action.equals("s")) { // Stand sequence
                        System.out.println("\nPlayer stands with a hand value of: " + player.calculateHandValue());
                        playerTurn = false; // Setting value of state to pass turn

                    } else if (action.equals("d") && player.getHand().size() == 2 && !doubledDown) { // Double down
                        doubledDown = true;
                        if (bet * 2 <= playerBalance) {
                            bet *= 2;
                            System.out.println("Player doubles down. New bet: $" + bet);
                            player.receiveCard(deck.dealNextCard());
                            System.out.println("\nPlayer hits:");
                            player.printHand();
                            playerTurn = false;
                        } else {
                            System.out.println("Insufficient balance to double down.");
                        }
                    } else {
                        System.out.println("Can only double down on first hand... Sorry bud...");
                    }
                }

                // Dealer's turn
                System.out.println("\nDealer's turn:");
                dealer.printHand();

                while (dealer.calculateHandValue() < 17) { // Dealer hits if less than 17
                    dealer.receiveCard(deck.dealNextCard());
                    System.out.println("\nDealer hits:");
                    dealer.printHand();
                }

                if (dealer.calculateHandValue() > 21) {
                    System.out.println("Dealer busts! Player wins.");
                    playerBalance += bet;
                } else {
                    System.out.println("Dealer stands with a hand value of: " + dealer.calculateHandValue());
                    if (determineWinner(player, dealer)) {
                        playerBalance += bet;
                    } else {
                        playerBalance -= bet;
                    }
                }

                System.out.println("\nPlay again? (y/n)");
                playAgain = sc.nextLine().trim().equalsIgnoreCase("y");
            }

            System.out.println("Thanks for playing! Your final balance is: $" + playerBalance);
        }


        sc.close();
    }

    private static int getPlayerBet(Scanner sc, int playerBalance) {
        int bet = 0;
        while (bet <= 0 || bet > playerBalance || bet % 5 != 0) {
            System.out.println("Enter your bet (increments of 5): ");
            bet = Integer.parseInt(sc.nextLine().trim());
            if (bet <= 0 || bet > playerBalance || bet % 5 != 0) {
                System.out.println("Invalid bet amount. Please enter a valid amount in increments of 5.");
            }
        }
        return bet;
    }

    private static boolean determineWinner(Player player, Player dealer) {
        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();

        System.out.println("\nFinal Results:");
        player.printHand();
        dealer.printHand();

        if (playerValue > 21) {
            System.out.println("Player busts! Dealer wins.");
            return false;
        } else if (dealerValue > 21) {
            System.out.println("Dealer busts! Player wins.");
            return true;
        } else if (playerValue > dealerValue) {
            System.out.println("Player wins!");
            return true;
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins!");
            return false;
        } else {
            System.out.println("It's a tie!");
            return false; // This should not take the player bet!!!
        }
    }
}
