from socket import *
import argparse

"""
    Client: $ python perfClient.py SERVER_IP SERVER_PORT
"""
parser = argparse.ArgumentParser(description='Client for TCP socket')
parser.add_argument('server', type=str, default='128.197.11.40', help='server\'s IP address')
parser.add_argument('port', type=int, help='server\'s port number')
args = parser.parse_args()

# based on K&R textbook
serverIP = args.server
serverPort = args.port
with socket(AF_INET, SOCK_STREAM) as clientSocket:
    clientSocket.connect((serverIP, serverPort))
    while True:
        message = input('Input your message: ')
        clientSocket.sendall(message.encode())
        # if input nothing close the client
        if message == '':
            print('Client closed')
            break
        recvMessage = clientSocket.recv(2048).decode()
        print(f'Server >> {recvMessage}')
