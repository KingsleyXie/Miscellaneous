import re
import socket
import threading

conf = {
	'server_host': 'localhost',
	'server_port': 2333,
	'recv_buff': 1024,
	'start_id': 1000,
	'end_msg': 'bye',
	'quit_msg': 'terminate',
	'sys_msg': '[SYSTEM]'
}

notice = {
	'name': 'Please set your username > ',
	'all_disconnected': '\n[INFO] All clients are currently disconnected\n' +
		'[INFO] Type "' + conf['quit_msg'] + '" to quit\n' +
		'[INFO] Or just wait for new connections\n',
	'empty': '[ERROR] The client list is empty now!',
	'force': '[ERROR] Client list is not empty!'
	# 'force_alert': '[WARNING] Client list is not empty! Disconnect them anyway? [Y/n] '
}

curr_id = conf['start_id']
clients = []

server_conf = (conf['server_host'], conf['server_port'])
print('Starting socket server on {}:{}'.format(*server_conf))

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(server_conf)
server.listen(True)

def listener(client):
	client.send(notice['name'].encode())
	username = client.recv(conf['recv_buff']).decode()

	global curr_id
	curr_id += 1
	user_id = curr_id

	client.send(
		'Welcome, {}! Your ID is {}'
		.format(username, user_id).encode()
	)
	broadcast('{} (ID {}) joined the chat'.format(username, user_id))
	clients.append(client)

	while True:
		data = client.recv(conf['recv_buff']).decode()
		if data != conf['end_msg']:
			broadcast('{}: {}'.format(username, data))
		else:
			# Close current client socket
			client.send(conf['end_msg'].encode())
			clients.remove(client)
			client.close()

			broadcast('{} (ID {}) left the chat'.format(username, user_id))

			if len(clients) == 0:
				print(notice['all_disconnected'])

			# Exit the listener thread
			break

def broadcast(msg):
	print(msg)
	for client in clients:
		client.send(msg.encode())

def accepter():
	try:
		while True:
			client, addr = server.accept()
			listen = threading.Thread(target = listener, args = (client, ))
			listen.start()
	except Exception:
		# Server socket closed on sender thread
		pass

def sender():
	while True:
		data = input()
		if data == conf['quit_msg']:
			if not len(clients):
				global server
				server.close()
				break
			else:
				print(notice['force'])
				# BUG: Disconnect operation will cause recv problem
				# if not re.match(r'n|N', input(notice['force'])):
					# for client in clients:
						# client.send(conf['end_msg'].encode())
						# clients.remove(client)
						# client.close()
					# server.close()
					# break
		else:
			if len(clients):
				broadcast(conf['sys_msg'] + ' ' + data)
			else:
				print(notice['empty'])

ac_trd = threading.Thread(target = accepter)
ac_trd.start()

sd_trd = threading.Thread(target = sender)
sd_trd.start()

# Todo: OOP, [Unit Test]
