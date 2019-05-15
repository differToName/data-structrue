package person.yang.study.thread.model;

/**
 * @Author: yangyl
 * @Date: 2019/5/15 8:54
 *
 */
public class ThreadModel {
    private String name;
    private String desc;
    private String news;
    private Integer count;

    public ThreadModel(String name, String desc, String news, Integer count) {
        this.name = name;
        this.desc = desc;
        this.news = news;
        this.count = count;
    }

    public ThreadModel() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "ThreadModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", news='" + news + '\'' +
                ", count=" + count +
                '}';
    }
}
