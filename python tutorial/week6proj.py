# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

# initialize some useful global variables
in_play = False
outcome = ""
score = 0

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}

#define globals for players draw
DEALER_POS = [50, 200]
PLAYER_POS = [50, 400]

# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        self.cards = list()

    def __str__(self):
        info = "Hand contains "
        for card in self.cards:
            info += card.get_suit() + card.get_rank() + ' '
        return info

    def add_card(self, card):
        self.cards.append(card)

    def get_value(self):
        handvals = map(lambda x: VALUES[x.get_rank()], self.cards)
        totalval = sum(handvals)
        if 1 in handvals and totalval+10 <= 21:
            totalval += 10
        return totalval
   
    def draw(self, canvas, pos):
        loc = list(pos)
        for card in self.cards:
            card.draw(canvas, loc)
            loc[0], loc[1] = loc[0]+CARD_SIZE[0], loc[1]
        
# define deck class 
class Deck:
    def __init__(self):
        self.cardlist = []
        for suit in SUITS:
            for rank in RANKS:
                self.cardlist.append(Card(suit, rank))

    def shuffle(self):
        random.shuffle(self.cardlist)

    def deal_card(self):
        return self.cardlist.pop(-1)
    
    def __str__(self):
        return "Deck contains " + ' '.join([card.__str__() for card in self.cardlist])



#define event handlers for buttons
def deal():
    global outcome, in_play, my_deck, player_hand, dealer_hand, score
    if in_play:
        outcome, in_play = "Player lost the round. New deal?", False
        score -= 1
    else:
        player_hand = Hand()
        dealer_hand = Hand()
        my_deck = Deck()
        my_deck.shuffle()
        for _ in range(2):
            player_hand.add_card(my_deck.deal_card())
            dealer_hand.add_card(my_deck.deal_card())
        outcome, in_play = "Hit or stand?", True

def hit():
    # replace with your code below
    global outcome, in_play, my_deck, player_hand, score
    # if the hand is in play, hit the player
    if in_play:
        player_hand.add_card(my_deck.deal_card())
    # if busted, assign a message to outcome, update in_play and score
    if player_hand.get_value() > 21:
        outcome, in_play = "You have busted", False
        score -= 1
       
def stand():
    # replace with your code below
    global outcome, in_play, my_deck, player_hand, dealer_hand, score
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more
    if in_play:
        while (dealer_hand.get_value() < 17):
            dealer_hand.add_card(my_deck.deal_card())
    # assign a message to outcome, update in_play and score
    if (dealer_hand.get_value() > 21):
        outcome, in_play = "Dealer busted", False
        score += 1
    else:
        if (dealer_hand.get_value() >= player_hand.get_value()):
            outcome, in_play = "Dealer wins", False
            score -= 1
        else:
            outcome, in_play = "You win", False
            score += 1

# draw handler    
def draw(canvas):
    # test to make sure that card.draw works, replace with your code below
    global outcome, in_play, my_deck, player_hand, deal_hand, score
    canvas.draw_text("Blackjack", (50, 50), 30, 'Blue')
    canvas.draw_text("score = "+str(score), (200, 50), 20, 'Black')
    canvas.draw_text(outcome, (50, 100), 30, 'Black')
    player_hand.draw(canvas, PLAYER_POS)
    dealer_hand.draw(canvas, DEALER_POS)
    if in_play:
        canvas.draw_image(card_back, CARD_BACK_CENTER, CARD_BACK_SIZE, [DEALER_POS[0]+CARD_BACK_CENTER[0], DEALER_POS[1]+CARD_BACK_CENTER[1]], CARD_BACK_SIZE)


# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling
deal()
frame.start()


# remember to review the gradic rubric