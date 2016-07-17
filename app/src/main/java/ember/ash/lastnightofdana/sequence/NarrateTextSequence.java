package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import ember.ash.lastnightofdana.game.Game;

public class NarrateTextSequence extends Sequence {
   private SequenceListener listener;
   private String text;
   private Game game;

   public NarrateTextSequence(String text, Game game){
      this.text = text;
      this.game = game;
   }

   @Override
   public void play() {
      narrateText();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void narrateText(){
      game.getTextNarrate().setVisibility(View.VISIBLE);
      game.getTextNarrate().setText(text);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(500);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            game.getArrowMiddle().setVisibility(View.VISIBLE);
            game.getArrowMiddle().startAnimation(getIntermitentAnimation());
            notifyWaitingNarration();
         }
      });
      game.getTextNarrate().startAnimation(fade);
   }

   private void notifyWaitingNarration(){
      listener.onSequenceWaitingForNarration();
   }

   private Animation getIntermitentAnimation(){
      Animation anim = new AlphaAnimation(0f, 1f);
      anim.setDuration(250);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }
}
