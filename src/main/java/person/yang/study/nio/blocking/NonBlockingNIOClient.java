package person.yang.study.nio.blocking;

/**
 * @ClassName NonBlockingNIOClient
 * @Description: 非阻塞式网络，选择器
 * @Author ylyang
 * @Date 2019/7/24 16:49
 **/

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

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
public class NonBlockingNIOClient {

    //客户端
    public static void clientTest(){
        SocketChannel socketChannel = null;
        try {
            //1. 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8999));
            //2. 切换为非阻塞模式
            socketChannel.configureBlocking(false);
            //3. 分配指定大小的缓冲区，用于读取数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //4. 读取数据并放入通道
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
               String str = scanner.next();
                byteBuffer.put((LocalDateTime.now().toString()+"\n:"+str).getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            NIOBlockingServer.closed(socketChannel);
        }
    }

    public static void main(String[] args){
        clientTest();
    }
}
