package com.ly.liugw.demo.test.systeminfo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

@Slf4j
public final class CmdToolkit {

        private CmdToolkit() { 
        }

    public static String currentPid() {
        // https://stackoverflow.com/a/7690178
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        int index = jvmName.indexOf('@');

        String PID = "";

        if (index > 0) {
            try {
                PID = Long.toString(Long.parseLong(jvmName.substring(0, index)));
            } catch (Throwable e) {
                // ignore
            }
        }

        return PID;
    }

        /** 
         * 读取控制命令的输出结果 
         * 
         * @param cmd                命令 
         * @param isPrettify 返回的结果是否进行美化（换行），美化意味着换行，默认不进行美化,当此参数为null时也不美化， 
         * @return 控制命令的输出结果 
         * @throws IOException 
         */ 
        public static String readConsole(String cmd, Boolean isPrettify) throws IOException, InterruptedException {
                StringBuffer cmdout = new StringBuffer(); 
                log.info("执行命令：" + cmd); 
                Process process = Runtime.getRuntime().exec(cmd);     //执行一个系统命令 
                InputStream fis = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null; 
                if (isPrettify == null || isPrettify) { 
                        while ((line = br.readLine()) != null) { 
                                cmdout.append(line); 
                        } 
                } else { 
                        while ((line = br.readLine()) != null) { 
                                cmdout.append(line).append(System.getProperty("line.separator")); 
                        } 
                }
            /**
             * 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止
             */
            //process.waitFor();
                log.info("执行系统命令后的结果为：\n" + cmdout.toString()); 
                return cmdout.toString().trim(); 
        }

    public static void main(String[] args) throws Exception {
        String out = readConsole("jps -l", false);
//        for (int i=0; i<100; i++) {
////            System.out.println("i=" + i);
////            Thread.sleep(100);
////        }
        System.out.println(currentPid() );
        System.out.println("=========================");
        String out2 = readConsole("jps -l", true);
//        System.out.println(out);
    }
}