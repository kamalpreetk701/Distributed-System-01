//provides the implementation DDO server

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DDO_Server
{
	public static void main(String args[]) throws IOException
	{
		 	Logger logger;
	     	Handler fileHandler;
	     	SimpleFormatter plainText;
			logger = Logger.getLogger(Logger.class.getName());
	        logger.setUseParentHandlers(false);
	        fileHandler = new FileHandler("D:/JAVA/DSassg1/Ddolog.txt",true); //log file created in append mode

	        plainText = new SimpleFormatter();
	        fileHandler.setFormatter(plainText);
	        logger.addHandler(fileHandler);
		Server_Imp ds=new Server_Imp("DDO");  			//Server is started with server name
	try {
		Registry registry=LocateRegistry.createRegistry(ds.get_rmi_port());
		registry.bind("Ddo", ds);						//object ds is bind to Ddo reference
		System.out.println(" DDO Server statred...");
		logger.info("DDO Server started ");

	} catch (AlreadyBoundException e) 
	{
		e.printStackTrace();
	}
		logger.info("UDP Server started at port"+ds.get_udp_port());
		System.out.println("UDP Server started at port"+ds.get_udp_port());
	ds.UDPServer(ds.get_udp_port());						//starts the udp server



	}
}
