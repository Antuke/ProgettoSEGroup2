package com.segroup2.progettosegroup2.Actions;

import java.awt.Desktop;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Action that opens a specified URL in the system's default browser.
 */
public class ActionOpenUrl implements ActionInterface, Serializable {
    private final String url;

    public ActionOpenUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        this.url = url;
    }

    @Override
    public boolean execute() throws RuntimeException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                return true;
            } catch (IOException | URISyntaxException e) {
                // Log or handle the error
                e.printStackTrace();
                return false;
            }
        } else {
            // Desktop or browse action not supported
            return false;
        }
    }

    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }

    @Override
    public String toString() {
        return "Open URL: " + url;
    }
}
