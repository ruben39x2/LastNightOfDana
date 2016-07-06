package ember.ash.lastnightofdana.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.util.FontsOverride;

public class GameActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
              WindowManager.LayoutParams.FLAG_FULLSCREEN);

      FontsOverride.overrideTypefaces(this);
   }

   public void onClickStartGame(View view){
      int id = view.getId();
      switch (id){
         case R.id.button_dana:
            startGameDana();
            break;
         default:
            Toast.makeText(this, "Not available yet", Toast.LENGTH_SHORT).show();
            break;
      }
   }

   private void startGameDana(){
      Game game = new Game(this);
      game.start();
   }
}
