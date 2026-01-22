package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionOpenUrlTest {

    @Test
    void testConstructorValidUrl() {
        ActionOpenUrl action = new ActionOpenUrl("https://www.google.com");
        assertNotNull(action);
        assertEquals("Open URL: https://www.google.com", action.toString());
    }

    @Test
    void testConstructorNullUrl() {
        assertThrows(IllegalArgumentException.class, () -> new ActionOpenUrl(null));
    }

    @Test
    void testConstructorEmptyUrl() {
        assertThrows(IllegalArgumentException.class, () -> new ActionOpenUrl(""));
        assertThrows(IllegalArgumentException.class, () -> new ActionOpenUrl("   "));
    }

    @Test
    void testAddReturnsFalse() {
        ActionOpenUrl action = new ActionOpenUrl("https://example.com");
        assertFalse(action.add(new ActionOpenUrl("https://other.com")));
    }

    @Test
    void testRemoveReturnsFalse() {
        ActionOpenUrl action = new ActionOpenUrl("https://example.com");
        assertFalse(action.remove(new ActionOpenUrl("https://other.com")));
    }
}
