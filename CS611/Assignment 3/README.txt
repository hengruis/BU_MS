Blackjack & Triantra Ena

Run instructions:
$ javac *.java
$ java CardGame

Game rule:
- Blackjack
  One live player plays against computer as dealer.

  First, one card is distributed to player and another to dealer. Both cards 
  are known to player and dealer. The cards are dealt again separately, but
  the dealer's one is unknown to player.

  Then player determines what to do next: hit, stand, double up or split. 
  Note that player can only split when two initial cards are the same value 
  and player can afford another same bet.

  Game is over if either wins or there is a tie. If player has no money left, 
  the game is not allowed to start.

- Trianta Ena
  Multiple players play together. One of the player computer as dealer. 
  Default banker is last one, change will be ask at end of every round in 
  turns of money amount.

  First, one card is distributed to players and banker. Only banker's card is 
  known to other players. Players take turns to decide bet or fold.

  Players who have bet take turns to receive 2 more hidden cards. Banker 
  receive 2 more hidden card.

  Action Part:(Not exactly same to piazza)
  Players take turns to take action. For example, from player 1 to player last one, take 1 action
    each turn. Players who has stand or fold or bust will not be asked to take actions. When all
     players are stand or bust or fold. Banker's hidden card will be revealed. And banker hit until
     over 27. If banker hit over 27, banker bust and all money for bet will be turned back (no matter
     player bust or not. Just like draw round). But no more winning prize for players who wins except
     Natural 31 players.

  Winning Condition:
  If player has value larger than banker's. He will get prize same as his bet. 
  If he has the value smaller or equal to banker's, he loses his bet. Natural 
  31 for players will be announced once players get 2 hidden card, and game 
  will continue for banker and other players. Natural 31 for banker will be 
  announced when banker reveals his hidden card, all bet of players will 
  belong to banker.

  Next round:
  Continue is asked at the end of every round. If you choose to continue, you 
  will be asked if willing to be the banker in turns of amount of money. If 
  no one wants to be the banker, game will end.

  Game is over when a round over and there exist a player has no money. Or 
  players choose to cash out.

=============================================================================
                              Class description
=============================================================================
1. BlackjackGame
    Inherits class "CardGame". Main Blackjack game logics. Controls the game 
  process.

2. BlackjackPlayer
    Inherits class "Player". Used for Blackjack game.

3. Card
    Implements card including its rank and suit as well as some common 
  operations.

4. CardGame
    Inherits class "Game". The main function is here.

5. Dealer
    The dealer of games. Contains a hand of cards of dealer.

6. Deck
    A deck of cards. Able to shuffle, take cards from deck, etc.

7. Game
    Can be implemented if other types of games added.

8. Hand
    Maintain the structure of cards, including fields of total values, bet 
  on the hand. Can calculate the values of the hand and determine if the 
  hand is bust or qualified to win. Also, can show all cards in the hand.

9. Player
    Save player's name and money.

10. TEGame
    Inherits class "CardGame". Main Trianta Ena game logics. Controls the 
  game process.

11. TEPlayer
    Inherits class "Player". Use for Trianta Ena players and banker.
