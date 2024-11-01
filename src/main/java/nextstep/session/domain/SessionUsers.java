package nextstep.session.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionUsers {
    private final List<SessionUser> users;

    public SessionUsers() {
        this(new ArrayList<>());
    }

    public SessionUsers(final List<SessionUser> users) {
        this.users = users;
    }

    public int size() {
        return users.size();
    }

    public void add(final SessionUser user) {
        users.add(user);
    }

    public boolean contains(final SessionUser sessionUser) {
        return users.stream()
            .anyMatch(user -> user.matchSessionUser(sessionUser));
    }
}
