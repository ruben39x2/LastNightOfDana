package ember.ash.lastnightofdana.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.Sequence;

public class GameActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
              WindowManager.LayoutParams.FLAG_FULLSCREEN);
   }

   public void onClickStartGame(View view){
      int id = view.getId();
      switch (id){
         case R.id.button_dana:
            Toast.makeText(this, "Gogo", Toast.LENGTH_SHORT).show();
            Sequence.fadeAllToBlack(this, 2000);
            break;
         default:
            Toast.makeText(this, "Not available yet", Toast.LENGTH_SHORT).show();
            break;
      }
   }
}
