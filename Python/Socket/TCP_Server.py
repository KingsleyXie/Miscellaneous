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

def listener(con, addr):
	# Set username
	con.send(b'Please set your username > ')
	username = con.recv(conf['recv_buff']).decode()
	user_id = ++curr_id

	print(
		'Connected with {}:{}, id: {}, username: {})'
		.format(addr[0], addr[1], user_id, username)
	)
	con.send(
		'Welcome, {}! Your ID is {}'
		.format(username, user_id).encode()
	)

	while True:
		data = con.recv(conf['recv_buff']).decode()
		if data == conf['end_msg']:
			con.send(conf['end_msg'].encode())
			con.close()
			curr_id--

			print(
				'Disconnected with {} ({}:{})'
				.format(username, addr[0], addr[1])
			)

			if curr_id == conf['start_id']:
				print('All clients are terminated, closing socket server...')
				server.close()
				sys.exit(0)

		print('{}: {}'.format(username, data))
		con.send(('I have received your data: ' + data).encode())

def sender():
	# con.send(input().encode())
	pass

while True:
	con, addr = server.accept()
	listen = threading.Thread(target = listener, args = (con, addr))
	listen.start()

# send = threading.Thread(target = sender)
# send.start()
