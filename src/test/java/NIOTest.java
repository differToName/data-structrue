import io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @ClassName NIOTest
 * @Description:
 * @Author ylyang
 * @Date 2019/7/16 16:30
 **/
/**
 * 1.常见缓冲区：
 * ByteBuffer（最常用）
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 上述缓冲区的管理方式几乎一致，都是通过 allocate() 方法获取缓冲区
 *
 * 2.缓冲区存取数据的两个核心方法：
 *     put()：把数据存入缓冲区
 *     get()：获取缓冲区中的数据
 *
 * 3.缓冲区四个核心属性：
 *     详细见Buffer源码，ByteBuffer等继承Buffer，其核心四属性如下：
 *     Invariants(不变性（规律）): mark <= position <= limit <= capacity
 *
 *     private int mark = -1;//标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 *
 *     private int position = 0;//位置，表示缓冲区中可以操作数据的位置
 *     private int limit;//界限，表示缓冲区中可以操作数据的大小，即limit之后数据不能进行读写
 *     private int capacity;//容量，表示缓冲区中最大存储数据的容量，一旦声明不能改变
 */
public class NIOTest {
    @Test
    public void nioTest(){
        String testStr = "abcd";
        //分配缓冲区大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024

        //利用put()方法写数据,现在position将改变而limit和capacity不变
        buffer.put(testStr.getBytes());
        System.out.println(buffer.position());//4
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024

        //调用flip()方法，将写模式改为读模式，此时position变为0，
        // limit变为有数据(position之前位置)位置，capacity不变，即limit之后不允许读
        buffer.flip();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//4
        System.out.println(buffer.capacity());//1024

        //读取数据
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes,0,2));//abcd
        System.out.println(buffer.position());//4
        System.out.println(buffer.limit());//4
        System.out.println(buffer.capacity());//1024

        //rewind()方法，可重复读数据
        buffer.rewind();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//4
        System.out.println(buffer.capacity());//1024

        //clear()方法，清空缓存,！！但是里面的数据还在，但是出于“被遗忘状态”
        buffer.clear();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024
        //测试清空后缓冲区是否有数据
        System.out.println((char) buffer.get());//a

        //测试关键属性mark，用于标记上一次position位置，可通过reset()方法回到上一次位置
        System.out.println("---------------------------------------测试mark标记、展示其它方法");
        ByteBuffer newBuf = ByteBuffer.allocate(1024);
        String newStr = "12345";
        byte[] newByte = new byte[1024];
        newBuf.put(newStr.getBytes());
        newBuf.flip();
        System.out.println(newBuf.position());//0
        //标记position的位置
        newBuf.mark();
        newBuf.get(newByte,0,2);
        //读取一次后的position
        System.out.println("字符串："+new String(newByte,0,2)+",位置："+newBuf.position());//字符串：12,位置：2
        //重置回到上一次标记位置
        newBuf.reset();
        System.out.println(newBuf.position());//0

        //其它方法：是否还有空余操作空间
        if(newBuf.hasRemaining()){
            //输出剩余操作空间,此处是读模式，故只有5
            newBuf.flip();
            System.out.println(newBuf.remaining());//5
        }

    }
}
