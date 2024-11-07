package nextstep.session.domain;

import java.time.LocalDateTime;

public class SessionCoverImage {
    private final String name;
    private final ImageExtension extension;
    private final ImageDimensions dimensions;
    private final ImageSize size;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SessionCoverImage(final String name, final ImageExtension extension, final ImageDimensions dimensions, final ImageSize size) {
        this(name, extension, dimensions, size, LocalDateTime.now(), null);
    }

    public SessionCoverImage(final String name, final ImageExtension extension, final ImageDimensions dimensions, final ImageSize size, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.name = name;
        this.extension = extension;
        this.dimensions = dimensions;
        this.size = size;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public ImageExtension getExtension() {
        return extension;
    }

    public ImageDimensions getDimensions() {
        return dimensions;
    }

    public ImageSize getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
