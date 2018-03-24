import socket

host = "localhost"
port = 2333

s = socket.socket()
s.connect((host,port))

data = raw_input("Input your data to server->\t")
while data != "exit":
	s.send(data)
	recv = s.recv(1024)

	print "Received data from server: " + str(recv)
	data = raw_input("Input your data to server->\t")
s.close()
