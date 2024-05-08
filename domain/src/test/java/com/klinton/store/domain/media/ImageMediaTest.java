package com.klinton.store.domain.media;

import com.klinton.store.domain.aggregate.media.ImageMedia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageMediaTest {

    private static final String PRODUCT_ID = "productId";

    private static final String LOCATION = "location";

    private static final String CONTENT_TYPE = "image/jpeg";

    @Test
    public void givenValidParamsWithoutId_whenCallImageMediaWith_thenShouldReturnImageMediaInstance() {
        // When
        var imageMedia = ImageMedia.with(PRODUCT_ID, LOCATION, CONTENT_TYPE);

        // Then
        assertNotNull(imageMedia);
        assertNotNull(imageMedia.id());
        assertEquals(PRODUCT_ID, imageMedia.productId());
        assertEquals(LOCATION, imageMedia.location());
        assertEquals(CONTENT_TYPE, imageMedia.contentType());
    }

    @Test
    public void givenValidParamsWithId_whenCallImageMediaWith_thenShouldReturnImageMediaInstance() {
        // Given
        var id = "imageId";

        // When
        var imageMedia = ImageMedia.with(id, PRODUCT_ID, LOCATION, CONTENT_TYPE);

        // Then
        assertNotNull(imageMedia);
        assertEquals(id, imageMedia.id());
        assertEquals(PRODUCT_ID, imageMedia.productId());
        assertEquals(LOCATION, imageMedia.location());
        assertEquals(CONTENT_TYPE, imageMedia.contentType());
    }
}
