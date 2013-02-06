/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.classloader;

import java.lang.reflect.*;

import swingwtx.swing.SwingWTUtils;

/**
 * Bootstrap class - uses our special classloader that can switch real
 * AWT/Swing for SwingWT and then starts the program.
 *
 * This uses code from the excellent WebOnSwing (http://webonswing.sf.net)
 * project, and the Apache BCEL (ByteCode Engineering Library)
 *
 */
public class SwingWTBootstrap {

    public static void main(final String[] args) {
        printCopyright();

        try {
            if (args == null || args.length == 0 || args.length > 1) {
                printUsage();
                System.exit(1);
            }

            if (args[0].equals("--help") || args[0].equals("-?")) {
                printHelp();
                System.exit(1);
            }

            // When you create an instance, it becomes
            // the system class loader.
            final SwingWTClassLoader loader = SwingWTClassLoader.getInstance();
            
    		Runnable realMain = new Runnable(){
    			public void run() {
    				try {
    		           Class startup = loader.loadClass(args[0]);
    		            Method m = startup.getMethod("main", new Class[] { String[].class } );
    		            
    		            // Strip off the first argument and pass the remainder on to the
    		            // calling application if some exist
    		            String[] newargs = null;
    		            if (args.length > 1) {
    		                newargs = new String[args.length - 1];
    		                for (int i = 1; i < args.length; i++) {
    		                    newargs[i-1] = args[i];
    		                }
    		            }
    		            else {
    		                newargs = new String[] { };
    		            }
    		            
    		            m.invoke(null, new Object[] { newargs } );
    				} catch (Throwable t) {
    					t.printStackTrace();
    				}
    			}
    		};
    		
    		if (SwingWTUtils.isMacOSX()) {
    			SwingWTUtils.initialiseMacOSX(realMain);
    		}
    		else {
    			realMain.run();
    		}

            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	
    }

    public static void printUsage() {
        System.out.println("Usage: ");
	System.out.println("    swingwtbootstrap classpath startupclass");
	System.out.println("");
	System.out.println("    Where classpath is an additional classpath containing your program");
	System.out.println("    and startupclass is the fully qualified java package/class of the");
	System.out.println("    program's class containing the main(String[] args) method");
    }

    public static void printCopyright() {
	    
        System.out.println("SwingWT BootStrap, Copyright(c)2004 R.Rawson-Tetley");
	System.out.println("This program uses classloading code from the ");
	System.out.println("WebOnSwing (http://webonswing.sourceforge.net) and Apache BCEL projects.\n");
	System.out.println("This program is distributed in the hope that it will be useful,\n" +
			   "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
			   "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU\n" +
                           "Lesser General Public Licence for more details.");
    }

    public static void printHelp() {
        System.out.println("swingwtbootstrap: Run existing Swing and AWT programs, dynamically");
	System.out.println("switching AWT/Swing for SwingWT on the fly.");
	System.out.println("");
	printUsage();
    }

}
