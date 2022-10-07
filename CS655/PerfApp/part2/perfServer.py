from socket import *
import argparse
# import threading
import _thread
import time
from msgSetting import *


# parse command line arguments
def getParams():
    """
        Server: $ python perfServer.py SERVER_PORT
        E.g. $ python perfServer.py 12345
    """
    parser = argparse.ArgumentParser(description='TCP Server for performance measurement')
    parser.add_argument('-p', type=int, help='server\'s port number')
    args = parser.parse_args()

    return args.p


def sendERR(soc, address, phase):
    if phase == 'csp':
        soc.sendall(CSPERR.encode())
    elif phase == 'mp':
        soc.sendall(MPERR.encode())
    elif phase == 'ctp':
        soc.sendall(CTPERR.encode())
    print(f'Connection from {address} closed')
    soc.close()


# implement server CSP
def serverCSP(soc, clientAddr):
    message = fragRecv(soc, 4096)
    try:
        # parse message
        msg = message.split(' ')
        if msg[0] == 's':
            try:
                measType = msg[1]
                numProbes = int(msg[2])
                msgSize = int(msg[3])
                serverDelay = int(msg[4][:-1])  # remove '\n'
                soc.sendall(CSPACK.encode())
                serverMP(soc, clientAddr, numProbes, serverDelay)
            except:
                sendERR(soc, clientAddr, 'csp')
        else:
            sendERR(soc, clientAddr, 'csp')
    except:
        sendERR(soc, clientAddr,'csp')


# implement server MP
def serverMP(soc, clientAddr, num, delay):
    """
        num (int): number of probes
        size (int): message size in bytes
        delay (int): server delay in milliseconds
    """
    for cnt in range(1, num + 1):  # set counter
        message = fragRecv(soc, 4096)
        try:
            # parse message
            msg = message.split(' ')
            if msg[0] == 'm':
                seqNum = int(msg[1])
                if seqNum == cnt and seqNum <= num:
                    # payload = msg[2]
                    time.sleep(delay / 1000)  # server delay
                    soc.sendall(message.encode())
                else:
                    sendERR(soc, clientAddr, 'mp')
                    break
            else:
                sendERR(soc, clientAddr, 'mp')
                break
        except:
            sendERR(soc, clientAddr, 'mp')
            break
    serverCTP(soc, clientAddr)


# implement server CTP
def serverCTP(soc, clientAddr):
    message = fragRecv(soc, 4096)
    if message == 't\n':
        soc.sendall(CTPACK.encode())
        print(f'Connection from {clientAddr} closed')
        soc.close()
    else:
        sendERR(soc, clientAddr, 'ctp')


def handleClient(connectionSocket, clientAddr):
    serverCSP(connectionSocket, clientAddr)


if __name__ == '__main__':
    # init
    print('Server starting...')
    serverPort = getParams()
    serverSocket = socket(AF_INET, SOCK_STREAM)
    serverSocket.bind(('', serverPort))
    serverSocket.listen()
    print(f'Server ready on port {serverPort}')

    try:
        while True:
            connSocket, addr = serverSocket.accept()
            print(f'Connected by {addr}')
            _thread.start_new_thread(handleClient, (connSocket, addr))
    except KeyboardInterrupt:
        print('Server closed')
        serverSocket.close()
