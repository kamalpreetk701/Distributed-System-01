import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Manager_Thread2 extends Thread {
	static String username = "";                                             //username of server manager
	static String locationname;

	public void run()                                                //client thread statrs execution
	{
			Login l=new Login();
			try {
				username = l.user_login();
				locationname = username.substring(0, 3);
			if (locationname.equals("MTL"))                                //checks which server's manager logged in
			{
				Registry reg;
				reg = LocateRegistry.getRegistry(2964);
				ServerInterface ms = (ServerInterface) reg.lookup("Mtl");  //looks for reference Mtl in registry for montreal server

											                                   //performs the required function
				Operations_to_be_performed o=new Operations_to_be_performed();
				o.do_the_following(ms,username);
				//logger.info(Logger.class.getName());
			} else if (locationname.equals("LVL")) {
				Registry reg = LocateRegistry.getRegistry(2965);
				ServerInterface ls = (ServerInterface) reg.lookup("Lvl"); //looks for reference Lvl in registry for laval server
				Operations_to_be_performed o=new Operations_to_be_performed();
				o.do_the_following(ls,username);
			} else if (locationname.equals("DDO"))
			{
				Registry reg = LocateRegistry.getRegistry(2966);
				ServerInterface ds = (ServerInterface) reg.lookup("Ddo");    //looks for reference Ddo in registry for DDO server
				Operations_to_be_performed o=new Operations_to_be_performed();
				o.do_the_following(ds,username);                         //performs the required function
			}
		} catch (IOException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}
}



