package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.game.Game;

public class HideEarphonesAlertSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private long duration;
   private boolean listenerNotified;

   public HideEarphonesAlertSequence(long duration, Game game){
      this.duration = duration;
      this.game = game;
   }

   @Override
   public void play() {
      hideEarphonesAlert();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void hideEarphonesAlert(){
      final ImageView imageHeadphones = game.getImageHeadphones();
      final TextView textHeadphones = game.getTextHeadphones();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            imageHeadphones.setVisibility(View.GONE);
            textHeadphones.setVisibility(View.GONE);
            imageHeadphones.setImageDrawable(null); //clean memory
            notifyListener();
         }
      });
      imageHeadphones.startAnimation(fade);
      textHeadphones.startAnimation(fade);
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
