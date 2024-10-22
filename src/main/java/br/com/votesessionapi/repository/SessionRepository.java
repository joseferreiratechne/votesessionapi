package br.com.votesessionapi.repository;

import br.com.votesessionapi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByTopicIdAndIsOpenTrue(Long topicId);
}
