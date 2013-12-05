package de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.raptor2101.BattleWorldsKronos.Connector.Data.Entities.Message;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.R;

public class MessageListingActivity extends AbstractMessageListingActivity{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    super.onCreate(savedInstanceState);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.advanced_title_bar);
    
    TextView textView = (TextView) findViewById(R.id.advanced_title_bar_title);
    textView.setText(this.getTitle());
  }  

  @Override
  protected ProgressBar getProgressBar() {
    return (ProgressBar) findViewById(R.id.advanced_title_bar_progress_bar);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu_messages, menu);
    return true;
  }

  @Override
  protected void startWriteMessageActivity(Message message) {
    Intent intent = new Intent(this, WriteMessageActivity.class);
    intent.putExtra(WriteMessageActivity.INTENT_EXTRA_MESSAGE_RESPOND_TO, message);
    startActivity(intent);
  }
}
