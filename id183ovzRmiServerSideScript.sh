#!/bin/bash

# pulisco lo schermo
clear

if [ $# -eq "0" ]
then
	MIOPATH="./"
else
	MIOPATH=$1
fi

# Controllo se sono ROOT, per usare meccanismi di sudo
if [ whoami != "root" ]
then
	COMMANDSU="gksudo"
fi

# faccio partire o ripartire apache, mysql... HO BISOGNO DEI PERMESSI DI ROOT
for myservice in {apache,mysql}
do
	echo "provo ad avviare $myservice..."
	$COMMANDSU /opt/lampp/xampp start$myservice 2>/dev/null
	echo
done


# Estraggo IP e verifico quindi se sono connesso (non sono connesso se IP = 127.0.0.1)
# mioIP=$(ifconfig | grep Ethernet -A 1 | grep inet: | tail -n 1 | cut -d: -f 2 | cut -d" " -f 1)
mioIP=$(ifconfig | grep inet: | tail -n 1 | cut -d: -f 2 | cut -d" " -f 1)

if [ $mioIP != "127.0.0.1" ]
then
	echo "Connesso con successo, IP: $mioIP"
else
	echo "Nessuna connessione attiva"
fi

# Richiesta di apertura di un terminale per i comandi Client Side
echo
read -p "Vuoi avviare un terminale per i comandi RMI Client-Side (y/n)? " risp
case "$risp" in 
  s|S|y|Y ) lxterminal&;;
  n|N|* ) echo "...";;
esac

# passo l'IP come parametro al main del jar che a sua volta lo passa al costruttore
echo
echo "Begin RMI Server Side Session on $mioIP, press CTRL-C to STOP"
java -Djava.rmi.server.hostname=$mioIP -jar $MIOPATH*.jar $mioIP 3306

# Per il reboot dello script
echo
read -p "Vuoi riavviare lo script (y/n)? " risp
case "$risp" in 
  s|S|y|Y ) $0 $1;;
  n|N|* )
	echo "Spengo i servizi lampp precedentemente avviati,"
	echo "Cortesemente fornire le autorizzazione necessarie!"
	$COMMANDSU /opt/lampp/xampp stop 2>/dev/null;;
esac

exit
