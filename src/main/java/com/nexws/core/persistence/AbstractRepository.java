package com.nexws.core.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public abstract class AbstractRepository<E extends AbstractModel> {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(AbstractRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	public E createOrUpdate(E entity) throws RepositoryException {

		try {

			E returnObj = this.entityManager.merge(entity);

			return returnObj;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	public void delete(E entity) throws RepositoryException {
		try {

			E newEntity = this.entityManager.find(
					this.getGenericClass(), entity.getId());

			this.entityManager.remove(newEntity);
			this.entityManager.getEntityManagerFactory().getCache().evictAll(); // limpa o cache do hibernate

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	public E retrieveById(Long id) {
		List<E> entityList = this.retrieve(Restrictions.eq("id", id));

		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
		E entity = entityList.get(0);
		return entity;
	}

	public List<E> retrieveByProperty(String property, Object value) {
		return this.retrieve(Restrictions.eq(property, value));
	}

	@SuppressWarnings("unchecked")
	public List<E> retrieve(Criterion... criterions) {

		Session session = (Session) this.entityManager.getDelegate();
		Criteria criteria = session.createCriteria(this.getGenericClass());

		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					criteria.add(criterion);
				}
			}
		}

		return criteria.list();
	}

	/**
	 * Retorna a classe em processamento atual, exemplo se eu estou dando
	 * um create or update de um User, entao retorna User.class
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getGenericClass() {
		return (Class<E>) ((ParameterizedType)
				this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

}