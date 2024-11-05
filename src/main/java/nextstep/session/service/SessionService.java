package nextstep.session.service;

import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionUserRepository;
import nextstep.session.domain.SessionUsers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionUserRepository")
    private SessionUserRepository sessionUserRepository;

    public Session fetchSession(final Long sessionId) {
        final Session session = sessionRepository.findById(sessionId);
        final SessionUsers sessionUsers = sessionUserRepository.findById(sessionId);
        session.addSessionUsers(sessionUsers);

        return session;
    }
}
