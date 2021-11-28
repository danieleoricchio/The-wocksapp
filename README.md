# The Wocksapp
### Chat peer to peer in java.
--------------------------

Questa chat permette la comunicazione tra due client seguendo un protocollo predefinito diviso in 3 fasi:
```
 1. Fase di apertura, divisa anch'essa divisa in 3 fasi:
   - Fase 1, dove un client manda una richiesta di apertura contenente la seguente stringa: "a;NOME_MITTENTE".
   - Fase 2, dove il client che riceve la richiesta invia "y;NOME_DESTINATARIO" se vuole accettare la richiesta,
     altrimenti invia "n;".
   - Fase 3, il mittente, dopo aver ricevuto la conferma o il rifiuto di connessione, manda nuovamente "y;" o "n;".
```
```
 2. Fase di dialogo, dove un client invia "m;CONTENUTO_MESSAGGIO" al destinatario.   
```
```
 3. Fase di chiusura, nella quale un client, tramite l'utilizzo di un bottone invia "c;" 
    per disconnettersi dall'altro client.
```
