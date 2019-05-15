package person.yang.study.designpattern.strategy;

/**
 * @Author: yangyl
 * @Date: 2019/5/13 13:54
 * 策略模式
 */
public class Strategy {

    public static void main(String args[]){
        //相比之下，在纯策略模式下，main方法中需要知道Context和以及其子类（多）
        Context context;
//        context = new Context(new ConcreteStrategyB());
//        context.contextInterface();
//
//        context = new Context(new ConcreteStrategyC());
//        context.contextInterface();
        //而策略模式和简单工厂模式的结合，main方法中只需关注ContextWithFactory类，降低了耦合
        ContextWithFactory contextWithFactory = new ContextWithFactory("A");
        contextWithFactory.getResult();
//        String a = "1234567";
//        System.out.println(a.substring(1,3));
    }
}



/**
 * 抽象算法类
 */
abstract class StrategyAbs{
    /**
     * 算法方法
     */
    public abstract void algorithmInterface();
}

/**
 * 封装具体算法或行为，继承于StrategyAbs
 * 具体算法A
 */
class ConcreteStrategyA extends StrategyAbs{
    /**
     * 算法A实现方法
     */
    @Override
    public void algorithmInterface(){
        System.out.println("算法实现方法A");
    }
}

/**
 * 具体算法B
 */
class ConcreteStrategyB extends StrategyAbs{

    /**
     * 算法B具体实现
     */
    @Override
    public void algorithmInterface() {
        System.out.println("具体算法实现B");
    }
}

/**
 * 具体算法C
 */
class ConcreteStrategyC extends StrategyAbs{
    /**
     * 具体算法实现C
     */
    @Override
    public void algorithmInterface() {
        System.out.println("具体算法实现C");
    }
}

/**
 * 上下文
 * 初始化时，传入具体的策略对象
 */
class Context{
    StrategyAbs strategyAbs;
    //构造方法
    //初始化时，传入具体的策略对象
    public Context(StrategyAbs strategyAbs){
        this.strategyAbs = strategyAbs;
    }
    //上下文接口
    //根据决堤的策略对象，调用其算法的方法
    public void contextInterface(){
        strategyAbs.algorithmInterface();
    }
}

/**
 * 策略模式和工厂模式的结合
 * 策略模式和简单工厂模式的结合，main方法中只需关注ContextWithFactory类，降低了耦合
 */
class ContextWithFactory{
    StrategyAbs strategyAbs = null;

    /**
     * 取简单工厂模式的特点
     * @param type
     */
    public ContextWithFactory(String type){
        switch (type){
            case "A":
                strategyAbs = new ConcreteStrategyA();
                break;
            case "B":
                strategyAbs = new ConcreteStrategyB();
                break;
            case "C":
                strategyAbs = new ConcreteStrategyC();
                break;
        }
    }

    public void getResult(){
        strategyAbs.algorithmInterface();
    }
}