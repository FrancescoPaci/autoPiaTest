# autoPiaTest
Adeguamento dell'Anagrafica Radiologica

per tirate su l'applicazione c'è un modo abilitato:

1) tirare su il FE e BE separatamente
    - da un terminale entrare nella cartella del fe gestione-apparecchiature
    - lanciare npm install
    - lanciare mpn start
    - fare maven update
    - fare clean e package del be
    - lanciare il main di spring boot
    - l'applicazione risponde all'url http://localhost:4200/

2) tirare su solo il BE (il be builda il fe e lo mette nella cartella dist)
    - settare nel pom la var frontend.skip a false
    - fare maven update
    - fare clean e package del be
    - lanciare il main di spring boot
    - l'applicazione risponde all'url http://localhost:8080/

Ci sono due utenti, uno con ruolo admin (admin/admin) e uno con ruolo user (user/user)