package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import ember.ash.lastnightofdana.activity.GameActivity;
import ember.ash.lastnightofdana.game.Game;

public class FadeAllToBlackSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private long duration;
   private boolean listenerNotified;

   public FadeAllToBlackSequence(long duration, Game game){
      this.duration = duration;
      this.game = game;
   }

   @Override
   public void play() {
      fadeAllToBlack();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void fadeAllToBlack(){
      final ViewGroup parentViewGroup = game.getLayoutFather();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
               for (int i = 0; i < parentViewGroup.getChildCount(); i++) {
                  View view = parentViewGroup.getChildAt(i);
                  view.setClickable(true);
                  view.setVisibility(View.GONE);
                  if (view.equals(game.getImageBackground()) ||
                      view.equals(game.getImageLayer1()) ||
                      view.equals(game.getImageLayer2()) ||
                      view.equals(game.getImageLayer3())) {
                     ((ImageView)view).setImageDrawable(null); // clean memory
                  }
               }
               notifyListener();
         }

      });

      // Make nothing clickable and animate it
      for (int i = 0; i < parentViewGroup.getChildCount(); i++){
         if (parentViewGroup.getChildAt(i).getVisibility() == View.VISIBLE) {
            parentViewGroup.getChildAt(i).setClickable(false);
            parentViewGroup.getChildAt(i).startAnimation(fade);
         }
      }
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
