package person.yang.study.designpattern.decorator;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 11:32
 * 装饰模式学习类
 */
public class Decorator {
    public static void main(String args[]){
        /**
         * 装饰的方法是首先用ConcreteComponent实例化对象c，然后用
         * ConcreteDecoratorA的实例化对象d1来包装c，再用ConcreteDecoratorB
         * 的对象d2包装d1，最终执行d2的Operation
         */
        ConcreteComponent c = new ConcreteComponent();
        ConcreteDecoratorA d1 = new ConcreteDecoratorA();
        ConcreteDecoratorB d2 = new ConcreteDecoratorB();
        d1.setComponent(c);
        d2.setComponent(d1);
        d2.Operation();
    }

}

/**
 * 最上层的接口，可以给这些对象动态添加职责，也就是方法
 */
abstract class Component{
    public abstract void Operation();
}

/**
 * 具体操作对象，也可以给这个对象加些职责，也就是方法
 */
class ConcreteComponent extends Component{
    @Override
    public void Operation() {
        System.out.println("具体对象的操作");
    }
}

/**
 * Decorator装饰抽象类，扩展Component类的功能，
 * 对于Component来说，是无需知道Decorator的存在的
 */
abstract class DecoratorAbs extends Component{
    protected Component component;

    /**
     * 设置Component
     * @param component
     */
    public void setComponent(Component component){
        this.component = component;
    }

    /**
     * 重写职责方法，但是实际上执行的是传过来的Component的方法
     */
    @Override
    public void Operation() {
        if(component != null){
            //调用具体装饰类的方法实现
            component.Operation();
        }
    }
}

/**
 * 具体的装饰实现类A，起给Componet添加职责的功能
 */
class ConcreteDecoratorA extends DecoratorAbs{
    //本实现类独有的功能，区别于实现类B
    private String addFunction;

    @Override
    public void Operation() {
        //首先运行原Component也就是DecoratorAbs中的Operation方法，
        //再执行本类的特有方法
        super.Operation();
        addFunction = "new function";
        System.out.println("具体装饰类对象A的操作");
    }
}

/**
 * 具体装饰类B
 */
class ConcreteDecoratorB extends  DecoratorAbs{
    //同A类，先执行抽象DecoratorAbs中的方法，在执行本类方法，
    //相当于对原Component基础上进行了装饰
    @Override
    public void Operation() {
        super.Operation();
        methodOfB();
        System.out.println("具体装饰对象B的操作");
    }

    /**
     * 装饰类独有方法
     */
    private void methodOfB(){
        System.out.println("装饰类B特有方法");
    }
}