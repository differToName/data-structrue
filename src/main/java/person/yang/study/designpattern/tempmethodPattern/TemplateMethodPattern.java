package person.yang.study.designpattern.tempmethodPattern;

/**
 * @Author: yangyl
 * @Date: 2019/5/21 14:52
 * 模板方法模式
 */
public class TemplateMethodPattern {

    public static void main(String[] args){
        //用多态的特性实现不同的逻辑操作
        AbstractParent parent;
        parent = new ConcreteClassA();
        parent.templateMethod();

        parent = new ConcreteClassB();
        parent.templateMethod();
    }
}

/**
 * 抽象父类，即算法骨架，一个抽象模板，
 * 一些算法步骤推迟到子类去实现
 */
abstract class AbstractParent{
    //一些抽象行为，即被推迟的算法，需要在子类中去实现
    public abstract void primitiveOperation1();
    public abstract void primitiveOperation2();

    /**
     * 模板方法，构建逻辑框架，但是部分逻辑细节由子类实现
     * ，是公共使用的
     */
    public void templateMethod(){
        System.out.println("Template method start!");
        primitiveOperation1();
        primitiveOperation2();
        System.out.println("Template method end!");
    }
}

/**
 * 模板父类的步骤中，将要实现细节步骤的子类A
 */
class ConcreteClassA extends AbstractParent{

    @Override
    public void primitiveOperation1() {
        System.out.println("具体类A方法1的实现！");
    }

    @Override
    public void primitiveOperation2() {
        System.out.println("具体类A方法2的实现！");
    }
}

/**
 * 模板父类的步骤中，将要实现细节步骤的子类B
 */
class ConcreteClassB extends AbstractParent{

    @Override
    public void primitiveOperation1() {
        System.out.println("具体类B方法1的实现！");
    }

    @Override
    public void primitiveOperation2() {
        System.out.println("具体类B方法1的实现！");
    }
}