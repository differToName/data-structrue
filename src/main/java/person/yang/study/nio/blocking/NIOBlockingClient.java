package person.yang.study.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName NIOBlockingClient
 * @Description: 阻塞式
 * @Author ylyang
 * @Date 2019/7/24 15:14
 **/
public class NIOBlockingClient {
    //客户端
    public static void testClient(){
        SocketChannel sChannel = null;
        ByteBuffer buf = null;
        FileChannel inChannel = null;
        try {
            //1.获取通道
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9899));
            //2.分配缓冲区
            buf = ByteBuffer.allocate(1024);
            //读取本地文件，并发送到服务器
            inChannel = FileChannel.open(Paths.get("D://1.png"), StandardOpenOption.READ);
            while (inChannel.read(buf) !=- 1){
                //记住，切换为读取模式
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

            //②解决线程阻塞（服务器一直在等数据）的方法一：Shutdown the connection for writing without closing the channel.
            sChannel.shutdownOutput();

            //①接收服务端的反馈提示
            int len = 0;
            while ((len = sChannel.read(buf) )!= -1){
                buf.flip();
                System.out.println(new String(buf.array(),0,len));
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //为了方便
            NIOBlockingServer.closed(inChannel);
            NIOBlockingServer.closed(sChannel);
        }
    }


    public static void main(String[] args){
        testClient();
    }
}
