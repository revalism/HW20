package ir.hospital.service.queuingService;

import ir.hospital.entity.Queuing;
import ir.hospital.utility.ApplicationContext;
import ir.hospital.utility.SessionFactoryProvider;
import org.hibernate.Session;

public class QueuingServiceImpl implements QueuingService {
    @Override
    public void save(Queuing queuing) {
        if (checkQueuing(queuing)) {

            saveExtracted(queuing);
        } else throw new RuntimeException("this time is not available");
    }



    @Override
    public void saveOrUpdate(Queuing queuing) {
        if (queuing.getId() == null) {
            if (checkQueuing(queuing))
                saveExtracted(queuing);
        } else {
            updateExtracted(queuing);
        }
    }



    @Override
    public void update(Queuing queuing) {
        updateExtracted(queuing);
    }

    @Override
    public Queuing findById(Long id) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            return ApplicationContext.getQUEUING_REPOSITORY().findById(session, id).orElseThrow();
        }
    }

    @Override
    public void delete(Queuing queuing) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                ApplicationContext.getQUEUING_REPOSITORY().delete(session, queuing);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean checkQueuing(Queuing queuing) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            return ApplicationContext.getQUEUING_REPOSITORY().checkQueuing(session, queuing);
        }
    }

    private void updateExtracted(Queuing queuing) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                ApplicationContext.getQUEUING_REPOSITORY().update(session, queuing);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    private void saveExtracted(Queuing queuing) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                ApplicationContext.getQUEUING_REPOSITORY().save(session, queuing);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean isExist(Long id) {
        try (Session session = SessionFactoryProvider.sessionFactory.openSession()) {
            return ApplicationContext.getQUEUING_REPOSITORY().isExist(session, id);
        }
    }
}
