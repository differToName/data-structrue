package person.yang.study.designpattern.factorypattern;

/**
 * @Author: yangyl
 * @Date: 2019/5/17 8:42
 * 工厂模式，简单工厂模式
 */
public class SimpleFactoryPattern {

    public static void main(String args[]){
        Operator operator = OperatorFactory.createOperator("+");
        operator.number1 = 1d;
        operator.number2 = 2d;
        System.out.println(operator.getResult());
    }

}

/**
 * 运算类
 */
abstract class Operator{
    public Double number1;
    public Double number2;
    /**
     * 获取结果方法
     * @return
     */
    abstract Double getResult();
}

/**
 * 运算类-加法
 */
class OperatorAdd extends Operator{
//    public Double number1;参数放在抽象类中
//    public Double number2;

    public OperatorAdd() {
    }


    @Override
    Double getResult() {
        return number1+number2;
    }
}

/**
 * 运算类-减法
 */
class OperatorSub extends Operator{
//    public Double number1;
//    public Double number2;


    public OperatorSub() {
    }

    @Override
    Double getResult() {
        return number1 - number2;
    }
}

/**
 * 简单工厂
 */
class OperatorFactory{
    public static Operator createOperator(String operator){
        Operator operator1 = null;
        switch (operator){
            case "+":
                operator1 = new OperatorAdd();
                break;
            case "-":
                operator1 = new OperatorSub();
                break;
        }

        return operator1;
    }
}