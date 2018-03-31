import socket
import threading

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ser = ('localhost', 2333)
print('Connecting socket server on {}:{}'.format(*ser))
s.connect(ser)

# Set username
recv = s.recv(1024).decode()
data = input(recv)
s.send(data.encode())

def listener():
	while True:
		recv = s.recv(1024).decode()
		if recv == 'exit':
			break
		else:
			print(recv)

def sender():
	while True:
		data = input()
		s.send(data.encode())
		if data == 'exit':
			break

listen = threading.Thread(target = listener)
listen.start()

send = threading.Thread(target = sender)
send.start()
