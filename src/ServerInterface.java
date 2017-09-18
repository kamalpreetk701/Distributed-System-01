//Provides the remote interface for RMI server

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServerInterface extends Remote
{
	Boolean createTRecord(String f_name, String l_name, String addr, String number, String spec, String loc) throws RemoteException;
	Boolean createSRecord(String f_name, String l_name, String[] courses, String status, Date dat) throws RemoteException;
	String[] getRecordCount() throws RemoteException;
	String display() throws RemoteException;
	Boolean edit(String id, String field_name, String value) throws RemoteException;
	Boolean edit(String id, String field_name, String[] value) throws RemoteException;
	void user_details(String username) throws RemoteException;
}
