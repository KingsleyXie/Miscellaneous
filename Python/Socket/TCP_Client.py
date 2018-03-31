import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ser = ('localhost', 2333)
print('Connecting socket server on {}:{}'.format(*ser))
s.connect(ser)

# Set username
recv = s.recv(1024).decode()
data = input(recv)
s.send(data.encode())

while True:
	recv = s.recv(1024).decode()
	print(recv)

	data = input('Input your data to server -> ')
	s.send(data.encode())
	if data == 'exit':
		break
