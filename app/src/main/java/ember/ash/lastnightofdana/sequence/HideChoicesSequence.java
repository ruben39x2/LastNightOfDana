package ember.ash.lastnightofdana.sequence;

import android.view.View;

import ember.ash.lastnightofdana.game.Game;

public class HideChoicesSequence extends Sequence {
   private SequenceListener listener;
   private Game game;

   public HideChoicesSequence(Game game){
      this.game = game;
   }

   @Override
   public void play() {
      hideChoices();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void hideChoices(){
      game.getButtonChoice1().clearAnimation();
      game.getButtonChoice2().clearAnimation();
      game.getButtonChoice1().setVisibility(View.GONE);
      game.getButtonChoice2().setVisibility(View.GONE);
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
