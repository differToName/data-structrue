package person.yang.study.nio;

/**
 * @ClassName ChannelTest
 * @Description: 通道测试
 * @Author ylyang
 * @Date 2019/7/19 16:00
 **/

/**
 * 1.通道(Channel):
 *     用于源节点与目标节点的连接，在Java NIO中负责缓冲区数据的传输，Channel
 *     本身不存数据，因此需要配合缓冲区进行传输。
 * 2.通道的主要实现类
 *     主要是实现java.nio.channels.Channel接口:
 *          --FileChannel   一般（本地）文件使用
 *          --SocketChannel     网络使用(TCP)
 *          --ServerSocketChannel     网络使用(TCP)
 *          --DatagramChannel     网络使用(UDP)
 * 3.获取通道
 *   方法一：
 *      java针对支持通道的类提供getChannel()方法
 *      本地IO：
 *          FileInputStream/FileOutputStream
 *          RandomAccessFile
 */
public class ChannelTest {


    public static void main(String args[]){

    }
}
