package DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entities.Evento;
import exceptions.NotFound;

public class EventoDAO {
    // E' un'astrazione, cioè, siccome le interazioni con il DB richiedono un po' di righe
    // di codice non particolarmente semplice, creiamo questa classe che fornirà dei metodi
    // semplici da usare nel main nascondendo queste complessità

    private final EntityManager entityManager;
    // Tutti i metodi di questo DAO avranno bisogno di utilizzare l'EntityManager poiché è l'oggetto che mi consente
    // di salvare, cancellare, leggere, sincronizzarmi col DB. Siccome l'oggetto entity manager viene creato nel main
    // è comodo passarlo come parametro del costruttore del DAO in maniera da avercelo già a disposizione in tutti i suoi metodi

    public EventoDAO(EntityManager em) {
        this.entityManager = em;
    }

    public void save(Evento newEvento) {
        // Entity Manager quando facciamo modifiche esige una transazione
        // 1. Creiamo una transazione
        EntityTransaction transaction = this.entityManager.getTransaction();
        // 2. Facciamo partire la transazione
        transaction.begin();
        // 3. Siccome newEvento non è MANAGED, per aggiungerlo all'elenco degli oggetti
        // monitorati (PersistenceContext) dobbiamo effettuare un'operazione di PERSIST
        this.entityManager.persist(newEvento);
        // 4. L'operazione di COMMIT sincronizza il PersistenceContext con il DB
        // Siccome in questo caso c'è un oggetto nuovo nel PC, creerà una nuova riga nella tabella eventi
        transaction.commit();
        // 5. Log di avvenuto salvataggio
        System.out.println("L'evento " + newEvento + " è stato salvato nel DB!");
    }

    public Evento getById(long id) {
        Evento fromDB = this.entityManager.find(Evento.class, id); // Se non trova niente mi torna NULL
        if (fromDB == null) throw new NotFound(id);
        return fromDB;
    }

    public void delete(long id) {
        // 1. Cerchiamo l'evento tramite id (riciclando getById che gestisce anche l'eccezione custom)
        Evento fromDB = this.getById(id);
        // 2. Creiamo una transazione
        EntityTransaction transaction = this.entityManager.getTransaction();
        // 3. Facciamo partire la transazione
        transaction.begin();
        // 4. Informiamo l'EntityManager che l'evento è da cancellare dal DB, tramite metodo .remove()
        this.entityManager.remove(fromDB);
        // 5. L'operazione di COMMIT sincronizza il PersistenceContext con il DB
        // Siccome in questo caso c'è un oggetto segnato da rimuovere, il DB cancellerà la riga corrispondente
        transaction.commit();
        // 6. Log di avvenuta cancellazione
        System.out.println("L'evento " + fromDB + " è stato rimosso dal DB!");
    }
}