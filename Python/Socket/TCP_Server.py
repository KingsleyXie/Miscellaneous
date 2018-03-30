import socket
import threading

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ser = ('localhost', 2333)
print('Starting socket server on {}:{}'.format(*ser))

s.bind(ser)

def listener():
	s.listen(1)

	while 1:
		con, addr = s.accept()
		try:
			print('Connected with', addr)
			while True:
				data = con.recv(1024).decode()
				if data == 'exit':
					break
				print('Received', data)
				con.send(('I have received your data:' + data).encode())
		finally:
			con.close()

def sender():
	# con.send(input().encode())
	pass

listen = threading.Thread(target = listener)
send = threading.Thread(target = sender)

listen.start()
send.start()
