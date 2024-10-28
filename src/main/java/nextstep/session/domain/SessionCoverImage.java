package nextstep.session.domain;

public class SessionCoverImage {
    private final String name;
    private final ImageExtension extension;
    private final ImageProperties properties;
    private final ImageSize size;

    public SessionCoverImage(final String name, final ImageExtension extension, final ImageProperties properties, final ImageSize size) {
        this.name = name;
        this.extension = extension;
        this.properties = properties;
        this.size = size;
    }
}
