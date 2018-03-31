import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ser = ('localhost', 2333)
print('Connecting socket server on {}:{}'.format(*ser))
s.connect(ser)

while True:
	data = input('Input your data to server->\t')
	s.send(data.encode())
	if data == 'exit':
		break
	recv = s.recv(1024).decode()

	print('Received data from server:', recv)
