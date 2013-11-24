package de.raptor2101.BattleWorldsKronos.Connector;

import de.raptor2101.BattleWorldsKronos.Connector.AbstractConnectorApp;
import de.raptor2101.BattleWorldsKronos.Connector.NotificationService;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities.GameListingActivity;

public class ConnectorApp extends AbstractConnectorApp {  
    @Override
    public void onCreate() {
      NotificationService.setResponseActivities(GameListingActivity.class, null);
      super.onCreate();
    }
}
