package main.java.org.p2p.多播;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

//发送端程序

/**
 * 　”组播“可以比方为：你对着大街喊：”是男人的来一下，一人发一百块”，那么男的过来，女就不会过来,因为没有钱发她不理你(组播：其中所有的男生就是一个组)，换句话说: 组播是一台主机向指定的一组主机发送数据报包，因为如果采用单播方式，逐个节点传输，有多少个目标节点，就会有多少次传送过程，这种方式显然效率极低，是不可取的；如果采用不区分目标、全部发送的广播方式，虽然一次可以传送完数据，但是显然达不到区分特定数据接收对象的目的，又会占用网络带宽。采用组播方式，既可以实现一次传送所
 *
 * 有目标节点的数据，也可以达到只对特定对象传送数据的目的。
 *
 * IP网络的组播一般通过组播IP地址来实现。组播IP地址就是D类IP地址，即224.0.0.0至239.255.255.255之间的IP地址。
 *
 * 1.组播的优点：
 *
 * (1)具备广播所具备的所有优点；
 *
 * (2)与单播相比，提供了发送数据报包的效率，与广播相比，减少了网络流量；
 *
 * 2.组播的缺点：
 *
 * (1)与单播协议相比没有纠错机制，发生丢包错包后难以弥补，但可以通过一定的容错机制和QOS加以弥补；
 */
public class SendUdp
{
    public static void main(String[] args) throws IOException
    {
        MulticastSocket ms=null;
        DatagramPacket dataPacket = null;
        ms = new MulticastSocket();
        ms.setTimeToLive(32);  
        //将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的  
         byte[] data = "组播 测试".getBytes();   
         InetAddress address = InetAddress.getByName("239.0.0.255");
         dataPacket = new DatagramPacket(data, data.length, address,8899);  
         ms.send(dataPacket);  
         ms.close();   
    }
}