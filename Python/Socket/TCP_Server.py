import socket
import threading

conf = {
	'server_host': 'localhost',
	'server_port': 2333,
	'recv_buff': 1024,
	'start_id': 1000,
	'end_msg': 'bye'
}

curr_id = conf['start_id']

server_conf = (conf['server_host'], conf['server_port'])
print('Starting socket server on {}:{}'.format(*server_conf))

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(server_conf)
server.listen(True)

clients = []

def listener(con, addr):
	# Set username
	con.send(b'Please set your username > ')
	username = con.recv(conf['recv_buff']).decode()

	global curr_id
	curr_id += 1
	user_id = curr_id

	con.send(
		'Welcome, {}! Your ID is {}'
		.format(username, user_id).encode()
	)
	broadcast('{} (ID {}) joined the chat'.format(username, user_id))
	clients.append(con)

	while True:
		data = con.recv(conf['recv_buff']).decode()
		if data != conf['end_msg']:
			broadcast('{}: {}'.format(username, data))
		else:
			# Close current client socket
			curr_id -= 1
			con.send(conf['end_msg'].encode())
			con.close()

			clients.remove(con)

			broadcast('{} (ID {}) left the chat'.format(username, user_id))

			# Close server socket if all clients are disconnected
			if curr_id == conf['start_id']:
				print('All clients are currently terminated, closing server...')
				global server
				server.close()
				raise SystemExit(0)

			# Exit the listener thread
			break

def broadcast(msg):
	print(msg)
	for client in clients:
		client.send(msg.encode())

try:
	while True:
		con, addr = server.accept()
		listen = threading.Thread(target = listener, args = (con, addr))
		listen.start()
except Exception:
	# Server socket closed on listener thread
	pass
