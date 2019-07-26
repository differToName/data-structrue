package person.yang.study.nio;

import person.yang.study.nio.blocking.NIOBlockingServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName PipeLearning
 * @Description: 管道测试
 * @Author ylyang
 * @Date 2019/7/25 16:27
 **/
public class PipeLearning {

    public static void pipeTest(){
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Pipe pipe = null;
        Pipe.SourceChannel sourceChannel = null;
        Pipe.SinkChannel sinkChannel = null;
        try {//---用多线程写
            pipe = Pipe.open();
            //将缓冲写入管道中
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //sink用于写数据
            sinkChannel = pipe.sink();
            buf.put("向管道发送信息".getBytes());
            buf.flip();
            sinkChannel.write(buf);

            //读取管道中的消息
            sourceChannel = pipe.source();
            buf.flip();
            int len = sourceChannel.read(buf);
            System.out.println(new String(buf.array(),0,len));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            NIOBlockingServer.closed(sourceChannel);
            NIOBlockingServer.closed(sinkChannel);
        }
    }

    public static void main(String[] args){
        pipeTest();
    }
}
