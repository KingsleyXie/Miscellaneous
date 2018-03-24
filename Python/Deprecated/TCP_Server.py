import socket

host = "localhost"
port = 2333

s = socket.socket()
s.bind((host, port))

print "Server is running, waiting for connection."

s.listen(1)
con, addr = s.accept()

print "Connected with: " + str(addr) + "\n"

while 1:
	data = con.recv(1024)
	if data == "exit":
		break

	print "Recieved data from client: " + str(data)
	con.send("I have received your data: " + data)

con.close()
