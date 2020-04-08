# Лабораторная 5

```bash
PS C:\Users\mdere\vagrant_box\vnets> vagrant status
Current machine states:

inetRouter                running (virtualbox)
centralRouter             running (virtualbox)
centralServer             running (virtualbox)
office1Router             running (virtualbox)
office1Server             running (virtualbox)
office2Router             running (virtualbox)
office2Server             running (virtualbox)

This environment represents multiple VMs. The VMs are all listed
above with their current state. For more information about a specific
VM, run `vagrant status NAME`.
```

## Задание 1

### Разобрать структуру

* inetRouter

```bash
[root@inetRouter vagrant]# ip r
192.168.255.8/30 via 192.168.255.2 dev eth1
192.168.255.4/30 via 192.168.255.2 dev eth1
192.168.255.0/30 dev eth1  proto kernel  scope link  src 192.168.255.1
192.168.2.0/24 via 192.168.255.2 dev eth1
192.168.1.0/24 via 192.168.255.2 dev eth1
10.0.2.0/24 dev eth0  proto kernel  scope link  src 10.0.2.15
192.168.0.0/24 via 192.168.255.2 dev eth1
169.254.0.0/16 dev eth0  scope link  metric 1002
169.254.0.0/16 dev eth1  scope link  metric 1003
default via 10.0.2.2 dev eth0
```

* centralServer

```bash
[root@centralRouter ~]# ip r
default via 192.168.255.1 dev eth1
10.0.2.0/24 dev eth0 proto kernel scope link src 10.0.2.15 metric 100
192.168.0.0/28 dev eth2 proto kernel scope link src 192.168.0.1 metric 102
192.168.0.32/28 dev eth3 proto kernel scope link src 192.168.0.33
192.168.0.64/26 dev eth4 proto kernel scope link src 192.168.0.65
192.168.1.0/24 via 192.168.255.6 dev eth4
192.168.2.0/24 via 192.168.255.10 dev eth3
192.168.255.0/30 dev eth1 proto kernel scope link src 192.168.255.2 metric 101
192.168.255.4/30 dev eth4 proto kernel scope link src 192.168.255.5 metric 104
192.168.255.8/30 dev eth3 proto kernel scope link src 192.168.255.9 metric 103
```

* office2Router

```bash
[root@office2Router vagrant]# ip r
default via 192.168.255.5 dev eth1
10.0.2.0/24 dev eth0 proto kernel scope link src 10.0.2.15 metric 100
192.168.0.0/24 via 192.168.255.5 dev eth1
192.168.1.0/25 dev eth2 proto kernel scope link src 192.168.1.1 metric 102
192.168.1.128/26 dev eth3 proto kernel scope link src 192.168.1.129 metric 103
192.168.1.192/26 dev eth4 proto kernel scope link src 192.168.1.193 metric 104
192.168.2.0/24 via 192.168.255.5 dev eth1
192.168.255.4/30 dev eth1 proto kernel scope link src 192.168.255.6 metric 101
192.168.255.8/30 via 192.168.255.5 dev eth1
[root@office2Router vagrant]# ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:8a:fe:e6 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 83394sec preferred_lft 83394sec
    inet6 fe80::5054:ff:fe8a:fee6/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:dd:e5:e1 brd ff:ff:ff:ff:ff:ff
    inet 192.168.255.6/30 brd 192.168.255.7 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fedd:e5e1/64 scope link
       valid_lft forever preferred_lft forever
4: eth2: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:1c:a8:56 brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.1/25 brd 192.168.1.127 scope global noprefixroute eth2
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe1c:a856/64 scope link
       valid_lft forever preferred_lft forever
5: eth3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:8a:63:c9 brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.129/26 brd 192.168.1.191 scope global noprefixroute eth3
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe8a:63c9/64 scope link
       valid_lft forever preferred_lft forever
6: eth4: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:89:50:53 brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.193/26 brd 192.168.1.255 scope global noprefixroute eth4
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe89:5053/64 scope link
       valid_lft forever preferred_lft forever
[root@office2Router vagrant]#
```

* office2Server

