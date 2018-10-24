package dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Person;

public class DatabaseRepo {

	public EntityManagerFactory emFactory;
	public EntityManager entityManager;

	//creaaza un factory de Entity Manager
	public DatabaseRepo(String persistenceUnitName) {
			emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		}

	public void close() {
		emFactory.close();
	}

	//Metoda ce insereaza sau creeaza o entitate de tip persoana
	public void createOrUpdate(Person personToBeInserted) {

		try {
			entityManager = emFactory.createEntityManager(); //preia un entity manager din factory
			try {
				entityManager.getTransaction().begin(); //se porneste o tranzactie
				
				//intre getTransaction.begin si commit, daca da o eroare intre ele, nici una dintre operatii nu se vor commite
				
				entityManager.persist(personToBeInserted); //persista in baza de date o persoana
				entityManager.getTransaction().commit(); //se commite o tranzactie
			} catch (Exception ex) { //daca tranzactia crapa
				entityManager.getTransaction().rollback();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close(); //inchidem entityManager
		}
	}
}
