package dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Person;

public class DatabaseRepo {

	public EntityManagerFactory emFactory;
	public EntityManager entityManager;

	public DatabaseRepo(String persistenceUnitName) {
			emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		}

	public void close() {
		emFactory.close();
	}

	public Person createOrUpdate(Person entity) {

		try {
			entityManager = emFactory.createEntityManager();
			try {
				entityManager.getTransaction().begin();
				entityManager.persist(entity);
				entityManager.getTransaction().commit();
			} catch (Exception ex) {
				entityManager.getTransaction().rollback();
				entityManager.getTransaction().begin();
				entity = entityManager.merge(entity);
				entityManager.getTransaction().commit();
			}
			return entity;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			entityManager.getTransaction().rollback();
			return null;
		} finally {
			entityManager.close();
		}
	}
}
