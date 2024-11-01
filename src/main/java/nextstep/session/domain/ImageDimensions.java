package nextstep.session.domain;

public class ImageDimensions {
    private final static int MINIMUM_WIDTH = 300;
    private final static int MINIMUM_HEIGHT = 200;
    private final static double ASPECT_RATIO = 3.0 / 2.0;

    private final int width;
    private final int height;

    public ImageDimensions(final int width, final int height) {
        if (width < MINIMUM_WIDTH) {
            throw new IllegalArgumentException("width 는 최소" + MINIMUM_WIDTH + "px 이상이어야 합니다.");
        }

        if (height < MINIMUM_HEIGHT) {
            throw new IllegalArgumentException("height 는 최소 " + MINIMUM_HEIGHT + "px 이상이어야 합니다.");
        }

        if ((double) width / height != ASPECT_RATIO) {
            throw new IllegalArgumentException("3:2 비율의 이미지만 업로드가 가능합니다.");
        }

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
