package ember.ash.lastnightofdana.sequence;

import ember.ash.lastnightofdana.game.Game;

public class PlayBackgroundMusicSequence extends Sequence {
   private SequenceListener listener;
   private int musicId;
   private boolean looping;
   private Game game;

   public PlayBackgroundMusicSequence(int musicId, boolean loop, Game game){
      this.musicId = musicId;
      this.looping = loop;
      this.game = game;
   }

   @Override
   public void play() {
      playBackgroundMusic();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void playBackgroundMusic(){
      game.playMusic(musicId, looping);
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
