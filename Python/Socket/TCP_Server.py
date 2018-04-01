import socket
import threading

conf = {
	'server_host': 'localhost',
	'server_port': 2333,
	'recv_buff': 1024,
	'start_id': 1000,
	'end_msg': 'bye',
	'quit_msg': 'terminate',
	'sys_msg': '[system]'
}

curr_id = conf['start_id']
clients = []

server_conf = (conf['server_host'], conf['server_port'])
print('Starting socket server on {}:{}'.format(*server_conf))

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(server_conf)
server.listen(True)

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
			con.send(conf['end_msg'].encode())

			clients.remove(con)
			con.close()

			broadcast('{} (ID {}) left the chat'.format(username, user_id))

			if len(clients) == 0:
				print(
					'\n[info] All clients are currently disconnected\n' +
					'[info] Type "' + conf['quit_msg'] + '" to quit\n' +
					'[info] Or just wait for new connections\n'
				)

			# Exit the listener thread
			break

def broadcast(msg):
	print(msg)
	for client in clients:
		client.send(msg.encode())

def accepter():
	try:
		while True:
			con, addr = server.accept()
			listen = threading.Thread(target = listener, args = (con, addr))
			listen.start()
	except Exception:
		# Server socket closed on sender thread
		pass

def sender():
	while True:
		data = input()
		if data == conf['quit_msg']:
			if len(clients) == 0:
				global server
				server.close()
				break
			else:
				print('Client list is not empty now!')
		broadcast(conf['sys_msg'] + ' ' + data)

ac_trd = threading.Thread(target = accepter)
ac_trd.start()

sd_trd = threading.Thread(target = sender)
sd_trd.start()

# Todo: Force Terminate, OOP, [Unit Test]
