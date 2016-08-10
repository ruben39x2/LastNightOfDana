package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import ember.ash.lastnightofdana.game.Game;

public class WaitForClickSequence extends Sequence {
   private SequenceListener listener;
   private View view;

   public WaitForClickSequence(View viewToClick){
      this.view = viewToClick;
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
      notifyWaitingClick();
   }

   private void notifyWaitingClick(){
      listener.onSequenceWaitingForClick(view);
   }
}
