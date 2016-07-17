package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ember.ash.lastnightofdana.game.Game;

public class SlideViewInSequence extends Sequence {
   private SequenceListener listener;
   private View view;
   private Game game;

   public SlideViewInSequence(View view, Game game){
      this.view = view;
      this.game = game;
   }

   @Override
   public void play() {
      slideViewIn();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void slideViewIn(){
      view.setVisibility(View.VISIBLE);
      Animation animation = AnimationUtils.loadAnimation(game.getActivity(), android.R.anim.slide_in_left);
      animation.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      view.startAnimation(animation);
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
