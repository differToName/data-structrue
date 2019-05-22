package person.yang.study.designpattern.facade;

/**
 * @Author: yangyl
 * @Date: 2019/5/22 11:13
 * 外观模式
 */
public class Facade {
    public static void main(String[] args){
        //客户端可以不知道子系统的存在
        System.out.println("客户端调用-------------");
        FacadePattern facadePattern = new FacadePattern();
        facadePattern.methodA();
        facadePattern.methodB();
    }
}

/**
 * 外观类，需要了解所有子系统的方法或属性，
 * 进行组合，以备外界调用
 */
class FacadePattern{
    SubSystemOne one;
    SubSystemTwo two;
    SubSystemThree three;
    SubSystemFour four;

    public FacadePattern(){
        one = new SubSystemOne();
        two = new SubSystemTwo();
        three = new SubSystemThree();
        four = new SubSystemFour();
    }

    public void methodA(){
        System.out.println("外观类方法组A()");
        one.methodOne();
        two.methodTwo();
        three.methodThree();
    }

    public void methodB(){
        System.out.println("--外观类方法组B()");
        four.methodFour();
        two.methodTwo();
        one.methodOne();
    }
}

//子系统1
class SubSystemOne{
    public void methodOne(){
        System.out.println("子系统一方法");
    }
}

//子系统2
class SubSystemTwo{
    public void methodTwo(){
        System.out.println("子系统二方法");
    }
}

//子系统3
class SubSystemThree{
    public void methodThree(){
        System.out.println("子系统三方法");
    }
}

//子系统4
class SubSystemFour{
    public void methodFour(){
        System.out.println("子系统四方法");
    }
}