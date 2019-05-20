package person.yang.study.designpattern.prototypepattern;


import java.io.*;

/**
 * @Author: yangyl
 * @Date: 2019/5/17 10:50
 * 基本原型模式
 */
public class BasicPrototypePattern{


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 浅复制测试
        //被克隆的原型
//        Address address = new Address("深圳");
        //克隆对象
//        Address cloneAddress = address.clone();
//        System.out.println("提供的原型城市名称："+address.getLocation());
//        System.out.println("克隆城市名称："+cloneAddress.getLocation());
//        System.out.println("两个对象是否相同："+(address == cloneAddress));
        /*输出：提供的原型城市名称：深圳
               克隆城市名称：深圳
               两个对象是否相同：false*/

        //浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的（这样不安全）。
        //深复制测试
        System.out.println("深复制测试-------------------------------------------------------------------");
        Address address = new Address("深圳");
        Student s = new Student("小明",address);
        Student sclone = (Student) s.clone();
        System.out.println("学生姓名：" + s.getName() + "   克隆学生姓名：" + sclone.getName());
        System.out.println("学生地址：" + s.address.getLocation() + "   克隆学生地址：" + sclone.address.getLocation());
        System.out.println("两个学生对象"+(s == sclone?"相同":"不同"));
        System.out.println("修改被克隆学生姓名为李四，地址为四川，");
        s.setName("李四");
        s.address.setLocation("四川");
        System.out.println("学生姓名：" + s.getName() + "   克隆学生姓名：" + sclone.getName());
        System.out.println("学生地址：" + s.address.getLocation() + "   克隆学生地址：" + sclone.address.getLocation());
        //得出结论：浅复制只复制值类型的变量和对对象的引用
        //         深复制不仅复制值类型的变量，把原对象引用的对象也进行复制.


        //完全拷贝测试
        System.out.println("-------------------------------------------------------------完全复制测试");
        Address address1 = new Address("重庆");
        Author author = new Author("小样",address1);
        Author cloneAuthor = author.absoluteClone();
        System.out.println("原作者姓名："+author.getName()+"，地址为："+author.getAddress().getLocation());
        System.out.println("克隆作者姓名："+cloneAuthor.getName()+",克隆作者地址："+cloneAuthor.getAddress().getLocation());
        System.out.println("修改原作者的相关信息---------");
        author.setName("小闷");
        author.setAddress(new Address("四川"));
        System.out.println("原作者姓名："+author.getName()+"，地址为："+author.getAddress().getLocation());
        System.out.println("克隆作者姓名："+cloneAuthor.getName()+",克隆作者地址："+cloneAuthor.getAddress().getLocation());
    }
}

/**
 * 原型类
 */
abstract class Prototype{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Prototype(String id) {
        this.id = id;
    }
    //此方法为此类关键
    public abstract Prototype clone();
}


/**
 * 浅复制
 */
class Address implements Cloneable,Serializable{
    //注意要实现序列化
    private static final long serialVersionUID = -8987133685524375197L;
    private String location;

    public Address(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Address clone(){
        Address a = null;
        try{
            a = (Address) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return a;
    }
}

/**
 * 深拷贝
 */
class Student implements Cloneable{
    private String name;
    Address address;

    public Student(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Student clone(){
        Student s = null;
        try{
            s = (Student)super.clone();
            //原对象引用的对象也复制
            s.address = (Address)address.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return s;
    }
}

/**
 * 深复制，引用对象也要实现Serializable
 */
class Author implements Serializable {

    private static final long serialVersionUID = -1803241232940821850L;
    private String name;
    Address address;

    public Author(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 使用序列化技术实现完全拷贝
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Author absoluteClone() throws IOException,ClassNotFoundException{
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);
        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Author) ois.readObject();
    }
}