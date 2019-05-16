package person.yang.study.designpattern.proxypattern;

/**
 * @Author: yangyl
 * @Date: 2019/5/16 9:11
 * 代理模式
 * 类似于B追求A，但托C去给A送礼
 * 代理模式即追求者B通过代理人C去给A送礼
 */
public class ProxyPattern {

    public static void main(String args[]){
        SchoolGirlA girlA = new SchoolGirlA("marry");
        //中间人C找到girlA
        ProxyPersonC personC = new ProxyPersonC(girlA);
        personC.giveDolls();
        personC.giveFlowers();
        personC.giveChocolate();
    }

}

/**
 * 代理接口
 */
interface GiveGift{
    void giveDolls();
    void giveFlowers();
    void giveChocolate();
}

/**
 * 被追求的女孩
 */
class SchoolGirlA{
    private String name;

    public SchoolGirlA(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SchoolGirl{" +
                "name='" + name + '\'' +
                '}';
    }
}

/**
 * 追求者
 */
class PursuerB implements GiveGift{

    SchoolGirlA girlA;

    public PursuerB(SchoolGirlA girlA){
        this.girlA = girlA;
    }

    @Override
    public void giveDolls() {
        System.out.println(girlA.getName() + "，送你洋娃娃");
    }

    @Override
    public void giveFlowers() {
        System.out.println(girlA.getName() + "，送你花花");
    }

    @Override
    public void giveChocolate() {
        System.out.println(girlA.getName() + "，送你巧克力");
    }
}

/**
 * 代理类,中间人C
 */
class ProxyPersonC implements GiveGift{
    //那个追求者
    PursuerB pursuerB;

    public ProxyPersonC(SchoolGirlA girlA){
        //中间人通过这种方式去找A
        pursuerB = new PursuerB(girlA);
    }

    @Override
    public void giveDolls() {
        pursuerB.giveDolls();
    }

    @Override
    public void giveFlowers() {
        pursuerB.giveFlowers();
    }

    @Override
    public void giveChocolate() {
        pursuerB.giveChocolate();
    }
}
