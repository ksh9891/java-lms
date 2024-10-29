package nextstep.session.domain;

public class SessionCoverImage {
    private final String name;
    private final ImageExtension extension;
    private final ImageDimensions dimensions;
    private final ImageSize size;

    public SessionCoverImage(final String name, final ImageExtension extension, final ImageDimensions dimensions, final ImageSize size) {
        this.name = name;
        this.extension = extension;
        this.dimensions = dimensions;
        this.size = size;
    }
}
