package ember.ash.lastnightofdana.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.game.ViewsHolder;
import ember.ash.lastnightofdana.util.FontsOverride;

public class GameActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
              WindowManager.LayoutParams.FLAG_FULLSCREEN);

      FontsOverride.overrideTypefaces(this);
      Game.getInstance().setActivity(this);
      Game.getInstance().loadSounds();
   }

   public void onClickStartGame(View view){
      startGameDana();
   }

   private void startGameDana(){
      ViewsHolder.clearViews();
      Game.getInstance().start();
   }
}
