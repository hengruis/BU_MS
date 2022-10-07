from socket import *
import argparse
# import threading
import _thread

"""
    Server: $ python perfServer.py SERVER_PORT
"""
parser = argparse.ArgumentParser(description='Server for TCP socket')
parser.add_argument('port', type=int, help='server\'s port number')
args = parser.parse_args()
serverPort = args.port


# based on K&R textbook

# server responds to each client
def handleClient(clientSocket, clientAddr):
    while True:
        recvMessage = clientSocket.recv(2048).decode()
        # if message from client is empty, close the connection
        if not recvMessage:
            print(f'Connection from {clientAddr} closed')
            break
        print(f'Client {clientAddr} >> {recvMessage}')
        # return exactly the same message
        clientSocket.sendall(recvMessage.encode())
    clientSocket.close()


print('Server setting up...')
with socket(AF_INET, SOCK_STREAM) as serverSocket:
    serverSocket.bind(('', serverPort))
    serverSocket.listen()
    print('Server ready')

    # server keeps running unless closed manually
    # Note: Ctrl+C not working on local machine, but works on csa
    try:
        while True:
            connectionSocket, addr = serverSocket.accept()
            print(f'Connected by {addr}')
            # open a thread for each client
            _thread.start_new_thread(handleClient, (connectionSocket, addr))
    except KeyboardInterrupt:
        serverSocket.close()
        print('Server closed')
