package com.joehxblog.tagger.android;

import com.joehxblog.mocked.MockSharedPreferences;
import com.joehxblog.tagger.History;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryPreferenceTest {

    private static final String TEST_TITLE = "TITLE";
    private static final String TEST_URL = "URL";

    private HistoryPreference prefs;

    @BeforeEach
    void beforeEach() {
        this.prefs = new HistoryPreference(new MockSharedPreferences());
    }

    @Test
    void testGetHistory_Empty() {
        assertEquals(0, this.prefs.getHistory().size());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testCreateHistoryItem(final int count) {
        IntStream.range(0, count)
                .forEach(number -> this.prefs.createHistoryItem(TEST_TITLE + number, TEST_URL + number));

        assertEquals(count, this.prefs.getHistory().size());
    }

    @Test
    void testClearHistory() {
        this.prefs.createHistoryItem(TEST_TITLE,TEST_URL);
        this.prefs.clearHistory();

        assertEquals(0, this.prefs.getHistory().size());
    }

    @Test
    void testCreateHistoryItem_Same() {
        this.prefs.createHistoryItem(TEST_TITLE,TEST_URL);
        final History history = this.prefs.getHistory().get(0);

        assertEquals(TEST_TITLE, history.getTitle());
        assertEquals(TEST_URL, history.getUrl());
    }
}