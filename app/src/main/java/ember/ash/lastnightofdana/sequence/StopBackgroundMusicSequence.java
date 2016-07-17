package ember.ash.lastnightofdana.sequence;

import ember.ash.lastnightofdana.game.Game;

public class StopBackgroundMusicSequence extends Sequence {
   private SequenceListener listener;
   private Game game;

   public StopBackgroundMusicSequence(Game game){
      this.game = game;
   }

   @Override
   public void play() {
      stopBackgroundMusic();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void stopBackgroundMusic(){
      game.stopMusic();
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
