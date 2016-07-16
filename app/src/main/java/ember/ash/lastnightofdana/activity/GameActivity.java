package ember.ash.lastnightofdana.activity;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.game.ViewsHolder;
import ember.ash.lastnightofdana.util.FontsOverride;

public class GameActivity extends AppCompatActivity {
   private MediaPlayer mediaPlayer;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
              WindowManager.LayoutParams.FLAG_FULLSCREEN);

      FontsOverride.overrideTypefaces(this);
      Game.getInstance().setActivity(this);
      Game.getInstance().loadSounds();
      animateStartButton();
      Game.getInstance().playMusic(R.raw.music_intro, true);

   }

   private void animateStartButton() {
      findViewById(R.id.button_play).startAnimation(ViewsHolder.getInstance().getButtonAnimation());
   }

   public void onClickStartGame(View view){
      Game.getInstance().stopMusic();
      startGameDana();
   }

   private void startGameDana(){
      ViewsHolder.clearViews();
      Game.getInstance().playClick();
      Game.getInstance().start();
   }

   public void onClickGameChoice(View v){
      Toast.makeText(this, "you pressed " + ((Button)v).getText() + " ", Toast.LENGTH_SHORT).show();
   }

   @Override
   protected void onPause() {
      super.onPause();
      Game.getInstance().stopMusic();
   }

   @Override
   protected void onResume() {
      super.onResume();
      //Game.getInstance().resumeMusic();
   }
}
