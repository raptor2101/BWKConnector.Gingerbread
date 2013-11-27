package de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.raptor2101.BattleWorldsKronos.Connector.ApplicationSettings;
import de.raptor2101.BattleWorldsKronos.Connector.NotificationService;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.NavigationButtonAdapter;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.R;
import de.raptor2101.BattleWorldsKronos.Connector.Task.GamesLoaderTask;
import de.raptor2101.BattleWorldsKronos.Connector.ConnectorApp;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.GameViewAdapater;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.Controls.GameView;

public class GameListingActivity extends Activity implements GamesLoaderTask.ResultListener, OnItemClickListener {
  private GameViewAdapater mAdapter = new GameViewAdapater(this);
  private GameView mExpandedView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.game_listing_activity);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.advanced_title_bar);
    
    
    NavigationButtonAdapter adapter =new NavigationButtonAdapter(this, R.menu.navigation_menu);
    ListView listView = (ListView) findViewById(R.id.navigation_menu);
    listView.setAdapter(adapter);
    
    listView = (ListView) findViewById(R.id.game_listing);
    listView.setAdapter(mAdapter);
    listView.setOnItemClickListener(this);
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    ApplicationSettings settings = new ApplicationSettings(this);
    
    if(settings.getEmail().equals(ApplicationSettings.EmptyResult)){
      startSettingsActivity();
      return;
    }
    
    ConnectorApp app = (ConnectorApp) getApplication();
    long lastLoad = app.getDatabase().getLastPersistTimestamp();
    loadGames(SystemClock.elapsedRealtime()-lastLoad>settings.getRefreshCylce());
  }

  private void loadGames(boolean forceReload) {
    ApplicationSettings settings = new ApplicationSettings(this);
    GamesLoaderTask task = new GamesLoaderTask(this, settings.getEmail(), settings.getPassword(), this);
    
    task.execute(new Boolean[]{forceReload});
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.advanced_title_progress_bar);
    progressBar.setVisibility(View.VISIBLE);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    if(item.getItemId() == R.id.action_settings){
      startSettingsActivity();
    } else if(item.getItemId() == R.id.action_refresh){
      loadGames(true);
    }
    return super.onMenuItemSelected(featureId, item);
  }

  private void startSettingsActivity() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }
  
  @Override
  public void handleResult(GamesLoaderTask.Result result) {
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.advanced_title_progress_bar);
    progressBar.setVisibility(View.GONE);
    
    if(result != null){
      mAdapter.setGames(result.getGames());
      NotificationService.reset(this);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    GameView gameInfoView = (GameView) view;
    if(gameInfoView.isExpanded()){
      gameInfoView.collapse();
      mExpandedView = null;
    } else{
      if(mExpandedView != null){
        mExpandedView.collapse();
      }
      gameInfoView.expand();
      mExpandedView = gameInfoView;
    }
  }
}
