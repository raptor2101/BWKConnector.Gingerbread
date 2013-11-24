package de.raptor2101.BattleWorldsKronos.Connector.Gui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
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
import de.raptor2101.BattleWorldsKronos.Connector.JSON.GameListing;
import de.raptor2101.BattleWorldsKronos.Connector.Task.GameListingLoaderTask;
import de.raptor2101.BattleWorldsKronos.Connector.Task.GameListingLoaderTask.ResultListener;
import de.raptor2101.BattleWorldsKronos.Connector.ConnectorApp;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.GameInfoAdapater;
import de.raptor2101.BattleWorldsKronos.Connector.Gui.Controls.GameInfoView;

public class GameListingActivity extends Activity implements ResultListener, OnItemClickListener {
  private GameInfoAdapater mAdapter = new GameInfoAdapater(this);
  private GameInfoView mExpandedView;
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
    ConnectorApp app = (ConnectorApp) getApplication();
    
    if(settings.getEmail().equals(ApplicationSettings.EmptyResult)){
      startSettingsActivity();
      return;
    }
    
    
    if(app.getTimestampResultStored() == 0 || SystemClock.elapsedRealtime()-app.getTimestampResultStored()>settings.getRefreshCylce()){
      loadGameInfos();
    } else {
      handleResult(app.getResult());
    }
  }

  private void loadGameInfos() {
    ApplicationSettings settings = new ApplicationSettings(this);
    
    GameListingLoaderTask task = new GameListingLoaderTask(AndroidHttpClient.newInstance((String) getText(R.string.app_name)), settings.getEmail(), settings.getPassword(), this);
    task.execute(new Void[0]);
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
      loadGameInfos();
    }
    return super.onMenuItemSelected(featureId, item);
  }

  private void startSettingsActivity() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }
  
  @Override
  public void handleResult(GameListing result) {
    ProgressBar progressBar = (ProgressBar) findViewById(R.id.advanced_title_progress_bar);
    progressBar.setVisibility(View.GONE);
    
    if(result != null){
      mAdapter.setGameInfos(result.getMyGames());
      ConnectorApp app = (ConnectorApp) getApplication();
      app.storeResult(result);
      NotificationService.reset(this);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    GameInfoView gameInfoView = (GameInfoView) view;
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
