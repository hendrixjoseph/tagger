package com.joehxblog.tagger.android;

import com.joehxblog.mocked.MockSharedPreferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AffiliatePreferenceTest {
    private static final String TEST_TAG = "tag";

    private AffiliatePreference prefs;

    @BeforeEach
    void beforeEach() {
        this.prefs = new AffiliatePreference(new MockSharedPreferences());
    }

    @Test
    void testGetTags() {
        assertTrue(this.prefs.getTags().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testAddTag(final int count) {
        IntStream.range(0, count)
                .forEach(number -> this.prefs.addTag(TEST_TAG + number));

        assertEquals(count, this.prefs.getTags().size());
    }

    @Test
    void testAddTag_Contains() {
        this.prefs.addTag(TEST_TAG);

        assertTrue(this.prefs.getTags().contains(TEST_TAG));
    }

    @Test
    void testAddTag_NotContains() {
        assertFalse(this.prefs.getTags().contains(TEST_TAG));
    }

    @Test
    void testRemoveTag() {
        this.prefs.addTag(TEST_TAG);
        this.prefs.removeTag(TEST_TAG);

        assertEquals(0, this.prefs.getTags().size());
    }

    @Test
    void testReplaceTag() {
        this.prefs.addTag(TEST_TAG);
        this.prefs.replaceTag(TEST_TAG, TEST_TAG + 1);

        assertAll(
                () -> assertFalse(this.prefs.getTags().contains(TEST_TAG)),
                () -> assertTrue(this.prefs.getTags().contains(TEST_TAG + 1)),
                () -> assertEquals(1, this.prefs.getTags().size()));
    }

    @Test
    void testDefaultTag() {
        this.prefs.addTag(TEST_TAG);

        assertTrue(this.prefs.isDefaultTag(TEST_TAG));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testDefaultTag(final int count) {
        this.prefs.addTag(TEST_TAG);

        IntStream.range(0, count)
                .forEach(number -> this.prefs.addTag(TEST_TAG + number));

        assertAll(
                () -> assertTrue(this.prefs.isDefaultTag(TEST_TAG)),
                () -> assertTrue(IntStream.range(0, count).mapToObj(number -> TEST_TAG + number).noneMatch(this.prefs::isDefaultTag)));
    }
}