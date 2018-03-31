import socket
import threading

conf = {
	'server_host': 'localhost',
	'server_port': 2333,
	'recv_buff': 1024,
	'end_msg': 'bye'
}

server_conf = (conf['server_host'], conf['server_port'])
print('Connecting socket server on {}:{}'.format(*server_conf))

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(server_conf)

# Set username
recv = client.recv(conf['recv_buff']).decode()
data = input(recv)
client.send(data.encode())

def listener():
	while True:
		recv = client.recv(conf['recv_buff']).decode()
		if recv == conf['end_msg']:
			break
		else:
			print(recv)

def sender():
	while True:
		data = input()
		client.send(data.encode())
		if data == conf['end_msg']:
			break

listen = threading.Thread(target = listener)
listen.start()

send = threading.Thread(target = sender)
send.start()
