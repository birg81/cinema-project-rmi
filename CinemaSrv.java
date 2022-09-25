import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CinemaSrv extends UnicastRemoteObject implements Icinema {
	private static String DB;
	private static String IP;
	private static String SQLport;
	private static Connection conn;
	private static Statement stat;
	private static ResultSet rs;
	static {
		conn = null;
		stat = null;
		rs = null;
	}
	public CinemaSrv() throws RemoteException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.print("Trovato Driver JDBC per la connessione MySQL\n");
		}
		catch (ClassNotFoundException e) {
			System.out.print(
				"Non trovato il Drive JDBC per la connessione con MySQL\n"
			);
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:" + SQLport + "/" + DB, "root", ""
			);
			System.out.print(
				"Connesso con MySQL\nPorta: " + SQLport + "\n" +
				"Base Dati: " + DB + "\n"
			);
			stat = conn.createStatement();
		}
		catch (SQLException e) {
			System.out.print(
				"Problemi con la connessione\n" +
				"Probabile causa: \nPorta: " + SQLport + " non Valida\n" +
				"Base Dati " + DB + " inesistente!\n"
			);
			e.printStackTrace();
		}
	}
	protected void finalize() {
		try {
			rs.close();
			stat.close();
			conn.close();
			System.out.print("Risorse MySQL rilasciate\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String AttoriNome(String NomeCognome) {
		String strResult = "";
		try {
			rs = stat.executeQuery(
				"SELECT "+
					"DISTINCT (CONCAT(v.Name, ' ', v.Surname)) as Nome, " +
					"v.DateBorn as dt, " +
					"c.nameCountry as Paese " +
				"FROM " +
						"(VIP v JOIN CastMovie a ON a.mCodVIP = v.codVIP) " +
					"JOIN " +
						"CountryType c ON v.cCountry = c.codCountry " +
				"WHERE "+
					"CONCAT(v.Name, ' ', v.Surname) LIKE '%" +
						NomeCognome.toLowerCase() +
					"%';"
			);
			while (rs.next()) {
				String[] dt = rs.getString("dt").split("-");
				strResult =
					String.valueOf(strResult) +
					rs.getString("Nome") + "\t" +
					dt[2] + "/" + dt[1] + "/" + dt[0] + "\t" +
					rs.getString("paese") + "\n";
			}
			System.out.print("* AttoriNome(" + NomeCognome + ")\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	@Override
	public String AttoriPaese(String Paese) {
		String strResult = "";
		try {
			rs = stat.executeQuery(
				"SELECT " +
					"DISTINCT (CONCAT(v.Name, ' ', v.Surname)) as Nome, "+
					"v.DateBorn as dt, " +
					"c.nameCountry as Paese "+
				"FROM "+
						"(VIP v JOIN CastMovie a ON a.mCodVIP = v.codVIP) "+
					"JOIN " +
						"CountryType c " +
					"ON v.cCountry = c.codCountry "+
				"WHERE "+
					"c.codCountry = '" + Paese.toUpperCase() + "' OR " +
					"c.nameCountry LIKE '%" + Paese.toLowerCase() + "%';"
				);
			while (rs.next()) {
				String[] dt = rs.getString("dt").split("-");
				strResult =
					String.valueOf(strResult) +
					rs.getString("Nome") + "\t" +
					dt[2] + "/" + dt[1] + "/" + dt[0] + "\t" +
					rs.getString("paese") + "\n";
			}
			System.out.print("* AttoriPaese(" + Paese + ")\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	@Override
	public String FilmTitolo(String title) {
		String strResult = "";
		try {
			rs = stat.executeQuery(
				"SELECT "+
					"titleORIG, titleLOC, durata as min, annoProd as dt " +
				"FROM " +
					"Movies " +
				"WHERE " +
					"titleORIG LIKE '%" + title.toLowerCase() + "%' OR " +
					"titleLOC LIKE '%" + title.toLowerCase() + "%';"
			);
			while (rs.next()) {
				String dt = rs.getString("dt").split("-")[0];
				int min = rs.getInt("min");
				strResult =
					String.valueOf(strResult) + (
						rs.getString("titleLOC") != null
							? rs.getString("titleLOC")
							: rs.getString("titleORIG")
					) + "\t" +
					(min - min % 60) / 60 + "h:" +
					min % 60 + "':00\"\t" +
					dt +
					"\n *** " + rs.getString("titleORIG") + " ***\n\n";
			}
			System.out.print("* FilmTitolo(" + title + ")\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	@Override
	public String FilmAnno(String Anno) {
		String strResult = "";
		try {
			rs = stat.executeQuery(
				"SELECT titleORIG, titleLOC, durata as min, annoProd as dt " +
				"FROM Movies "+
				"WHERE annoProd = " + Anno + ";"
			);
			while (rs.next()) {
				String dt = rs.getString("dt").split("-")[0];
				int min = rs.getInt("min");
				strResult =
					String.valueOf(strResult) + (
						rs.getString("titleLOC") != null
							? rs.getString("titleLOC")
							: rs.getString("titleORIG")
						) + "\t" +
						(min - min % 60) / 60 + "h:" +
						min % 60 + "':00\"\t" + dt +
						"\n *** " + rs.getString("titleORIG") + " ***\n\n";
			}
			System.out.print("* FilmAnno(" + Anno + ")\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}
	public static void main(String[] args) throws RemoteException {
		DB = "HomeCinema";
		IP = args.length > 0 ? args[0] : "localhost";
		SQLport = args.length > 1 ? args[1] : "3306";
		try {
			LocateRegistry.createRegistry(1099);
			System.out.println("Java rmiregistry creato");
		}
		catch (RemoteException e) {
			LocateRegistry.getRegistry();
			e.printStackTrace();
			System.out.println("Java rmiregistry creato precedentemete");
		}
		try {
			CinemaSrv ObjSrv = new CinemaSrv();
			Naming.rebind("rmi://" + IP + ":1099/cinemaRMI", ObjSrv);
			System.out.print("Creato Binding\nIP: " + IP + "\n");
		}
		catch (MalformedURLException | RemoteException e) {
			e.printStackTrace();
		}
	}
}