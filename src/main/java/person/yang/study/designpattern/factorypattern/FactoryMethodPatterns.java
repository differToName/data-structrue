package person.yang.study.designpattern.factorypattern;

/**
 * @Author: yangyl
 * @Date: 2019/5/17 9:36
 * 工厂方法模式
 */
public class FactoryMethodPatterns {

    public static void main(String[] args){
        IFactory factory = new AddFatory();
        Operator operator = factory.createOperator();
        operator.number1 = 1d;
        operator.number2 = 2d;
        System.out.println(operator.getResult());
    }
}

/**
 * 工厂接口，每个工厂都会实现这个接口，都有公共的生产方法
 */
interface IFactory{
    /**
     * 使用另外一个包类
     * @return
     */
    Operator createOperator();
}

/**
 * 加法工厂
 */
class AddFatory implements IFactory{

    @Override
    public Operator createOperator() {
        return new OperatorAdd();
    }
}

/**
 * 减法工厂
 */
class SubFactory implements IFactory{
    @Override
    public Operator createOperator() {
        return new OperatorSub();
    }
}