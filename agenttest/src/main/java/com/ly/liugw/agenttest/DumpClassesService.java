package com.ly.liugw.agenttest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C), 2018-2018, open source
 * FileName: DumpClassesService
 *
 * @author : liugw
 * Date:     2019/12/8 21:01
 */
public class DumpClassesService implements ClassFileTransformer {
    private static final List<String> SYSTEM_CLASS_PREFIX = Arrays.asList("java", "sum", "jdk");

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!isSystemClass(className)) {
            System.out.println("load class " + className);
            FileOutputStream fos = null;
            try {
                // 将类名统一命名为classNamedump.class格式
                fos = new FileOutputStream(className + "dump.class");
                fos.write(classfileBuffer);
                fos.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                // 关闭文件输出流
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classfileBuffer;
    }

    /**
     * 判断一个类是否为系统类
     *
     * @param className 类名
     * @return System Class then return true,else return false
     */
    private boolean isSystemClass(String className) {
        // 假设系统类的类名不为NULL而且不为空
        if (null == className || className.isEmpty()) {
            return false;
        }

        for (String prefix : SYSTEM_CLASS_PREFIX) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}