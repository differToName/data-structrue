package person.yang.study.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 9:24
 */
@Component
public class InitThread implements CommandLineRunner {
    @Autowired
    private ThreadClass threadClass;

    @Override
    public void run(String... args) throws Exception {
        threadClass.testMth();
    }
}
