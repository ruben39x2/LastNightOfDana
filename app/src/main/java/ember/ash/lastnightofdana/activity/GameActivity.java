package ember.ash.lastnightofdana.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.game.GameLogic;
import ember.ash.lastnightofdana.game.SceneEnum;
import ember.ash.lastnightofdana.sequence.AnimationEndListener;
import ember.ash.lastnightofdana.util.FontsOverride;
import ember.ash.lastnightofdana.util.LogSystem;

public class GameActivity extends AppCompatActivity {
   private Game game;
   private Activity activity = this;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_game);

      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
              WindowManager.LayoutParams.FLAG_FULLSCREEN);

      FontsOverride.overrideTypefaces(this);
      game = new Game(this);

      animateStartButton();
      //animateSky();

      game.playMusic(R.raw.music_intro, true);
   }

   private void animateStartButton() {
      Button button = (Button)findViewById(R.id.button_play);
      button.startAnimation(game.getButtonAnimation());
   }

   private void animateSky(){
      Animation alpha1 = new AlphaAnimation(1f, 0.2f);
      alpha1.setDuration(2500);
      alpha1.setFillAfter(true);
      game.getImageBackground().startAnimation(alpha1);

      Animation alpha2 = new AlphaAnimation(1f, 0.6f);
      alpha2.setDuration(3000);
      alpha2.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            game.getImageLayer2().setImageResource(R.drawable.streetlight_ilumination);
            fadeStarsIn();
            startShootingStars();
         }
      });
      game.getImageLayer1().startAnimation(alpha2);
   }

   private void fadeStarsIn(){
      for (int i = 0; i < 30; i++){
         // add a still star to the layout
         final ImageView star = new ImageView(this);
         star.setImageResource(R.drawable.tiny_star);
         star.setPadding(randomX(), randomY(), 0, 0);
         //game.getLayoutStars().addView(star);

         // fade in the star
         Animation alpha = new AlphaAnimation(0f, 1f);
         alpha.setDuration(randomDuration());
         star.startAnimation(alpha);
      }
   }

   private int randomX(){
      return (int) (Math.random() * 800);
   }

   private int randomY(){
      return (int) (Math.random() * 220);
   }

   private long randomDuration(){
      return (long) (Math.random() * 6000);
   }

   private void startShootingStars(){
      game.getLayoutStars().setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            showShootingStar(event.getX(), event.getY());
            return false;
         }
      });
   }

   private void showShootingStar(float x, float y){
      final ImageView star = new ImageView(this);
      star.setImageResource(R.drawable.tiny_star);
      game.getLayoutStars().addView(star);
      TranslateAnimation anim = new TranslateAnimation(x, x + 250f, y, y + 125f);
      anim.setDuration(1200);
      anim.setInterpolator(new AccelerateInterpolator(1.8f));
      star.startAnimation(anim);
   }

   public void onClickStartGame(View view){
      game.stopMusic();
//      game.getLayoutStars().removeAllViews();
//      game.getImageLayer2().setImageDrawable(null);
      startGameDana();
   }

   private void startGameDana(){
      game.playClick();
      game.start();
   }

   public void onClickGameChoice(View v){
      game.playClick();
      game.playScene(
              GameLogic.decideSceneToPlay(
                      game.getCurrentScene(),
                      v.getId()));
   }

   // By the moment, every time that the activity is paused, we destroy it
   @Override
   protected void onPause() {
      if (game.getCurrentScene() == SceneEnum.MAIN_MENU){
         game.getImageLayer2().setImageDrawable(null);
      }
      game.stopMusic();
      game.release();
      super.onPause();
      finish();
   }

   @SuppressLint("NewApi")
   @SuppressWarnings("deprecation")
   public boolean copyToClipboard(Context context, String text) {
      try {
         int sdk = android.os.Build.VERSION.SDK_INT;
         if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
         } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("error", text);
            clipboard.setPrimaryClip(clip);
         }
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   public void showUncaughtExceptionDialog(final Exception e){
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
      alertDialogBuilder
              .setCancelable(false)
              .setTitle("Ooops!")
              .setMessage("Something unexpected has happened. We apologize for the inconvenience, but the game should be closed. The text of the error has been copied to the clipboard.")
              .setPositiveButton("Ok, exit :(", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    game.release();
                    game.stopMusic();
                    activity.finish();
                 }
              })
              .setNegativeButton("Try to continue", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(activity, "Ok, let's go...", Toast.LENGTH_SHORT).show();
                 }
              });
      // Create alert dialog and show it.
      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();
      // Copy error to clipboard
      copyToClipboard(activity, parseException(e));
   }

   private String parseException(Exception e){
      String error = "Last night of Dana - Unhandled exception from thread\n";
      error += "Reason of the error: " + e.getMessage() + "\nTRACE:\n";
      StackTraceElement[] trace = e.getStackTrace();
      for (StackTraceElement aTrace : trace) error += aTrace.toString() + "\n";
      error += "CAUSE:\n";
      if (e.getCause() != null) {
         StackTraceElement[] cause = e.getCause().getStackTrace();
         for (StackTraceElement aCause : cause) error += aCause.toString() + "\n";
      } else {
         error += "Cause not known";
      }
      return error;
   }
}
