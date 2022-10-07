from socket import *
import argparse
import random
import time
from msgSetting import *


# parse command line arguments
def getParams():
    """
        Client: $ python perfClient.py SERVER_IP SERVER_PORT MEASURE PROBE_NUM MSG_SIZE SERVER_DELAY
        E.g. $ python perfClient.py 10.0.0.1 12345 -m rtt -n 10 -s 32 -d 0
    """
    parser = argparse.ArgumentParser(description='TCP Client for performance measurement')
    parser.add_argument('server', type=str, help='server\'s IP address or name')
    parser.add_argument('port', type=int, help='server\'s port number')
    parser.add_argument('-m', type=str, choices=['rtt', 'tput'], help='measurement type, RTT or throughput')
    parser.add_argument('-n', type=int, help='number of probes')
    parser.add_argument('-s', type=int, help='message size in bytes')
    parser.add_argument('-d', type=int, help='server delay in milliseconds')
    args = parser.parse_args()

    return args.server, args.port, args.m, args.n, args.s, args.d


def calcAvgRTT(startTimes, endTimes):
    # startTimes (list): when client sends probe
    # endTimes (list): when client receives response from server
    RTTs = list(map(lambda x, y: (y - x) * 1000, startTimes, endTimes))
    return sum(RTTs) / len(RTTs)  # in milliseconds


def calcAvgTput(startTimes, endTimes, messageSize):
    RTTs = list(map(lambda x, y: (y - x), startTimes, endTimes))
    Tputs = [messageSize / (RTT * 1024) for RTT in RTTs]
    return sum(Tputs) / len(Tputs)  # in Kb/s


if __name__ == '__main__':
    # a list used for generate texts, without space for simplicity
    charList = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()-=_+{}[];,.?'
    starts = []
    ends = []

    # init
    serverIP, serverPort, measType, numProbes, msgSize, serverDelay = getParams()
    clientSocket = socket(AF_INET, SOCK_STREAM)
    clientSocket.connect((serverIP, serverPort))
    print(f'Connecting to {serverIP}:{serverPort}...')

    #############################################################################
    #                           connection setup phase                          #
    #############################################################################
    cspMsg = CSPMessage(measType, numProbes, msgSize, serverDelay)
    print('Client sending: ' + cspMsg.getMsg())
    clientSocket.sendall(cspMsg.getMsg().encode())
    cspResponse = fragRecv(clientSocket, 4096)
    print(f'Server >> {cspResponse}')

    if cspResponse == CSPERR:
        print('Connection closed')
        clientSocket.close()
    elif cspResponse == CSPACK:
        # print('Connection established')

        #########################################################################
        #                           measurement phase                           #
        #########################################################################
        for cnt in range(1, numProbes + 1):  # cnt as sequence number
            text = ''.join(random.choices(charList, k=msgSize))
            starts.append(time.time())
            clientSocket.sendall(MPMessage(cnt, text).getMsg().encode())
            # print(f'Sending: {text}')
            mpResponse = fragRecv(clientSocket, 4096)
            ends.append(time.time())
            print(f'Server >> {mpResponse[:14]}...')
            # print(f'Server >> {mpResponse}')  # comment this line for large message size
            if mpResponse == MPERR:
                print('Connection closed')
                clientSocket.close()
                break

        print('Computing...')
        if measType == 'rtt':
            avgRTT = calcAvgRTT(starts, ends)
            print(f'Average RTT: {avgRTT:.4f} ms')
        elif measType == 'tput':
            avgTput = calcAvgTput(starts, ends, msgSize)
            print(f'Average throughput: {avgTput:.4f} Kb/s')

        #########################################################################
        #                      connection termination phase                     #
        #########################################################################
        clientSocket.sendall(CTPMessage().getMsg().encode())
        ctpResponse = fragRecv(clientSocket, 4096)
        print(f'Server >> {ctpResponse}')
        clientSocket.close()
