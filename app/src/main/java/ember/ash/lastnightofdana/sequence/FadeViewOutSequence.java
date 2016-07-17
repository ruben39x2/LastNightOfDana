package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class FadeViewOutSequence extends Sequence {
   private SequenceListener listener;
   private View view;
   private long duration;

   public FadeViewOutSequence(long duration, View view){
      this.duration = duration;
      this.view = view;
   }

   @Override
   public void play() {
      fadeViewOut();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }


   private void fadeViewOut(){
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            if (view instanceof ImageView) {
               ((ImageView) view).setImageDrawable(null);
            }
            view.setVisibility(View.GONE);
            notifyListener();
         }
      });
      view.startAnimation(fade);
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
