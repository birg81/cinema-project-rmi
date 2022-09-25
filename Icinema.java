import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Icinema extends Remote {
	public String AttoriNome(String var1) throws RemoteException;
	public String AttoriPaese(String var1) throws RemoteException;
	public String FilmTitolo(String var1) throws RemoteException;
	public String FilmAnno(String var1) throws RemoteException;
}