package com.ly.liugw.demo.test.getobjectmemory;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * 获取对象的内存地址
 *   Java不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe类提供了硬件级别的原子操作,在java中内存中的对象地址是可变的，所以获得的内存地址有可能会变化。要获得内存地址也只能通过Unsafe的方法来获得,下面类提供了获取java对象内存地址的方法
 *   64位JVM中，标记字段和类型指针各占64位（一共16个字节），比如Integer类，仅有一个私有的int字段（4个字节），而头部额外多出16个字节，因此，每一个Integer的内存额外开销至少是400%，这也是java要引入基本类型的原因之一。
 *
 *   为了减少对象内存的使用，64位JVM引入了压缩指针的概念（虚拟机选项-XX:+UseCompressedOops，默认开启），将堆中的64位指针压缩成32位，这样以来，对象头占用的内存就从16字节下降到了12字节。
 *
 *  默认情况下，JVM堆中对象的起始位置需要对齐到8的倍数，如果一个对象用不到8N个字节，那么空白的那部分空间就浪费掉了，这些浪费的空间我们称之为对象的填充。
 */
public class Addresser {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) throws Exception {

        Object[] array = new Object[] { o };

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        //arrayBaseOffset方法是一个本地方法，可以获取数组第一个元素的偏移地址
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                //getInt方法获取对象中offset偏移地址对应的int型field的值
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                //getLong方法获取对象中offset偏移地址对应的long型field的值
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

    public static void main(String... args) throws Exception {
        Object mine = "Hello world".toCharArray(); //先把字符串转化为数组对象
        long address = addressOf(mine);
        System.out.println("Addess: " + address);

        // Verify address works - should see the characters in the array in the output
        // 16 字节的对象头
        printBytes(address, 17);

        A a = new A();
        a.name = "Hello world";
        address = addressOf(a);
        System.out.println("Addess: " + address);
        printBytes(address, 1000);
    }

    public static void printBytes(long objectAddress, int num) {
        for (long i = 0; i < num; i++) {
            int cur = unsafe.getByte(objectAddress + i);
            System.out.print((char) cur);
        }
        System.out.println();
    }
}

class A {
    public String name ;
}
