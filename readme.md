# Cinema Project
## Introduzione
Questo progetto si pone l'obbiettivo di mostrare il funzionamento della tecnologia **RMI** di **Java** e della tecnologia **JDBC**.
## Funzionamento
Un file bash ```id183ovzRmiServerSideScript.sh``` avvia il progetto e verifica che client e server siano connessi alla medesima rete.
Il **lato server** si interfaccia, attraverso **JDBC**, al DBMS e quindi al DB.
Il Server fornisce quindi al **lato client** dei servizi.
Il **lato client**, attraverso attraverso un menu testuale propone una serie di funzioni di consultazioni e/o ricerca all'utente, quali:
- Attori in base alla corrispondenza parziale di nome e/o cognome
- Attori in base alla corrispondenza parziale del paese di origine
- Film in base alla corrispondenza parziale del nome
- Film in base al anno
## File
All'interno del repository sono disponibili i seguenti file:
- ```readme.md``` questo file!
- ```id183ovzRmiServerSideScript.sh``` script che avvia il progetto
- ```Icinema.java``` Interfaccia comune sia al lato server che client. Deve essere presente all'interno dei due file (vedi dopo)
- ```CinemaSvr.java```: gestisce l'interazione tra DBMS e fornisce i servizi al client. Tale file, assieme all'interfaccia, è importantissimo per la creazione del file ```CinemaSvr.jar```
- ```CinemaClt.java```: fornisce all'utente i dati della propria consultazione. Tale file, assieme all'interfaccia, è importantissimo per la creazione del file ```CinemaClt.jar```
## Istruzioni
Per l'avvio del progetto è necessario aver avviato il DBMS dopodiché è necessario disporre dei seguenti file:
- ```id183ovzRmiServerSideScript.sh``` script che avvia il progetto
- ```CinemaSvr.jar``` gestisce il lato server
- ```CinemaClt.jar``` gestisce il lato client
Ovviamente in questo repository non sono disponibili i file ```.jar``` ma soltanto i sorgenti in formato ```.java```.
È quindi necessario creare i file ```.jar``` a partire dai file sorgenti disponibili
### Creazione dei file jar
Per creae questa tipologia di file è necessario inanzitutto compilare i file java in bytecode (file ```.class```) attraverso il comando:
```bash
javac *.java
```
Dopodiché dividere i file in due cartelle:
- ```./CinemaSvr/Icinema.class```
- ```./CinemaSvr/CinemaSvr.class```
- ```./CinemaClt/Icinema.class```
- ```./CinemaSvr/CinemaSvr.class```
**NB**
I file ```./CinemaSvr/Icinema.class``` e ```./CinemaClt/Icinema.class``` sono la stessa cosa!
A questo punto portarsi in una delle due cartelle e dare il seguente comando
```bash
jar -cf <NomeCartella>.jar *.class
```
## Prerequisiti
Inolte tale codice necessita anche di:
- l'uso di **JDBC** del giusto connettore, nell'esempio è stato usato *mysql-j-connector*
	- il download del connettore mysql è disponibile [qui](https://dev.mysql.com/downloads/connector/j/)
	- è preferibile scaricarlo come ```platform-indipendent```
	- estrarre dal file compresso il file ```.jar``` e importarlo nel tuo progetto
- usare un DBMS:
	- nell'esempio è stato usato MySQL/MariaDB dbms
	- è più sembra usare [XAMPP](https://www.apachefriends.org/download.html)
- usare la giusta base di dati (```sakila.db```)
	- scaricare compresso di [```sakila```](https://dev.mysql.com/doc/index-other.html) da qui
	- estrarre dal file compresso solo i file:
		- ```sakila-schema.sql``` contiene lo schema delle diverse tabelle
		- ```sakila-data.sql``` contine la istanze delle diverse tabelle
	- Dopo aver scaricato il DB puoi consultare la guida [qui](https://dev.mysql.com/doc/sakila/en/sakila-installation.html) per importare il tuo db all'interno del DBMS
## Attenzione
Purtroppo la tecnologia **RMI** sembra deprecata.
Il codice è vecchio e quindi presenta alcuni errori di semplice risoluzione!