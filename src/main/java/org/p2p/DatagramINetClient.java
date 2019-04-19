package main.java.org.p2p;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * UDP P2P测试客户机：发送消息，接收服务器发来的消息
 * 
 * @author yujing
 * 
 */
public class DatagramINetClient extends Thread {
	// 公网服务器地址
	private SocketAddress destAdd = new InetSocketAddress(" 192.168.1.121",
			10000);
	private DatagramSocket sendSocket;// 发送Socket对象
	// 显示接收到消息的组件
	private JTextArea jta_recive = new JTextArea(10, 25);
	private JComboBox jcb_addList = new JComboBox();// 其他客户机的地址显示

	public DatagramINetClient() {
		try {
			sendSocket = new DatagramSocket();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] recvData = new byte[1024];
				// 创建接收数据包对象
				DatagramPacket recvPacket = new DatagramPacket(recvData,
						recvData.length);
				System.out.println("等待接收数据到来。。。");
				sendSocket.receive(recvPacket);
				byte[] data = recvPacket.getData();
				System.out.println("收到数据：" + new String(data).trim());
				// 读到信息
				ByteArrayInputStream bins = new ByteArrayInputStream(data);
				ObjectInputStream oins = new ObjectInputStream(bins);
				Object dataO = oins.readObject();
				if (dataO instanceof Set) {// 服务器端的地址列表
					Set<InetSocketAddress> othersAdds = (Set<InetSocketAddress>) dataO;
					jcb_addList.removeAllItems();
					// 将收到的地址列表加入到界面下拉框中
					for (InetSocketAddress it : othersAdds) {
						jcb_addList.addItem(it);

					}

				} else if (dataO instanceof String) {
					String s = (String) dataO;
					// 显示你到界面
					jta_recive.append(s + "\r\n");
				} else {
					String s = "unknown msg:" + dataO;
					jta_recive.append(s + "\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendP2PMsg(String msg, InetSocketAddress dest) {
		try {
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bous);
			oos.writeObject(msg);
			oos.flush();
			byte[] data = bous.toByteArray();
			DatagramPacket dp = new DatagramPacket(data, data.length, dest);
			sendSocket.send(dp);
			System.out.println("已发送一条点对点消息to：" + dest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendRequestMsg(String msg) {
		try {
			byte[] buffer = msg.getBytes();
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length,
					destAdd);
			sendSocket.send(dp);
			System.out.println("已发送给服务器：" + msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 显示主界面
	public void setUpUI() {
		JFrame frame = new JFrame();
		frame.setTitle("P2P测试——客户端");
		frame.setLayout(new FlowLayout());
		frame.setSize(300, 300);
		JButton button = new JButton("获取其他客户及地址");
		frame.add(button);
		frame.add(jcb_addList);
		// 发送请求给服务器端的其他客户及列表信息
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendRequestMsg("取得地址");

			}
		});
		// 用户名密码标签
		JLabel la_name = new JLabel("接收到的消息");
		JLabel la_users = new JLabel("发送给：");
		final JTextField jtf_send = new JTextField(20);// 发送输入框
		JButton bu_send = new JButton("Send");
		frame.add(la_name);
		frame.add(jta_recive);
		frame.add(la_users);
		frame.add(jtf_send);
		frame.add(bu_send);
		// 发送事件监听
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = jtf_send.getText();
				// 得到选中的目标地址
				InetSocketAddress dest = (InetSocketAddress) jcb_addList
						.getSelectedItem();
				sendP2PMsg(msg, dest);
				jtf_send.setText("");

			}
		};
		bu_send.addActionListener(al);
		jtf_send.addActionListener(al);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

	}

	// 主函数
	public static void main(String[] args) {
		DatagramINetClient sender = new DatagramINetClient();
		sender.start();
		sender.setUpUI();
	}

}
