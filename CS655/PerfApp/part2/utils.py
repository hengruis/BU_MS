import matplotlib.pyplot as plt

msgSizes_RTT = [1, 100, 200, 400, 800, 1000]
msgSizes_Tput = [1000, 2000, 4000, 8000, 16000, 32000]

RTT_d0 = [15.1826, 15.2059, 16.4066, 14.8434, 14.9122, 17.6094]  # 0 delay
RTT_d50 = [65.0811, 65.3793, 63.9962, 63.9879, 65.0598, 65.5305]  # 50 delay
RTT_d200 = [214.7977, 214.9483, 213.6235, 216.3523, 217.0600, 216.2840]  # 200 delay
RTT_d1000 = [1016.0182, 1018.3858, 1018.0209, 1018.1428, 1017.9012, 1017.4386]  # 1000 delay
RTT_d0_geni = [95.1093, 93.1031, 94.8847, 93.2593, 95.4018, 94.7968]  # test with geni server

Tput_d0 = [52.4297, 118.0815, 236.7796, 449.9894, 851.3577, 1893.0661]
Tput_d50 = [14.8678, 30.2594, 59.2186, 115.7297, 223.4794, 438.8968]
Tput_d200 = [4.5243, 9.0186, 17.9474, 35.6892, 71.0517, 134.8897]
Tput_d1000 = [0.9508, 1.8898, 3.8162, 7.2755, 14.0382, 27.5994]
Tput_d0_geni = [10.5520, 34.0709, 48.5423, 67.4167, 83.5547, 98.6967]

# plt.figure(figsize=(25, 15), dpi=200)
# plt.plot(msgSizes_RTT, RTT_d0, label='delay = 0ms', c='red', linestyle='-')
# plt.plot(msgSizes_RTT, RTT_d50, label='delay = 50ms', c='yellow', linestyle='--')
# plt.plot(msgSizes_RTT, RTT_d200, label='delay = 200ms', c='green', linestyle='-.')
# plt.plot(msgSizes_RTT, RTT_d1000, label='delay = 1000ms', c='blue', linestyle=':')
# plt.scatter(msgSizes_RTT, RTT_d0, c='red')
# plt.scatter(msgSizes_RTT, RTT_d50, c='yellow')
# plt.scatter(msgSizes_RTT, RTT_d200, c='green')
# plt.scatter(msgSizes_RTT, RTT_d1000, c='blue')
# plt.legend(loc='best')
# plt.grid(True, linestyle='-', alpha=0.5)
# plt.xlabel('Message Size (bytes)')
# plt.ylabel('RTT (ms)')
# plt.title('RTT - Message Size')

plt.figure(figsize=(25, 15), dpi=200)
plt.plot(msgSizes_Tput, Tput_d0, label='delay = 0ms', c='red', linestyle='-')
plt.plot(msgSizes_Tput, Tput_d50, label='delay = 50ms', c='yellow', linestyle='--')
plt.plot(msgSizes_Tput, Tput_d200, label='delay = 200ms', c='green', linestyle='-.')
plt.plot(msgSizes_Tput, Tput_d1000, label='delay = 1000ms', c='blue', linestyle=':')
plt.scatter(msgSizes_Tput, Tput_d0, c='red')
plt.scatter(msgSizes_Tput, Tput_d50, c='yellow')
plt.scatter(msgSizes_Tput, Tput_d200, c='green')
plt.scatter(msgSizes_Tput, Tput_d1000, c='blue')
plt.legend(loc='best')
plt.grid(True, linestyle='-', alpha=0.5)
plt.xlabel('Message Size (bytes)')
plt.ylabel('Throughput (Kbps)')
plt.title('Throughput - Message Size')

plt.show()
