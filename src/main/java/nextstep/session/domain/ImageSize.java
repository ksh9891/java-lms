package nextstep.session.domain;

import java.util.Objects;

public class ImageSize {
    private static final long LIMIT_SIZE = 1024L;

    private final long size;

    public ImageSize(long size) {
        if (size > LIMIT_SIZE) {
            throw new IllegalArgumentException("이미지가 너무 큽니다.");
        }
        this.size = size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ImageSize imageSize = (ImageSize) o;
        return size == imageSize.size;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(size);
    }
}
