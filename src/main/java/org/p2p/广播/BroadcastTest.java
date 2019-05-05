package main.java.org.p2p.广播;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * “广播”可以比方为：一个人通过广播喇叭对在场的全体说话(他才不管你是否乐意听)。换句话说: 广播是一台主机对某一个网络上的所有主机发送数据报包。这个网络可能是网络，也可能是子网，还有可能是所有子网。
 *
 * 广播有两类：本地广播和定向广播
 *
 * 定向广播：将数据报包发送到本网络之外的特定网络的所有主机，然而，由于互联网上的大部分路由器都不转发定向广播消息，所以这里不深入介绍了
 * 本地广播：将数据报包发送到本地网络的所有主机，IPv4的本地广播地址为“255.255.255.255”，路由器不会转发此广播；
 * 1.广播的优点：
 *
 * (1)通信的效率高，信息一下子就可以传递到某一个网络上的所有主机。
 *
 * (2)由于服务器不用向每个客户端单独发送数据，所以服务器流量比较负载低；
 *
 * 2.广播的缺点：
 *
 * (1)非常占用网络的带宽；
 *
 * (2)缺乏针对性,也不管主机是否真的需要接收该数据, 就强制的接收数据；
 *
 * 3.应用场景：
 *
 * (1)有线电视就是典型的广播型网络
 */

//发送端程序
public class BroadcastTest
{
    public static void main(String[] args)
    {
        //广播的实现 :由客户端发出广播，服务器端接收
        String host = "255.255.255.255";//广播地址
        int port = 9999;//广播的目的端口
        String message = "test";//用于发送的字符串
        try
        {
            InetAddress adds = InetAddress.getByName(host);
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(message.getBytes(),message.length(), adds, port);
            ds.send(dp);
            ds.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

