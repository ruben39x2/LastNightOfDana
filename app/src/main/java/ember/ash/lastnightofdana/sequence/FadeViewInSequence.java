package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class FadeViewInSequence extends Sequence {
   private SequenceListener listener;
   private View view;
   private long duration;
   private boolean asynchronous = false;

   public FadeViewInSequence(long duration, View view){
      this.duration = duration;
      this.view = view;
   }

   public FadeViewInSequence setAsynchronous(){
      this.asynchronous = true;
      return this;
   }

   @Override
   public void play() {
      fadeViewIn();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void fadeViewIn(){
      view.setVisibility(View.VISIBLE);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            if (!asynchronous) notifyListener(); // notify listener when animation ends
         }
      });
      view.startAnimation(fade);
      if (asynchronous) notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
