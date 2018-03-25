from random import randint

# Information texts
info = {
	'welcome': 'Let\'s play Battleship!',
	'success': 'Congratulations! You sunk my battleship!',
	'overflow': 'Oops, that\'s not even in the ocean.',
	'duplicate': 'You guessed that one already.',
	'missed': 'You missed my battleship!'
}

# Initialize the board
board = []
for x in range(5):
	board.append(['O'] * 5)

# Generate random ship position
ship_row = randint(0, len(board) - 1)
ship_col = randint(0, len(board[0]) - 1)

# Function to print the board
def print_board():
	print()
	for row in board:
		print('  '.join(row))

# Welcome Texts
print(info['welcome'])
print_board()

print('--- Answer:  ({}, {}) ---'.format(ship_row, ship_col))

# Game Start
sep = '-' * 13 + ' ' * 3
for turn in range(5):
	print('\n\n\n{}Round {}{}\n'.format(sep, turn + 1, sep[::-1]))

	guess_row = int(input('Guess Row: '))
	guess_col = int(input('Guess Col: '))
	print()

	if guess_row == ship_row and guess_col == ship_col:
		print(info['success'])
		break
	else:
		if (guess_row < 0 or guess_row > 4) or (guess_col < 0 or guess_col > 4):
			print(info['overflow'])
		elif board[guess_row][guess_col] == 'X':
			print(info['duplicate'])
		else:
			print(info['missed'])
			board[guess_row][guess_col] = 'X'

		print_board()
