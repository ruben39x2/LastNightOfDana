package ember.ash.lastnightofdana.game;

import android.app.Activity;

import ember.ash.lastnightofdana.sequence.Sequence;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.*;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

/**
 * This objects represents a game
 */
public class Game {
   private Activity activity;
   private SequenceQueue queue = new SequenceQueue();

   public Game(Activity activity){
      this.activity = activity;
   }

   public void start(){
      queue.addSequence(new Sequence(FADE_TO_BLACK, activity));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT, activity, 400));
      queue.addSequence(new Sequence(WAIT, activity, 2500));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT, activity, 600));
      queue.addSequence(new Sequence(TEST, activity));
   }
}
