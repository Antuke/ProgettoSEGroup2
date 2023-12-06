package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.layout.VBox;

public interface RenderTrigger {
    /**
     * Aggiunge a {@code parent} tutti gli elementi necessari
     * @throws NullPointerException Se il parametro è nullo
     */
    void render(VBox parent);

    /**
     * @return Il trigger scelto dall'utente tramite l'oggetto Render utilizzato.
     * Il valore restituito potrebbe essere nullo
     */
    TriggerInterface getTriggerInterface();
}