```bash
[root@office2Server vagrant]# ip r
default via 192.168.1.129 dev eth1
10.0.2.0/24 dev eth0 proto kernel scope link src 10.0.2.15 metric 100
192.168.1.128/26 dev eth1 proto kernel scope link src 192.168.1.130 metric 101
[root@office2Server vagrant]# ping 8.8.8.8
PING 8.8.8.8 (8.8.8.8) 56(84) bytes of data.
64 bytes from 8.8.8.8: icmp_seq=1 ttl=40 time=31.3 ms
64 bytes from 8.8.8.8: icmp_seq=2 ttl=40 time=27.7 ms
64 bytes from 8.8.8.8: icmp_seq=3 ttl=40 time=31.7 ms
64 bytes from 8.8.8.8: icmp_seq=4 ttl=40 time=32.0 ms
64 bytes from 8.8.8.8: icmp_seq=5 ttl=40 time=50.0 ms
64 bytes from 8.8.8.8: icmp_seq=6 ttl=40 time=29.2 ms
64 bytes from 8.8.8.8: icmp_seq=7 ttl=40 time=59.1 ms
64 bytes from 8.8.8.8: icmp_seq=8 ttl=40 time=38.3 ms
64 bytes from 8.8.8.8: icmp_seq=9 ttl=40 time=41.7 ms
64 bytes from 8.8.8.8: icmp_seq=10 ttl=40 time=59.3 ms
^C
--- 8.8.8.8 ping statistics ---
10 packets transmitted, 10 received, 0% packet loss, time 9045ms
rtt min/avg/max/mdev = 27.708/40.074/59.393/11.504 ms
[root@office2Server vagrant]# tracepath 8.8.8.8 -n
 1?: [LOCALHOST]                                         pmtu 1500
 1:  192.168.1.129                                         0.304ms
 1:  192.168.1.129                                         0.298ms
 2:  192.168.255.5                                         0.431ms
 3:  192.168.255.1                                         0.467ms
[root@office2Server vagrant]# ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:8a:fe:e6 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 83576sec preferred_lft 83576sec
    inet6 fe80::5054:ff:fe8a:fee6/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:1f:2b:51 brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.130/26 brd 192.168.1.191 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe1f:2b51/64 scope link
       valid_lft forever preferred_lft forever
4: eth2: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:e2:0e:9f brd ff:ff:ff:ff:ff:ff
5: eth3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:46:fb:c0 brd ff:ff:ff:ff:ff:ff
6: eth4: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:bd:ea:d5 brd ff:ff:ff:ff:ff:ff
[root@office2Server vagrant]# ping 192.168.2.65
PING 192.168.2.65 (192.168.2.65) 56(84) bytes of data.
64 bytes from 192.168.2.65: icmp_seq=1 ttl=62 time=1.01 ms
64 bytes from 192.168.2.65: icmp_seq=2 ttl=62 time=0.960 ms
^C
--- 192.168.2.65 ping statistics ---
2 packets transmitted, 2 received, 0% packet loss, time 1002ms
rtt min/avg/max/mdev = 0.960/0.986/1.013/0.041 ms
[root@office2Server vagrant]# tracepath 192.168.2.65
 1?: [LOCALHOST]                                         pmtu 1500
 1:  gateway                                               0.309ms
 1:  gateway                                               0.387ms
 2:  192.168.255.5                                         0.905ms
 3:  192.168.2.65                                          0.923ms reached
     Resume: pmtu 1500 hops 3 back 3
```

### Нарисовать схему сети

### Расписать табличку подсетей

## Задание 2

### Найти свободные подсети

### Подсчитать, сколько узлов в каждой подсети, включая свободные

### Указать broadcast-адрес для каждой подсети

### Проверить, нет ли ошибок при разбиении

## Задание 3

### Все серверы и роутеры должны ходить в Интернет черз inetRouter

### Все серверы должны видеть друг друга

### У всех новых серверов отключить дефолт на NAT (eth0), который Vagrant поднимает для связи

## Задание 4

### поднять nginx на centralServer1

* office1Server

```bash
[root@office1Server vagrant]# systemctl start nginx
```

* office2Server

