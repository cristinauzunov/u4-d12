package cristinauzunov;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.Evento;
import entities.TipoEvento;
import DAO.EventoDAO;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("u4d12");

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // 1. Creiamo l'EntityManager a partire dalla factory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 2. Creiamo il DAO passandogli l'EntityManager
        EventoDAO eventoDAO = new EventoDAO(entityManager);

        // 3. Test: SAVE
        Evento nuovoEvento = new Evento(
                "Concerto Demon Slayer Live",
                LocalDate.of(2026, 9, 15),
                "Concerto a tema con le musiche dell'anime",
                TipoEvento.PUBBLICO,
                200
        );
        eventoDAO.save(nuovoEvento);

        // 4. Test: GET BY ID
        Evento trovato = eventoDAO.getById(nuovoEvento.getId());
        System.out.println("Trovato: " + trovato);

        // 5. Test: DELETE
        eventoDAO.delete(nuovoEvento.getId());

        // 6. Chiudiamo le risorse
        entityManager.close();
        entityManagerFactory.close();
    }
}