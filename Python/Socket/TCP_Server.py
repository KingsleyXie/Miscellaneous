import socket
import threading

def listener(con, addr):
	host = addr[0]
	port = addr[1]

	con.send(b'Please set your username > ')
	username = con.recv(1024).decode()
	print(
		'Connected with {} ({}:{})'
		.format(username, host, port)
	)
	con.send(('Welcome, ' + username).encode())

	while True:
		data = con.recv(1024).decode()
		if data == 'exit':
			con.send(b'exit')
			con.close()

			print(
				'Disconnected with {} ({}:{})'
				.format(username, host, port)
			)
			break
		print('{}: {}'.format(username, data))
		con.send(('I have received your data: ' + data).encode())

def sender():
	# con.send(input().encode())
	pass



s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ser = ('localhost', 2333)
print('Starting socket server on {}:{}'.format(*ser))

s.bind(ser)
s.listen(1)

while 1:
	con, addr = s.accept()
	listen = threading.Thread(target = listener, args = (con, addr))
	listen.start()

# send = threading.Thread(target = sender)
# send.start()