```bash
[root@office2Server vagrant]# nmap -sP 192.168.2.66

Starting Nmap 6.40 ( http://nmap.org ) at 2020-04-08 17:15 UTC
Nmap scan report for 192.168.2.66
Host is up (0.0012s latency).
Nmap done: 1 IP address (1 host up) scanned in 0.01 seconds
[root@office2Server vagrant]# curl 192.168.2.66
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
...
```

* centralRouter

```bash
[root@centralRouter ~]# ip -c a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 52:54:00:8a:fe:e6 brd ff:ff:ff:ff:ff:ff
    inet 10.0.2.15/24 brd 10.0.2.255 scope global noprefixroute dynamic eth0
       valid_lft 81518sec preferred_lft 81518sec
    inet6 fe80::5054:ff:fe8a:fee6/64 scope link
       valid_lft forever preferred_lft forever
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:c0:80:14 brd ff:ff:ff:ff:ff:ff
    inet 192.168.255.2/30 brd 192.168.255.3 scope global noprefixroute eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fec0:8014/64 scope link
       valid_lft forever preferred_lft forever
4: eth2: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:67:cb:29 brd ff:ff:ff:ff:ff:ff
    inet 192.168.0.1/28 brd 192.168.0.15 scope global noprefixroute eth2
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe67:cb29/64 scope link
       valid_lft forever preferred_lft forever
5: eth3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:99:2c:c3 brd ff:ff:ff:ff:ff:ff
    inet 192.168.255.9/30 brd 192.168.255.11 scope global noprefixroute eth3
       valid_lft forever preferred_lft forever
    inet 192.168.0.33/28 scope global eth3
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe99:2cc3/64 scope link
       valid_lft forever preferred_lft forever
6: eth4: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 08:00:27:ff:d4:fe brd ff:ff:ff:ff:ff:ff
    inet 192.168.255.5/30 brd 192.168.255.7 scope global noprefixroute eth4
       valid_lft forever preferred_lft forever
    inet 192.168.0.65/26 scope global eth4
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:feff:d4fe/64 scope link
       valid_lft forever preferred_lft forever
[root@centralRouter ~]# tcpdump -i eth4 port 80
tcpdump: verbose output suppressed, use -v or -vv for full protocol decode
listening on eth4, link-type EN10MB (Ethernet), capture size 262144 bytes
```

* officeServer2

```bash
[root@office2Server vagrant]# curl 192.168.2.66
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
...
```

* centralRouter

```bash
17:28:52.035041 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [S], seq 4230837690, win 29200, options [mss 1460,sackOK,TS val 4490493 ecr 0,nop,wscale 5], length 0
17:28:52.035643 IP 192.168.2.66.http > 192.168.1.130.56624: Flags [S.], seq 3937494362, ack 4230837691, win 28960, options [mss 1460,sackOK,TS val 4571056 ecr 4490493,nop,wscale 5], length 0
17:28:52.036068 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [.], ack 1, win 913, options [nop,nop,TS val 4490494 ecr 4571056], length 0
17:28:52.036125 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [P.], seq 1:77, ack 1, win 913, options [nop,nop,TS val 4490494 ecr 4571056], length 76: HTTP: GET / HTTP/1.1
17:28:52.036475 IP 192.168.2.66.http > 192.168.1.130.56624: Flags [.], ack 77, win 905, options [nop,nop,TS val 4571057 ecr 4490494], length 0
17:28:52.036714 IP 192.168.2.66.http > 192.168.1.130.56624: Flags [.], seq 1:4345, ack 77, win 905, options [nop,nop,TS val 4571057 ecr 4490494], length 4344: HTTP: HTTP/1.1 200 OK
17:28:52.036943 IP 192.168.2.66.http > 192.168.1.130.56624: Flags [P.], seq 4345:5074, ack 77, win 905, options [nop,nop,TS val 4571057 ecr 4490494], length 729: HTTP
17:28:52.037301 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [.], ack 4345, win 1184, options [nop,nop,TS val 4490495 ecr 4571057], length 0
17:28:52.037477 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [.], ack 5074, win 1275, options [nop,nop,TS val 4490496 ecr 4571057], length 0
17:28:52.037648 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [F.], seq 77, ack 5074, win 1275, options [nop,nop,TS val 4490496 ecr 4571057], length 0
17:28:52.038022 IP 192.168.2.66.http > 192.168.1.130.56624: Flags [F.], seq 5074, ack 78, win 905, options [nop,nop,TS val 4571059 ecr 4490496], length 0
17:28:52.038340 IP 192.168.1.130.56624 > 192.168.2.66.http: Flags [.], ack 5075, win 1275, options [nop,nop,TS val 4490496 ecr 4571059], length 0
```

