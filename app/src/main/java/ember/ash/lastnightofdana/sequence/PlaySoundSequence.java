package ember.ash.lastnightofdana.sequence;

import ember.ash.lastnightofdana.game.Game;

public class PlaySoundSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private int soundId;

   public PlaySoundSequence(int soundId, Game game){
      this.game = game;
      this.soundId = soundId;
   }

   @Override
   public void play() {
      playSound();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void playSound(){
      game.playSound(soundId);
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
