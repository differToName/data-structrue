package person.yang.study.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import person.yang.study.thread.model.ThreadModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 9:00
 * 线程类
 */
@Component
public class ThreadClass {
    @Autowired
    MethodClass methodClass;

    Integer thnum = 10;

    public void testMth() throws Exception{
        ThreadModel model = new ThreadModel("1","+","1234",1);
        ThreadModel model1 = new ThreadModel("2","-","1234",2);
        ThreadModel model2 = new ThreadModel("6","=","1234",3);
        ThreadModel model3 = new ThreadModel("3","*","1234",3);
        ThreadModel model4 = new ThreadModel("4","1234","1234",2);
        ThreadModel model5 = new ThreadModel("5","1234","1234",4);
        BlockQueue.SENDCACHEQUEUE.put(model);
        BlockQueue.SENDCACHEQUEUE.put(model1);
        BlockQueue.SENDCACHEQUEUE.put(model2);
        BlockQueue.SENDCACHEQUEUE.put(model3);
       // BlockQueue.SENDCACHEQUEUE.put(model4);
        //BlockQueue.SENDCACHEQUEUE.put(model5);

        ExecutorService threadPool = Executors.newFixedThreadPool(thnum);

        Runnable runner = () -> {
            ThreadModel modelTemp = new ThreadModel();
            try{
                modelTemp = BlockQueue.SENDCACHEQUEUE.poll(60, TimeUnit.SECONDS);
                methodClass.doSomething(modelTemp);
                System.out.println(modelTemp+"------当前线程"+Thread.currentThread().getName());
            }catch(Exception e){
                System.out.println("错误");
            }

        };

        for(int i = 0;i < thnum ;i++){
            threadPool.execute(runner);
        }
    }

}
