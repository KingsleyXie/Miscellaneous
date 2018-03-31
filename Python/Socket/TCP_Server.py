import socket
import threading

def listener(addr):
		print('Connected with {}:{}'.format(addr[0], addr[1]))
		while True:
			data = con.recv(1024).decode()
			if data == 'exit':
				con.close()
				break
			print('Message from client {}:{}\t{}'.format(addr[0], addr[1], data))
			con.send(('I have received your data:' + data).encode())

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
	listen = threading.Thread(target = listener, args = (addr, ))
	listen.start()

# send = threading.Thread(target = sender)
# send.start()
