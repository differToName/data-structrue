package person.yang.study.designpattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangyl
 * @Date: 2019/5/22 15:18
 * 建造者模式
 */
public class Builder {

    public static void main(String[] args){
        Director director = new Director();
        BuilderAbs bA = new ConcreteBuilderA();
        BuilderAbs bB = new ConcreteBuilderB();
        //指挥者来构建创造过程
        director.construct(bA);
        Product product = bA.getResult();
        product.show();

        director.construct(bB);
        Product product1 = bB.getResult();
        product1.show();
    }

}

/**
 * 产品类，
 */
class Product{
    List<String> parts = new ArrayList<>();

    /**
     * 添加产品部件
     * @param part
     */
    public void add(String part){
        parts.add(part);
    }

    /**
     * 展示类
     */
    public void show(){
        System.out.println("产品共有：");
        for (String part:parts
             ) {
            System.out.println(part);
        }
    }
}

/**
 * bulider抽象类
 * 抽象要实现的方法，即必须实现的部分
 */
abstract class BuilderAbs{
    /**
     * 必须创建的部分A
     */
    public abstract void builderPartA();
    /**
     * 必须创建的部分B
     */
    public abstract void builderPartB();
    public abstract Product getResult();
}

/**
 * 具体建造者，继承Builder，必须实现其定义的方法部分
 */
class ConcreteBuilderA extends BuilderAbs{

    private Product product = new Product();

    @Override
    public void builderPartA() {
        //System.out.println("具体建造者A生产部件A");
        product.add("具体建造类A实现的部件A");
    }

    @Override
    public void builderPartB() {
        //System.out.println("具体建造者A生产部件B");
        product.add("具体建造类A实现的部件A");
    }

    @Override
    public Product getResult() {
        return product;
    }
}

/**
 * 具体建造者B，同A类似，实现不同的方式的部件
 */
class ConcreteBuilderB extends BuilderAbs{

    private Product product = new Product();

    @Override
    public void builderPartA() {
       // System.out.println("具体建造者B生产部件AX");
        product.add("具体建造类B实现的部件AX");
    }

    @Override
    public void builderPartB() {
        //System.out.println("具体建造者B生产部件BX");
        product.add("具体建造类B实现的部件BX");
    }

    @Override
    public Product getResult() {
        return product;
    }
}

/**
 * 指挥者类，用来构建创造过程
 */
class Director{
    public void construct(BuilderAbs builder){
        builder.builderPartA();
        builder.builderPartB();
    }
}