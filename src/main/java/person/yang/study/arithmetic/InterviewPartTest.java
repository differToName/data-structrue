package person.yang.study.arithmetic;

import java.util.Arrays;

/**
 * 关于面试相关的一些算法
 */
public class InterviewPartTest {
    public static void main(String[] args) {
        //斐波那契数列,0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377
//        System.out.println(fibonacci(2));
//        maopaoSort();
//        selectSort();
        int[] array = {1,6,3,9,12,4,6,7,11,23,21};
        quickSort(array, 0, array.length-1);
    }

    //快速排序，???没搞懂
    private static void quickSort(int[] array,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i = low;//标记
        j = high;
        //基准位
        temp = array[low];
        while(i<j){
            //即最开始最左边是基准位，右边先开始找比基准位小的那一位
            while (temp<=array[j] && i<j){
                j--;
            }
            //右边基位找到了后，开始找小于左边的基位
            while (temp>=array[i] && i<j){
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        array[low] = array[i];
        array[i] = temp;
        //递归调用左半数组
        quickSort(array, low, j-1);
        //递归调用右半数组
        quickSort(array, j+1, high);
    }


    //选择排序
    //实现思路是每一轮循环找到最小的值，依次排到数组的最前面，这样就实现了数组的有序排列。
    private static void selectSort(){
        int[] array = {1,6,3,9,12,4,6,7,11,23,21};
        int minIndex;
        int temp;
        for(int i=0;i<array.length-1;i++){
            minIndex = i;
            //循环一次，找出最小的一位，i+1跨过头一位
            for(int j=i+1;j<array.length;j++){
                if(array[j]<array[minIndex]){
                    minIndex = j;
                }
            }
            //已经完成找出第一次的最小值
            if(minIndex != i){
                temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
        System.out.println(Arrays.toString(array));
    }

    //冒泡排序法1
    //最坏的情况：O(n2)【n的平方】，最好情况是O(n)
    private static void maopaoSort(){
        int[] array = {1,6,3,9,12,4,6,7,11,23,21};
        int temp;
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-i-1;j++){
                if(array[j]>array[j+1]){
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }

    //斐波那契数列,0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377
    //及前两个和是第三个
    private static int fibonacci(int index){
        if(index ==0 || index == 1){
            return index;
        }else {
            return fibonacci(index - 1) + fibonacci(index - 2);
        }
    }

    //二分法查找，平均查找长度=Log2(n+1)-1
    //二分查找特别适用于那种一经建立就很少改动而又经常需要查找的线性表
    public static void binarySearch(){
        //二分法适合有顺序的数组
        int[] seqArray = {1,6,9,11,20,56,78};
        int low = 0;//低位默认
        int high = seqArray.length-1;//高位
        int findValue = 56;//要查找的数值
        //二分法查找,低位不超过高位
        while(low <= high){
            int mid = (high+low)/2;//mid位
            System.out.println("取得mid的值："+mid);
            if(seqArray[mid] == findValue){
                System.out.println("找到目标值了，mid:"+mid);
                break;
            }
            //大于中间值，则将low向前移动0->1
            if(findValue > seqArray[mid]){
                low = mid + 1;
                System.out.println("查找值大于mid值，low增加："+low);
            }else{
                //其他情况即小于中间值，则high往小方向走
                high = mid -1;
                System.out.println("查找值小于mid值，high缩小："+high);
            }
            //当移动low大于high时说明没有这个元素
            if(low > high){
                System.out.println("没有查找到此元素");
                break;
            }
        }
    }
    //二分法插入
    //https://blog.csdn.net/zoran_/article/details/52504556
    public static void binaryInsert(){
        int[] binaryArg = new int[]{1,6,9,11,23,26,29};
        int high = binaryArg.length-1;
        int low = 0;
        int mid = 0;//初始化
        //要插入的值
        int insertValue = 20;
        //先确定好要插入的位置
        while (low<=high){
            mid = (high+low)/2;
            //小于数组的中间值，则high像小移动
            if(insertValue < binaryArg[mid]){
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        //确定好位置
        System.out.println("确定好的位置，一般以low为插入位置:high:"+high+",low:"+low);
        //向后移动,此处为固定数组，故最后一位会被淘汰，可以重新设置一个数组
        for(int i=binaryArg.length-1;i>=low;i--){
            binaryArg[i+1] = binaryArg[i];
//            System.out.println("新的数组："+binaryArg[i+1]);
        }
        binaryArg[low] = insertValue;
        System.out.println("新的数组："+ Arrays.toString(binaryArg));
    }

}
