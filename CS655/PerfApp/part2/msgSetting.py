# connection setup
class CSPMessage:
    # <PROTOCOL PHASE><WS><MEASUREMENT TYPE><WS><NUMBER OF PROBES><WS><MESSAGE SIZE><WS><SERVER DELAY>\n
    def __init__(self, measType, numProbes, msgSize, serverDelay):
        self.phase = 's'
        self.measType = measType  # 'rtt' or 'tput'
        self.numProbes = numProbes
        self.msgSize = msgSize  # size of each probe, in bytes
        self.serverDelay = serverDelay  # in milliseconds
        self.msg = None

    def getMsg(self):
        self.setMsg()
        return self.msg

    def setMsg(self):
        self.msg = f'{self.phase} {self.measType} {self.numProbes} {self.msgSize} {self.serverDelay}\n'

    def set_measType(self, measType):
        self.measType = measType

    def set_numProbes(self, numProbes):
        self.numProbes = numProbes

    def set_msgSize(self, msgSize):
        self.msgSize = msgSize

    def set_serverDelay(self, serverDelay):
        self.serverDelay = serverDelay


# measurement
class MPMessage:
    # <PROTOCOL PHASE><WS><PROBE SEQUENCE NUMBER><WS><PAYLOAD>\n
    def __init__(self, seqNum, payload):
        self.phase = 'm'
        self.seqNum = seqNum  # starting from 1 to numProbes in CSPMessage
        self.payload = payload  # msgSize in CSPMessage
        self.msg = None

    def getMsg(self):
        self.setMsg()
        return self.msg

    def setMsg(self):
        self.msg = f'{self.phase} {self.seqNum} {self.payload}\n'

    def set_seqNum(self, seqNum):
        self.seqNum = seqNum

    def set_payload(self, payload):
        self.payload = payload


# connection termination
class CTPMessage:
    # <PROTOCOL PHASE>\n
    def __init__(self):
        self.phase = 't'
        self.msg = None

    def getMsg(self):
        self.setMsg()
        return self.msg

    def setMsg(self):
        self.msg = f'{self.phase}\n'


# loop over to receive whole message
def fragRecv(soc, size):
    # soc (socket): socket for receiving
    # size (int): size of the fragment to be received
    msg = ''
    while True:
        frag = soc.recv(size).decode()
        msg += frag
        if not frag:
            break
        if frag[-1] == '\n':
            break
    return msg


CSPACK = '200 OK: Ready\n'
CSPERR = '404 ERROR: Invalid Connection Setup Message\n'
MPERR = '404 ERROR: Invalid Measurement Message\n'
CTPACK = '200 OK: Closing Connection\n'
CTPERR = '404 ERROR: Invalid Connection Termination Message\n'
