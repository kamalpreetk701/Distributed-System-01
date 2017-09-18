//Provides the implementation of Laval Server

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Laval_Server
{
	public static void main(String args[]) throws IOException
	{	 Logger logger;
	     Handler fileHandler;
	     SimpleFormatter plainText;
	     logger = Logger.getLogger(Logger.class.getName());		//Instance of Logger class is created
	        logger.setUseParentHandlers(false);
	        fileHandler = new FileHandler("D:/JAVA/DSassg1/Lavallog.txt",true);//Log file opened in append mode
			plainText = new SimpleFormatter();
	        fileHandler.setFormatter(plainText);
	        logger.addHandler(fileHandler);
	
	        Server_Imp ls=new Server_Imp("LVL");						//RMi server intialized
	
	try {
		Registry registry=LocateRegistry.createRegistry(ls.get_rmi_port());
		registry.bind("Lvl", ls);									//object reference binding
		System.out.println("Laval Server statred...");
		logger.info("Laval Server started ");
		}
		catch (AlreadyBoundException e)
		{
			e.printStackTrace();
		}
		logger.info("UDP Server started "+System.currentTimeMillis()+"at port"+ls.get_udp_port());
		System.out.println("UDP Server started at port"+ls.get_udp_port());

	ls.UDPServer(ls.get_udp_port());								//udp server is started


	}
}
