from random import randint

board = []

for x in range(5):
	board.append(['O'] * 5)

def print_board():
	print()
	for row in board:
		print('  '.join(row))
	print()

print('Let\'s play Battleship!')

ship_row = randint(0, len(board) - 1)
ship_col = randint(0, len(board[0]) - 1)

print('\n-- Answer:  ({0}, {1}) --'.format(ship_row, ship_col))

print('\n--------------  Game Starting!  -----------------\n')

for turn in range(5):
	print('. . . . . . . . . .')
	print('. . . Round {} . . .'.format(turn + 1))
	print('. . . . . . . . . .\n')

	guess_row = int(input('Guess Row: '))
	guess_col = int(input('Guess Col: '))
	print()

	if guess_row == ship_row and guess_col == ship_col:
		print('Congratulations! You sunk my battleship!')
		break
	else:
		if (guess_row < 0 or guess_row > 4) or (guess_col < 0 or guess_col > 4):
			print('Oops, that\'s not even in the ocean.')
		elif board[guess_row][guess_col] == 'X':
			print('You guessed that one already.')
		else:
			print('You missed my battleship!')
			board[guess_row][guess_col] = 'X'

		print_board()
