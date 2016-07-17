package ember.ash.lastnightofdana.sequence;

import android.view.View;

import ember.ash.lastnightofdana.game.Game;

public class ShowChoicesSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private String choice1, choice2;
   private boolean listenerNotified;

   public ShowChoicesSequence(String choice1, String choice2, Game game){
      this.game = game;
      this.choice1 = choice1;
      this.choice2 = choice2;
   }

   @Override
   public void play() {
      showChoices();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void showChoices() {
      game.getButtonChoice1().setText(" " + choice1 + " "); //hhmmm
      game.getButtonChoice1().setVisibility(View.VISIBLE);
      game.getButtonChoice1().startAnimation(
              game.getButtonAnimation());
      game.getButtonChoice2().setText(" " + choice2 + " "); //nice fix......
      game.getButtonChoice2().setVisibility(View.VISIBLE);
      game.getButtonChoice2().startAnimation(
              game.getButtonAnimation());
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
