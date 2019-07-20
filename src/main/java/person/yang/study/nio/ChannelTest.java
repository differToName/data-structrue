package person.yang.study.nio;

/**
 * @ClassName ChannelTest
 * @Description: 通道测试
 * @Author ylyang
 * @Date 2019/7/19 16:00
 **/

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
 *      java针对支持通道的类提供getChannel()方法，以下是支持此方法的类
 *      本地IO：
 *          FileInputStream/FileOutputStream
 *          RandomAccessFile    随机存储文件流
 *
 *      网络IO：
 *          Socket
 *          ServerSocket
 *          DatagramSocket
 *   方法二：
 *      在JDK1.7中的NIO.2 针对各个通道提供了静态方法open()
 *   方法三：
 *      在JDK1.7中的NIO.2 的Files工具类的newByteChannel()
 * 4.通道之间的数据传输
 *      transferFrom()
 *      transferTo()
 * 5.分散(Scatter)和聚集(Gather)
 *      分散读取(Scattering Reads):将通道中的数据分散到多个缓冲区
 *      聚集写入(Gathering Writes):将多个缓冲区中的数据 聚集到通道中
 * 6.字符集:Charset
 *      编码：字符串-->字节数组
 *      解码：字节数组-->字符串
 */
public class ChannelTest {

    /**
     * 测试3，方法一，非直接缓冲区
     */
    public static void testMethodOne(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        //1.申明并获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inputStream = new FileInputStream("d://1.png");
            outputStream = new FileOutputStream("d://2.png");
            //1.获取通道
            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();

            //2.通道不能存储数据，需要buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //3.读数据
            while (inChannel.read(buffer) != -1){
                //修改为   读取模式
                buffer.flip();
                //4. 将缓冲区中数据写入通道
                outChannel.write(buffer);
                //5. !!清空缓冲区，重新读取
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试3，方法一，直接缓冲区（内存映射文件）,只有ByteBuffer
     * 此处如果文件很大，多运行几次，可能出现内存占用过高(系统垃圾回收没即使处理)
     */
    public static void testMethodOne2(){
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            //参数二： Options specifying how the file is opened
            inChannel = FileChannel.open(Paths.get("d://","1.png"), StandardOpenOption.READ);
            //第三个参数，CREATE存在及覆盖，CREATE_NEW存在即报错
            outChannel = FileChannel.open(Paths.get("d://","3.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

            //内存映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
            //读写模式
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //直接对缓冲区进行数据的读写操作
            byte[] dst = new byte[inMappedBuf.limit()];
            inMappedBuf.get(dst);
            outMappedBuf.put(dst);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *测试4小点,通道间的数据传输(直接缓冲区)
     */
    public static void testMethod4(){
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("d://1.png"),StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("d://4.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
            //inChannel.transferTo(0,inChannel.size(),outChannel);
            //两个方法都是类似，只是其实例对象不同
            outChannel.transferFrom(inChannel,0,inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试分散和聚集
     */
    public static void testMethod5(){
        RandomAccessFile scatterFile = null;
        RandomAccessFile gatherFile = null;
        FileChannel scatterChannel = null;
        FileChannel gatherChannel = null;
        try {
            scatterFile = new RandomAccessFile("d://1.txt","rw");
            //1.获取通道
            scatterChannel = scatterFile.getChannel();
            //2.分配分散存储缓冲区
            ByteBuffer buf1 = ByteBuffer.allocate(100);
            ByteBuffer buf2 = ByteBuffer.allocate(1024);
            //3.分散读取
            ByteBuffer[] buf = {buf1,buf2};
            scatterChannel.read(buf);
            //更改模式为读
            for(ByteBuffer buffer : buf){
                buffer.flip();
                System.out.println(new String(buffer.array(),0,buffer.limit()));
                System.out.println("------------------------");
            }
            //4. 聚集写入
            gatherFile = new RandomAccessFile("d://2.txt","rw");
            gatherChannel = gatherFile.getChannel();
            gatherChannel.write(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        //testMethodOne();
        //testMethodOne2();
        //testMethod4();
        testMethod5();
    }
}
