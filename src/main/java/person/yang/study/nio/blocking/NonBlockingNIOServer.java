package person.yang.study.nio.blocking;

/**
 * @ClassName NonBlockingNIOServer
 * @Description: 非阻塞式网络通信
 * @Author ylyang
 * @Date 2019/7/24 10:12
 **/

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

}
