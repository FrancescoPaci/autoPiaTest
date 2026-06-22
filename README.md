# autoPiaTest
Adeguamento dell'Anagrafica Radiologica

per tirate su l'applicazione ci sono due modi:

1) tirare su il FE e BE separatamente
    - da un terminale entrare nella cartella del fe gestione-apparecchiature
    - lanciare mpn start
    - fare maven update e clean package del be e poi lanciare il main di spring boot
    - l'applicazione risponde all'url http://localhost:4200/
2) tirare su solo il BE
    - fare maven update e clean package del be e poi lanciare il main di spring boot
    - l'applicazione risponde all'url http://localhost:8080/