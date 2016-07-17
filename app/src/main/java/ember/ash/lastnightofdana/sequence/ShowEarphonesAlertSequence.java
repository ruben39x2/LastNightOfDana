package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;

public class ShowEarphonesAlertSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private long duration;
   private boolean listenerNotified;

   public ShowEarphonesAlertSequence(long duration, Game game){
      this.duration = duration;
      this.game = game;
   }

   @Override
   public void play() {
      showEarphonesAlert();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void showEarphonesAlert(){
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      game.getImageHeadphones().setImageResource(R.drawable.earphones);
      game.getImageHeadphones().setVisibility(View.VISIBLE);
      game.getImageHeadphones().startAnimation(fade);
      game.getTextHeadphones().setVisibility(View.VISIBLE);
      game.getTextHeadphones().startAnimation(fade);
   }

   /**
    * This method is always called only once by each instance of Sequence, even if
    * multiple animation listeners call it.
    */
   private synchronized void notifyListener(){
      if (!listenerNotified){
         if (listener != null){
            listener.onSequenceFinished();
         }
         listenerNotified = true;
      }
   }
}
