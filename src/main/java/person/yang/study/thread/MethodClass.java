package person.yang.study.thread;

import org.springframework.stereotype.Component;
import person.yang.study.thread.model.ThreadModel;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 8:55
 * 相当于一个公共处理类
 */
@Component
public class MethodClass {

    public void doSomething(ThreadModel model){

        switch (model.getCount()){
            case 1:
                model.setDesc("1234567"+model.getDesc());
                model.setNews("count=1");
                break;
            case 2:
                model.setDesc("7654321"+model.getDesc());
                model.setNews("count=2");
                break;
            case 3:
                model.setDesc("1111111"+model.getDesc());
                model.setNews("count=3");
                break;
            case 4:
                model.setDesc("2222222"+model.getDesc());
                model.setNews("count=4");
                break;
        }
    }
}
