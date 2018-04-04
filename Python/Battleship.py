import re
from random import randint

# Information texts
info = {
	'welcome': 'Let\'s play Battleship!',
	'customize': 'Begin with default values? [Y/n] ',
	'success': 'Congratulations! You sunk my battleship!',
	'duplicate': 'You guessed that one already.',
	'missed': 'You missed my battleship!',
	'alert_int': 'Please input an integer'
}

# Configuration
conf = {
	'rows': 7, # Board row number
	'cols': 7, # Board column number
	'rounds': 5, # Round number

	'init': 'O', # Board initial code
	'guessed': 'X', # Board guessed code
	'sep': '  ', # Board code separator
	'ans': False, # Display answer
}

# Function to receive integer
def int_input(msg, lmt_max =0xff, lmt_min =0):
	passed = False
	while not passed:
		try:
			num = int(input(msg))
			if num < lmt_min or num > lmt_max:
				print(
					'The input value should between {} and {}'
					.format(lmt_min, lmt_max)
				)
			else:
				passed = True
		except ValueError as e:
			print(info['alert_int'])
	return num

# Welcome texts
print(info['welcome'])

# Simplified customize option
if re.match(r'n|N', input(info['customize'])):
	conf['rows'] = int_input('Set Rows: ')
	conf['cols'] = int_input('Set Cols: ')
	conf['rounds'] = int_input(
		'Set Rounds: ',
		conf['rows'] * conf['cols'] - 1, 1
	)
	print()

# Initialize the board
board = []
for x in range(conf['rows']):
	board.append([conf['init']] * conf['cols'])

# Generate random ship position
ship_row = randint(0, len(board) - 1)
ship_col = randint(0, len(board[0]) - 1)

# Function to print the board
def print_board():
	print()
	for row in board:
		print(conf['sep'].join(row))



print_board()

# Display answer
if conf['ans']:
	# Center the answer
	ans_str = ' Answer:  ({}, {}) '.format(ship_row, ship_col)
	sep_len = len(conf['sep'])
	ans_width = (sep_len + 1) * conf['cols'] - sep_len
	print(ans_str.center(ans_width, '*'))

# Game start
sep = '-' * 13 + ' ' * 3
for turn in range(conf['rounds']):
	print('\n\n{}Round {}/{}{}\n'.format(
		sep, turn + 1, conf['rounds'], sep[::-1])
	)

	guess_row = int_input('Guess Row: ', conf['rows'])
	guess_col = int_input('Guess Col: ', conf['cols'])
	print()

	if guess_row == ship_row and guess_col == ship_col:
		print(info['success'])
		break
	else:
		if board[guess_row][guess_col] == conf['guessed']:
			print(info['duplicate'])
		else:
			print(info['missed'])
			board[guess_row][guess_col] = conf['guessed']

		print_board()
