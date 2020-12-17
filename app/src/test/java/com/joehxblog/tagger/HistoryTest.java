package com.joehxblog.tagger;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryTest {

    private static final String TEST_TITLE = "title";
    private static final String TEST_URL = "url";

    @Test
    void testJsonConstructor() throws JSONException {
        final History history = new History(TEST_TITLE, TEST_URL);
        final History fromJsonHistory = new History(history.toString());

        assertAll(() -> assertEquals(TEST_TITLE, fromJsonHistory.getTitle()),
                  () -> assertEquals(TEST_URL, fromJsonHistory.getUrl()));
    }

    @Test
    void testComparison() throws InterruptedException {
        final History history = new History(TEST_TITLE, TEST_URL);
        Thread.sleep(10);
        final History history1 = new History(TEST_TITLE, TEST_URL);

        assertCompare(history1, history);
    }

    void assertCompare(final Comparable first, final Comparable second) {
        assertAll(() -> assertTrue(first.compareTo(second) < 0),
                  () -> assertTrue(second.compareTo(first) > 0));
    }
}