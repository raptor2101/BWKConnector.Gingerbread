package de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities;

import android.os.Bundle;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.R;

public class GameListingActivity extends AbstractGameListingActivity implements OnItemClickListener{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    super.onCreate(savedInstanceState);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.advanced_title_bar);
  }  

  @Override
  protected ProgressBar GetProgressBar() {
    return (ProgressBar) findViewById(R.id.advanced_title_progress_bar);
  }
}
