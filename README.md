# gamerHUB

### Premessa 
Questa repository 🗂️ contiene il ***back-end*** dell'applicazione ***gamerHUB*** 🎮. Quì troverai solo le informazioni per clonare il ***back-end*** correttamente, per avere più informazioni su l'intera app controllare la repository del [***front-end***](https://github.com/domenico2003/Capstone-Project-FE), detto questo proseguiamo...

## Clonazione back end
Per clonare correttamente il ***back-end*** seguire i seguenti passaggi:
***
1. **Clonare repository** 🖨️:
![image](https://github.com/domenico2003/Capstone-Project-BE/assets/121806951/fd6f8ce5-bc61-4af9-b784-612f27e1ee91)
Nella pagina principale di questa repository fare il download della  come indicato sopra.
***
2. **Aggiunta file** 📂: Aprire la repository con qualsiasi editor di testo e aggiungere un file a livelo di `pom.xml` chiamato esattamente `env.properties` come indicato in basso.⬇️

   ![image](https://github.com/domenico2003/Capstone-Project-BE/assets/121806951/790ba110-730f-4bcf-b35a-06ff1922cedb)
 ***
3. ***Aggiunta variabili***  :
Aggiungi le variabili nel file precedentemente creato con il valore scelto da te:
![variabili](https://github.com/domenico2003/Capstone-Project-BE/assets/121806951/6a871e92-b702-4881-9a21-ec8e3d47b0c9)

- `PG_PW=password`: in questa variabile inserisci la password di pgAdmin (no master). 

- `PG_USERNAME=username`: quà inserisci il tuo username di pg-admin.

- `PG_DB=nome database`: quà inserisci il nome del dataBase che creerai in futuro.

- `JWT_SECRET=chiave sicurezza`: quà inserisci la chiave di sicurezza che deve essere composta da numeri e lettere random più lunga è, più sicura è.

- `JWT_EXPIRATION=durata in giorni del token (consigliato 7 giorni)`: quà inserisci la durata del token, mettere un numero da 1 a 7, numeri più alti potrebbero rompere il codice.
***
4. ***Creazione Database***🗄️:
Creare database **pgAdmin** chiamato esattamente come nella variabile scritta sopra.
*** 
5. ***Avviare applicazione back-end*** ▶️:
Ora non ti resta che avviare l'applicazione e proseguire con i passaggi nel [***front-end***](https://github.com/domenico2003/Capstone-Project-FE), per qualsiasi cosa o problema contattatemi nella sezione dedicata della repository linkata in precedenza⬆️
  
