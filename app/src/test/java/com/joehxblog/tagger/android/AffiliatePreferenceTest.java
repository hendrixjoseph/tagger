package com.joehxblog.tagger.android;

import com.joehxblog.mocked.MockSharedPreferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffiliatePreferenceTest {
    private AffiliatePreference prefs;

    @BeforeEach
    void beforeEach() {
        this.prefs = new AffiliatePreference(new MockSharedPreferences());
    }

    @Test
    void testGetTags() {
        assertEquals(0, this.prefs.getTags().size());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testAddTag(final int count) {
        IntStream.range(0, count)
                .forEach(number -> this.prefs.addTag("tag" + number));

        assertEquals(count, this.prefs.getTags().size());
    }

    @Test
    void testRemoveTag() {
        this.prefs.addTag("tag");
        this.prefs.removeTag("tag");
        assertEquals(0, this.prefs.getTags().size());
    }
}