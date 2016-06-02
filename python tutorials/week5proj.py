# implementation of card game - Memory

import simplegui
import random

cards = range(0,8) + range(0,8)
card_size = 800 // 16
start_pos = [card_size/4, card_size*1.3]
exposed = [False for _ in range(len(cards))]
cover = simplegui.load_image('http://i.imgur.com/jrgaGxU.jpg')
src_size = [cover.get_width(), cover.get_height()]
src_pos = [src_size[0] / 2, src_size[1] / 2];
nn = -1 # index of the latest card flipped
snn = -1 # index of the earlier latest card flipped
counter = 0 # counter of turns

# helper function to initialize globals
def new_game():
    global state, exposed, snn, nn, counter
    state = 0
    counter = 0
    label.set_text("Turns = " + str(counter))
    exposed = [False for _ in range(len(cards))]
    nn = -1
    snn = -1
    random.shuffle(cards)
    
    
# define event handlers
def mouseclick(pos):
    # add game state logic here
    global state, nn, snn, counter
    if (state == 0):
        cur = pos[0] // card_size
        if (not exposed[cur]):
            nn = index = cur
            exposed[nn] = True
            counter += 1
            state = 1
    elif (state == 1):
        cur = pos[0] // card_size
        if (not exposed[cur]):
            snn = nn
            nn = cur
            exposed[nn] = True
            counter += 1
            state = 2
    else:
        cur = pos[0] // card_size
        if (not exposed[cur]):
            if (cards[nn] != cards[snn]):
                exposed[nn] = exposed[snn] = False
            nn = cur
            exposed[nn] = True
            counter += 1
            state = 1
    label.set_text("Turns = " + str(counter))
    
                        
# cards are logically 50x100 pixels in size    
def draw(canvas):
    for card_index in range(len(cards)):
        if (exposed[card_index]):
            canvas.draw_text(str(cards[card_index]), [start_pos[0]+card_index*card_size, start_pos[1]], card_size, "White")
        else:
            canvas.draw_image(cover, src_pos, src_size, [25+card_index*50, 50], [50, 100])


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()


# Always remember to review the grading rubric