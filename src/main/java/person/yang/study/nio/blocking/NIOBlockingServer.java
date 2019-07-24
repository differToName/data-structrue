package person.yang.study.nio.blocking;

/**
 * @ClassName NIOBlockingServer
 * @Description: NIO 阻塞式
 * @Author ylyang
 * @Date 2019/7/24 11:20
 **/

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1. 使用NIO完成网络通信的三个核心
 *  - 通道(Channel):负责连接
 *      |-- SelectableChannel
 *          |-- SocketChannel
 *          |-- ServerSocketChannel
 *          |-- DatagramChannel
 *
 *          |-- Pipe.SinkChannel
 *          |-- Pipe.SourceChannel
 *
 *  - 缓冲区(Buffer):负责数据装载
 *
 *  - 选择器(Selector):是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 */
public class NIOBlockingServer {
    //服务端
    public static void testServer(){
        ServerSocketChannel ssChannel = null;
        FileChannel outChannel = null;
        SocketChannel acceptChannel = null;
        try {
            //1.获取通道
            ssChannel = ServerSocketChannel.open();
            //2.绑定连接,
            ssChannel.bind(new InetSocketAddress(9899));
            //3.获取客户端连接的通道
            acceptChannel = ssChannel.accept();
            //4.接收客户端传的数据，并保存到本地
            outChannel = FileChannel.open(Paths.get("D://2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
            ByteBuffer readBuf = ByteBuffer.allocate(1024);
            System.out.println("服务端开始读取客户端传数据");
            //此处可能会导致线程一直被占用，因为在等客户端数据
            //可解决方法：1.客户端调用shutdownOutput 2.使拥选择器
            while(acceptChannel.read(readBuf) != -1){
                //切换为读取模式
                readBuf.flip();
                outChannel.write(readBuf);
                readBuf.clear();
            }


            //①增加给客户端响应
            readBuf.put("服务端接收数据成功".getBytes());
            //切换为读模式
            readBuf.flip();
            acceptChannel.write(readBuf);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //记得关闭
            closed(outChannel);
            closed(acceptChannel);
            closed(ssChannel);
        }
    }

    public static void closed(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        testServer();
    }

}