* centralRouter

```bash
[root@centralRouter ~]# iptables -L
Chain INPUT (policy ACCEPT)
target     prot opt source               destination

Chain FORWARD (policy ACCEPT)
target     prot opt source               destination

Chain OUTPUT (policy ACCEPT)
target     prot opt source               destination
[root@centralRouter ~]# iptables -A INPUT -s 192.168.1.130 -p tcp --dport 80 -j REJECT
[root@centralRouter ~]# iptables -L -n -v
Chain INPUT (policy ACCEPT 35 packets, 2122 bytes)
 pkts bytes target     prot opt in     out     source               destination
    0     0 REJECT     tcp  --  *      *       192.168.1.130         0.0.0.0/0            tcp dpt:80 reject-with icmp-port-unreachable

Chain FORWARD (policy ACCEPT 49 packets, 23352 bytes)
 pkts bytes target     prot opt in     out     source               destination

Chain OUTPUT (policy ACCEPT 20 packets, 2395 bytes)
 pkts bytes target     prot opt in     out     source               destination
[root@centralRouter ~]# iptables -A FORWARD -s 192.168.1.130 -p tcp --dport 80 -j REJECT
[root@centralRouter ~]# iptables -L -n -v
Chain INPUT (policy ACCEPT 9 packets, 528 bytes)
 pkts bytes target     prot opt in     out     source               destination

Chain FORWARD (policy ACCEPT 22 packets, 11474 bytes)
 pkts bytes target     prot opt in     out     source               destination
    0     0 REJECT     tcp  --  *      *       192.168.1.130         0.0.0.0/0            tcp dpt:80 reject-with icmp-port-unreachable

Chain OUTPUT (policy ACCEPT 5 packets, 852 bytes)
 pkts bytes target     prot opt in     out     source               destination
[root@centralRouter ~]# iptables -L -n -v
Chain INPUT (policy ACCEPT 13 packets, 768 bytes)
 pkts bytes target     prot opt in     out     source               destination

Chain FORWARD (policy ACCEPT 34 packets, 17311 bytes)
 pkts bytes target     prot opt in     out     source               destination
    0     0 REJECT     tcp  --  *      *       192.168.1.130         0.0.0.0/0            tcp dpt:80 reject-with icmp-port-unreachable

Chain OUTPUT (policy ACCEPT 8 packets, 1712 bytes)
 pkts bytes target     prot opt in     out     source               destination
[root@centralRouter ~]# iptables -D FORWARD -s 192.168.1.130 -p tcp --dport 80 -j REJECT
[root@centralRouter ~]# iptables -A FORWARD -s 192.168.1.130 -p tcp --dport 80 -j DROP

```

* office2Server

```bash
[root@office2Server vagrant]# nmap -Pn 192.168.2.66

Starting Nmap 6.40 ( http://nmap.org ) at 2020-04-08 17:49 UTC
Nmap scan report for 192.168.2.66
Host is up (0.00094s latency).
Not shown: 997 closed ports
PORT    STATE    SERVICE
22/tcp  open     ssh
80/tcp  filtered http
111/tcp open     rpcbind

Nmap done: 1 IP address (1 host up) scanned in 1.26 seconds
```

* Forward localhost:8080 -> vagrant://192.168.2.66:80 nginx

```bash
iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 80 -j DNAT --to-destination 192.168.2.66:80
iptables -t nat -I POSTROUTING --dst 192.168.2.66 -p tcp --dport 80 -j SNAT --to-source 192.168.255.1
```
