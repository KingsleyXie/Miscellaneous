import socket

host = "localhost"
server_port = 2333
port = 6666

server = (host, server_port)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind((host, port))

transport = raw_input("Input your data to server->\t")
while 1:
	s.sendto(transport, server)
	if transport == "exit":
		break
	data,addr = s.recvfrom(1024)
	print "Received data from server: " + str(data)
	transport = raw_input("Input your data to server->\t")
s.close()
