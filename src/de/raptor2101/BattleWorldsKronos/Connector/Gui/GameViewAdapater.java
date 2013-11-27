package de.raptor2101.BattleWorldsKronos.Connector.Gui;

import de.raptor2101.BattleWorldsKronos.Connector.Gui.AbstractGameViewAdapater;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.IGameView;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.Controls.GameView;
import android.content.Context;

public class GameViewAdapater extends AbstractGameViewAdapater{

  public GameViewAdapater(Context context) {
    super(context);
  }

  @Override
  protected IGameView createGameView(Context context) {
    return new GameView(context);
  }

}
