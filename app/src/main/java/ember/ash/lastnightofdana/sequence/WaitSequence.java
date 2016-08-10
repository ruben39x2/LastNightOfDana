package ember.ash.lastnightofdana.sequence;

import ember.ash.lastnightofdana.game.Game;

public class WaitSequence extends Sequence {
   private SequenceListener listener;
   private Game game;
   private long duration;

   public WaitSequence(long duration, Game game){
      this.duration = duration;
      this.game = game;
   }

   @Override
   public void play() {
      waitBro();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void waitBro(){

      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(duration);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            if (game.getActivity() != null) {
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     notifyListener();
                  }
               });
            }
         }
      }).start();

   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
