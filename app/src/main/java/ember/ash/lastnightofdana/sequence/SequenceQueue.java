package ember.ash.lastnightofdana.sequence;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.game.ViewsHolder;

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
   private Queue<Sequence> queue = new ConcurrentLinkedQueue<>();
   private boolean isPlaying = false;

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
         public void onSequenceWaiting() {
            ViewsHolder.getInstance().getViewFather().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  notifySequenceFinished();
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
   public void notifySequenceFinished(){
      // Play a cool 'duru'
      Game.getInstance().playClick();

      //final MediaPlayer mp = MediaPlayer.create(Game.getInstance().getActivity(), R.raw.click);
      //mp.start();

      isPlaying = false;
      if (!queue.isEmpty()) playNextScene();

      /* This is extra cool stuff hehe */
      // Clean the narration text
      //ViewsHolder.getInstance().getTextNarrate().setVisibility(View.GONE);
      ViewsHolder.getInstance().getArrowMiddle().clearAnimation();
      ViewsHolder.getInstance().getArrowMiddle().setVisibility(View.GONE);

      // Remove the listener from the screen
      ViewsHolder.getInstance().getViewFather().setOnClickListener(null);



   }
}
