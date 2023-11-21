package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.infrastructure.mapper.UserMapper;

import java.util.Optional;

@AllArgsConstructor
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = """
                SELECT u
                            FROM UserJpaEntity u
                            WHERE u.email = :email
                """;
        TypedQuery<UserJpaEntity> query = em.createQuery(jpql, UserJpaEntity.class);
        query.setParameter("email", email);
        UserJpaEntity result = query.getSingleResult();
        return Optional.of(userMapper.userJpaEntityToUser(result));
    }

    @Override
    public UserJpaEntity save(UserJpaEntity userJpaEntity) {
        em.persist(userJpaEntity);
        return userJpaEntity;
    }
}
