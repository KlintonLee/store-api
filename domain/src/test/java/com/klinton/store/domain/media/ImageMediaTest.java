package com.klinton.store.domain.media;

import com.klinton.store.domain.aggregate.media.ImageMedia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageMediaTest {

    @Test
    public void givenValidParamsWithoutId_whenCallImageMediaWith_thenShouldReturnImageMediaInstance() {
        // Given
        var checksum = "checksum";
        var name = "name";
        var location = "location";

        // When
        var imageMedia = ImageMedia.with(checksum, name, location);

        // Then
        assertNotNull(imageMedia);
        assertNotNull(imageMedia.id());
        assertEquals(checksum, imageMedia.checksum());
        assertEquals(name, imageMedia.name());
        assertEquals(location, imageMedia.location());
    }

    @Test
    public void givenValidParamsWithId_whenCallImageMediaWith_thenShouldReturnImageMediaInstance() {
        // Given
        var id = "id";
        var checksum = "checksum";
        var name = "name";
        var location = "location";

        // When
        var imageMedia = ImageMedia.with(id, checksum, name, location);

        // Then
        assertNotNull(imageMedia);
        assertEquals(id, imageMedia.id());
        assertEquals(checksum, imageMedia.checksum());
        assertEquals(name, imageMedia.name());
        assertEquals(location, imageMedia.location());
    }
}
