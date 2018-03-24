import socket

host = "localhost"
port = 2333

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind((host,port))

print "Server is running, waiting for connection."

while 1:
	data,addr = s.recvfrom(1024)
	print len(data)
	if data == "exit":
		break
	print "Recieved data from client: " + str(data)
	print "Send data:\n\t" + "I have received your data: " + str(data)
	s.sendto("I have received your data: " + data, addr)
s.close()
