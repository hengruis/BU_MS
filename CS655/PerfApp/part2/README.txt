## README ##

Team: Hengrui Sh, Jiazheng Xiong

- ENVIRONMENT
    Windows 10 Home 21H2 19044.2075
    PyCharm 2022.2.2 Professional Edition
    Python 3.8.13

- Run
    To run the server:
    > python perfServer.py -p PORT
        @PORT: server port number

    To run the client
    > python perfClient.py HOST PORT -m MEASURE_TYPE -n NUM_PROBES -s MSG_SIZE -d DELAY
        @HOST: server hostname or IP address
        @PORT: server port number
        @MEASURE_TYPE: 'rtt' or 'tput'
        @NUM_PROBES: number of probes
        @MSG_SIZE: message size (bytes)
        @DELAY: delay between probes (miliseconds)
    !Note: If RTT is too small, there is probably a divided-by-zero exception. Please try again.
    
    To draw the graphs
    > python utils.py
    !Note: The plots do not show up neither in IDE nor in terminal, but they do appear in notebook without modifying the code.
