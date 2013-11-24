package de.raptor2101.BattleWorldsKronos.Connector.Gui.Controls;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.IGameInfoView;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.R;
import de.raptor2101.BattleWorldsKronos.Connector.JSON.GameInfo;
import de.raptor2101.BattleWorldsKronos.Connector.JSON.PlayerInfo;

public class GameInfoView extends LinearLayout implements IGameInfoView {

  private SimpleDateFormat mFormater;

  public GameInfoView(Context context) {
    super(context);
    mFormater = new SimpleDateFormat(context.getString(R.string.date_format_string), Locale.getDefault());
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.game_info_view, this);

  }

  public void setGameInfo(GameInfo gameInfo) {
    GameInfo.State gameState = gameInfo.getState();

    TextView textView = (TextView) this.findViewById(R.id.text_game_info_view_Title);
    textView.setText(gameInfo.getGameName());

    textView = (TextView) this.findViewById(R.id.text_game_info_view_State);
    textView.setText(gameState.getResourceId());
    textView.setTextColor(gameState.getColor(this.getContext()));

    textView = (TextView) this.findViewById(R.id.text_game_info_view_Round);
    textView.setText(String.format("%d", gameInfo.getCurrentRound()));

    textView = (TextView) this.findViewById(R.id.text_game_info_view_PlayerCount);
    textView.setText(String.format("%d", gameInfo.getPlayers().size()));

    textView = (TextView) this.findViewById(R.id.text_game_info_view_Date);
    textView.setText(String.format("%d", gameInfo.getPlayers().size()));

    textView = (TextView) this.findViewById(R.id.text_game_info_view_Date);
    textView.setText(mFormater.format(gameInfo.getUpdateDate()));

    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game_info_view_PlayerInfo);
    layout.removeAllViews();
    SparseArray<PlayerInfo> players = gameInfo.getPlayers();

    textView = (TextView) this.findViewById(R.id.text_game_info_view_PlayerCount);
    textView.setText(String.format("%d", players.size()));

    for (int i = 0; i < players.size(); i++) {
      PlayerInfoView view = new PlayerInfoView(getContext());
      view.setPlayerInfo(players.valueAt(i));
      layout.addView(view);
    }
  }

  public boolean isExpanded() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game_info_view_PlayerBox);
    return layout.getVisibility() == View.VISIBLE;
  }

  public void collapse() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game_info_view_PlayerBox);
    layout.setVisibility(View.GONE);
  }

  public void expand() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_game_info_view_PlayerBox);
    layout.setVisibility(View.VISIBLE);
  }

}
