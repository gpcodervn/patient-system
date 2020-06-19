package com.gpcoder.repository.impl;

import com.gpcoder.repository.BaseRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Repository with soft delete features
 * @param <T> The type of entity
 * @param <ID> The primary key of entity
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static final String DELETED_FIELD = "softDeleted";
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final Class<T> domainClass;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.domainClass = domainClass;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
    }

    @Override
    public int softDelete(ID id) {
        Assert.notNull(id, "The given id must not be null!");

        T entity = findById(id).orElseThrow(() -> new NoResultException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id)));

        return softDelete(entity);
    }

    @Override
    public int softDelete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUpdate<T> update = cb.createCriteriaUpdate(domainClass);

        Root<T> root = update.from(domainClass);

        update.set(DELETED_FIELD, true);

        ByIdSpecification<T, ID> specification = new ByIdSpecification<T, ID>(entityInformation, (ID) entityInformation.getId(entity));
        update.where(specification.toPredicate(root, null, cb));

        return em.createQuery(update).executeUpdate();
    }

    /**
     * Convert all conditions of the given entity information to Specification
     * @param <T>
     * @param <ID>
     */
    private static final class ByIdSpecification<T, ID extends Serializable> implements Specification<T> {

        private final JpaEntityInformation<T, ?> entityInformation;
        private final ID id;

        ByIdSpecification(JpaEntityInformation<T, ?> entityInformation, ID id) {
            this.entityInformation = entityInformation;
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            final List<Predicate> predicates = new ArrayList<>();
            if (entityInformation.hasCompositeId()) {
                for (String s : entityInformation.getIdAttributeNames()) {
                    predicates.add(cb.equal(root.<ID>get(s), entityInformation.getCompositeIdAttributeValue(id, s)));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            return cb.equal(root.<ID>get(entityInformation.getIdAttribute().getName()), id);
        }
    }
}
