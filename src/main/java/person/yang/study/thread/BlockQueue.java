package person.yang.study.thread;

import org.springframework.stereotype.Component;
import person.yang.study.thread.model.ThreadModel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 9:28
 */
@Component
public class BlockQueue {

    //发送CACHEMQ阻塞队列大小
    public static final int QUEUE_SIZE = 1000;
    //发送CACHEMQ阻塞队列
    public static final BlockingQueue<ThreadModel> SENDCACHEQUEUE = new ArrayBlockingQueue<ThreadModel>(QUEUE_SIZE);

}