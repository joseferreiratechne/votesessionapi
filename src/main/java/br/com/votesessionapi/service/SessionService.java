package br.com.votesessionapi.service;

import br.com.votesessionapi.model.Session;

public interface SessionService {

    Session openSession(Long topicId, Long duration);
}
