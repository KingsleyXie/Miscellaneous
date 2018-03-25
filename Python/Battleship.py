from random import randint

board = []

for x in range(5):
	board.append(['O'] * 5)

def print_board():
	for row in board:
		print('  '.join(row))

print('\nLet\'s play Battleship!\n')
print_board()

ship_row = randint(0, len(board) - 1)
ship_col = randint(0, len(board[0]) - 1)

print('\n-- Answer:  ({0}, {1}) --'.format(ship_row, ship_col))
