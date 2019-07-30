package chushka.repository;

import chushka.domain.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        this.entityManager =
                Persistence.createEntityManagerFactory("chushka").createEntityManager();
    }

    @Override
    public Product save(Product entity) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(entity);
        transaction.commit();

        return entity;
    }

    @Override
    public Product findById(String id) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        Product result = this.entityManager.createQuery("SELECT p FROM products p where p.id" +
                "= :id", Product.class).setParameter("id", id).getSingleResult();

        transaction.commit();

        return result;
    }

    @Override
    public List<Product> findAll() {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        List<Product> result =
                this.entityManager.createQuery("SELECT p FROM products p", Product.class).getResultList();
        transaction.commit();

        return result;
    }
}
