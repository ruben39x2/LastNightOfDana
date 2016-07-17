package ember.ash.lastnightofdana.sequence;

import android.view.View;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ember.ash.lastnightofdana.game.Game;

/**
 * There is some important logic about sequences here. Let's explain it.
 *
 * Every "game" object has a SequenceQueue. This queue has one really important
 * method: addSequence. AddSequence():
 * 1 - Adds a sequence object to the queue
 * 2 - If no sequences are being played, removes the head of the queue and plays it
 * When a sequence finishes being played, it removes the head of the queue and plays it
 * (if queue is not empty) through a listener.
 *
 * So, for instance:
 *
 * If we add 2 scenes to our SequenceQueue in our Game object:
 * queue.addScene(typeFadeToBlack)
 * queue.addScene(typeFadeToWhite)
 * Then:
 * 1 - sequence fade to black will be added to the queue, removed and played. The
 *     variable "isPlaying" will be set to true while sequence is being played.
 * 2 - sequence fade to white will be added to the queue. Nothing will be played as
 *     some sequence is being played already (this is, the fade to black sequence).
 * 3 - when reproduction of fade to black finishes, it triggers the next sequence to be
 *     played through a listener contained into the own sequence.
 * 4 - when reproduction of fade to white finishes, queue is empty and both scenes have
 *     been played in proper order. No other sequence is triggered to be played as long
 *     as the queue is empty (no other calls to addScene() have been done)
 *
 * Easy :)
 */
public class SequenceQueue {
   private Game game;
   private Queue<Sequence> queue = new ConcurrentLinkedQueue<>();
   private boolean isPlaying = false;

   public SequenceQueue(Game game){
      this.game = game;
   }

   public void removeAllAndStop(){
      queue.clear();
   }

   /**
    * Adds a sequence to this queue. The sequence will be played instantly if queue
    * is empty and will wait until the execution of other sequences finishes otherwise.
    *
    * @param sequence the sequence object to be played
    */
   public void addSequence(Sequence sequence){
      sequence.setListener(getListener());
      queue.add(sequence);
      if (!isPlaying) playNextScene();
   }

   private synchronized void playNextScene(){
      isPlaying = true;
      queue.remove().play();
   }

   private SequenceListener getListener(){
      return new SequenceListener() {
         @Override
         public void onSequenceFinished() {
            isPlaying = false;
            if (!queue.isEmpty()) playNextScene();
         }

         @Override
         public void onSequenceWaitingForNarration() {
            game.getLayoutFather().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  notifySequenceNarrationFinished();
               }
            });
         }

         @Override
         public void onSequenceWaitingForDialog() {
            game.getLayoutDialogText().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  notifySequenceDialogFinished();
               }
            });
         }
      };
   }

   /**
    * This is called when the screen hangs for the user to touch it and a onClick event happens.
    * This triggers moreless the same things that are triggered when a sequence finishes
    * an onSequenceFinished() is called.
    */
   public void notifySequenceNarrationFinished(){
      // Play a cool 'duru'
      game.playClick();

      // Do the stuff that a normal "sequenceFinished" would do, in order to trigger next scene
      isPlaying = false;
      if (!queue.isEmpty()) playNextScene();

      /* This is extra cool stuff hehe */
      // Clean the narration text
      game.getArrowMiddle().clearAnimation();
      game.getArrowMiddle().setVisibility(View.GONE);

      // Remove the listener from the screen
      game.getLayoutFather().setOnClickListener(null);
      game.getLayoutDialogText().setOnClickListener(null);
   }

   /**
    * This is called when the screen hangs for the user to touch it and a onClick event happens.
    * This triggers moreless the same things that are triggered when a sequence finishes
    * an onSequenceFinished() is called.
    */
   public void notifySequenceDialogFinished(){
      // Play a cool 'duru'
      game.playClick();

      // Do the stuff that a normal "sequenceFinished" would do, in order to trigger next scene
      isPlaying = false;
      if (!queue.isEmpty()) playNextScene();

      // If we are waiting because of a dialog text... let's do cool stuff too
      game.getArrowDialog().clearAnimation();
      game.getArrowDialog().setVisibility(View.GONE);

      // Remove the listener from the dialog
      game.getLayoutDialogText().setOnClickListener(null);
   }
}
