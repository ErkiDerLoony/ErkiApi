package erki.api.util;

import erki.api.util.Log.Level;

public class LogTest {
    
    public static void main(String[] args) {
        Log.setLevel(Level.WARNING);
        Log.info("Hallo Welt!");
        Log.error(new Throwable(new Throwable("CauseError")));
        Log.debug("Debugging1.");
        Log.setLevelForClasses(Level.DEBUG, Test.class);
        Log.debug("Debugging2.");
        new LogTest().new Test();
    }
    
    class Test {
        
        public Test() {
            Log.info("Test class info.");
            Log.print("Test print.");
            Log.debug("Test class constructor.");
        }
    }
}
