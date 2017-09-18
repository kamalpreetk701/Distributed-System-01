//Run the client thread

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Manager_With_Menu
{
	static void test_case1()
	{
		Login l=new Login();
		Manager_Thread2 c = new Manager_Thread2();            //run the new client thread
		c.start();
	}

	public static void main(String arg[]) throws RemoteException, NotBoundException
	{
	test_case1();
	}

}
