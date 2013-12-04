package de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.R;

public class WriteMessageActivity extends AbstractWriteMessageActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    super.onCreate(savedInstanceState);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.advanced_title_bar);
    
    TextView textView = (TextView) findViewById(R.id.advanced_title_bar_title);
    textView.setText(this.getTitle());
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu_write_message, menu);
    return true;
  }
  
  @Override
  protected ProgressBar getProgressBar() {
    return (ProgressBar) findViewById(R.id.advanced_title_bar_progress_bar);
  }
}
