package com.ly.liugw.app;

/**
 * java -javaagent:demo.jar  -Xbootclasspath/a:C:/app/Java/jdk1.8.0_91/jre/../lib/tools.jar com.ly.liugw.app.Hello
 * java -javaagent:demo.jar  -Xbootclasspath/a:tools.jar -classpath C:/app/Java/jdk1.8.0_91/lib;/e/dev/git/demo/agenttest/target/classes com.ly.liugw.app.Hello
 *
 * /c/app/Java/jdk1.8.0_91/jre/../lib/tools.jar
 *         at sun.instrument.TransformerManager.transform(Unknown Source)
 *         at sun.instrument.InstrumentationImpl.transform(Unknown Source)
 *         at sun.launcher.LauncherHelper.checkAndLoadMain(Unknown Source)
 * ======================= main ======
 */
public class Hello {
    public static void main(String[] args) {
        System.out.println("======================= main ======");
    }
}
