package controllers;

import java.io.IOException;

/**
 * Interface for list controllers (Controllers that control a JFXListView).
 */
public interface ListController {
    void refreshData() throws IOException;
}
