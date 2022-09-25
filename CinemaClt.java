import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CinemaClt {
	private static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		String IP = args.length > 0 ? args[0] : "localhost";
		try {
			Icinema ObjClt =
				(Icinema) Naming.lookup("rmi://" + IP + ":1099/cinemaRMI");
			boolean quit = false;
			while (!quit) {
				System.out.print(
					"*** rmi://" + IP + ":1099/cinemaRMI ***\n\n" +
					"1. per cercare Attori in base al nome\n" +
					"2. per cercare Attori in base al paese\n" +
					"3. per cercare Film in base al nome\n" +
					"4. per cercare Film in base al anno\n\n" +
					"0. per Uscire\n"
				);
				int choice = in.nextInt();
				in.nextLine();
				switch (choice) {
					case 1: {
						System.out.print(
							"\n * Immetti Nome attore o una parte di esso: "
						);
						String strChoice = in.nextLine();
						System.out.print(
							String.valueOf(ObjClt.AttoriNome(strChoice)) + "\n"
						);
						break;
					}
					case 2: {
						System.out.print(
							"\n * Immetti Paese attore o una parte di esso: "
						);
						String strChoice = in.nextLine();
						System.out.print(
							String.valueOf(ObjClt.AttoriPaese(strChoice)) + "\n"
						);
						break;
					}
					case 3: {
						System.out.print(
							"\n * Immetti Nome film o una parte di esso: "
						);
						String strChoice = in.nextLine();
						System.out.print(
							String.valueOf(ObjClt.FilmTitolo(strChoice)) + "\n"
						);
						break;
					}
					case 4: {
						System.out.print("\n * Immetti Anno film: ");
						String strChoice = in.nextLine();
						System.out.print(
							String.valueOf(ObjClt.FilmAnno(strChoice)) + "\n"
						);
						break;
					}
					default: {
						String strChoice = "";
						System.out.print("\n * Exit...\n\n");
						quit = true;
					}
				}
			}
		}
		catch (MalformedURLException | NotBoundException | RemoteException e) {
			e.printStackTrace();
		}
	}
}