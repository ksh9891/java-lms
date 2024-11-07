package nextstep.session.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionCoverImages {
    private final List<SessionCoverImage> coverImages;

    public SessionCoverImages() {
        this.coverImages = new ArrayList<>();
    }

    public SessionCoverImages(final List<SessionCoverImage> coverImages) {
        this.coverImages = coverImages;
    }

    public void add(final SessionCoverImage coverImage) {
        this.coverImages.add(coverImage);
    }
}
