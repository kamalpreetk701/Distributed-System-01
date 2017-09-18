import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Manager_Thread extends Thread
{
	static String username = "";                                             //username of server manager

	static String locationname;

	String thread_name = "";

	Manager_Thread(String name)
	{
		thread_name = name;
	}


	public void run()                                                //client thread statrs execution
	{
		System.out.println(this.thread_name);
			try {
				locationname=thread_name.substring(0,3);
				System.out.println(locationname);
		if (locationname.equals("MTL"))                                //checks which server's manager logged in
			{
		System.out.println(this.thread_name+"inside if");				Registry reg;
				reg = LocateRegistry.getRegistry(2964);
				ServerInterface ms = (ServerInterface) reg.lookup("Mtl");  //looks for reference Mtl in registry for montreal server
				Operations m = new Operations();
				m.Operations1(ms,username);
			} else if (locationname.equals("LVL"))
			{
				Registry reg = LocateRegistry.getRegistry(2965);
				ServerInterface ls = (ServerInterface) reg.lookup("Lvl"); //looks for reference Lvl in registry for laval server
				Operations m = new Operations();
				m.Operations1(ls,username);//performs the required function
			} else if (locationname.equals("DDO"))
			{
				Registry reg = LocateRegistry.getRegistry(2966);
				ServerInterface ds = (ServerInterface) reg.lookup("Ddo");    //looks for reference Ddo in registry for DDO server
				Operations m = new Operations();
				m.Operations1(ds,username);                           //performs the required function
			}
		} catch (IOException | NotBoundException e1)
			{
			e1.printStackTrace();
			}
	}
}



