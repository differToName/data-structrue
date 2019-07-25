package person.yang.study.nio.blocking;

/**
 * @ClassName NonBlockingNIOServer
 * @Description: 非阻塞式网络通信
 * @Author ylyang
 * @Date 2019/7/24 10:12
 **/

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

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
public class NonBlockingNIOServer {

    public static void serverTest(){
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel clientChannel = null;
        try {
            //1. 获取通道
            serverSocketChannel = ServerSocketChannel.open();
            //2. ☺ 使用非阻塞模式
            serverSocketChannel.configureBlocking(false);

            //3. 绑定连接
            //或者使用，则不用绑定
            // Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),8999);
            // ServerSocketChannel = socket.getChannel();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8999));

            //4. ☺！！获取选择器
            Selector selector = Selector.open();
            //5. ☺ 将通道注册到选择器中，此处为“监听接收事件”,可使用 位或 运算符来实现监听多种类型
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //6. ☺ 轮询式的获取选择器上 已经 “准备就绪”的事件
            while (selector.select() > 0){
                //7. ☺ 获取当前选择器中所有注册的“选择键”（已就绪的监听事件）
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历选择键
                while(iterator.hasNext()){
                    //8. ☺ 获取准备 就绪 的事件
                    SelectionKey sk = iterator.next();
                    //9. ☺ 判断具体是什么事件 准备就绪
                    if(sk.isAcceptable()){//是接收准备就绪事件
                        //10. ☺ 是接收准备就绪则获取客户端连接
                        clientChannel = serverSocketChannel.accept();
                        //11. ☺ 切换为非阻塞模式
                        clientChannel.configureBlocking(false);
                        //12. ☺ 将客户端连接通道注册到选择器上
                        clientChannel.register(selector,SelectionKey.OP_READ);
                    }else if(sk.isReadable()){//是读准备就绪事件，此应该是接收上一个if所注册到选择器中的读事件
                        //13. ☺ !!! 获取当前选择器上“读就绪”状态的通道
                        //Returns the channel for which this key was created.  This method will
                        //continue to return the channel even after the key is cancelled.
                        clientChannel = (SocketChannel) sk.channel();
                        //14. ☺ 获取数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        while((len = clientChannel.read(byteBuffer)) > 0){
                            //修改为读模式
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array(),0,len));
                            byteBuffer.clear();
                        }
                    }
                    //!注意位置
                    //取消选择键，不然若客户端只是一次性运行，到如上客户端注册入选择器后，再循环读
                    //出数据，之后未取消选择键，在进行循环进入是接收就绪(没有取消一直有效)，而此时客户端
                    //已经停止，则会报空指针异常
                    iterator.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            NIOBlockingServer.closed(serverSocketChannel);
            NIOBlockingServer.closed(clientChannel);
        }
    }

    public static void main(String[] args){
        serverTest();
    }

}
