package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.widget.ImageView;

import ember.ash.lastnightofdana.R;
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
      ImageView imageBackground = (ImageView) activity.findViewById(R.id.image_background);
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK, activity, 3000));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT, activity, 400));
      queue.addSequence(new Sequence(WAIT, activity, (long) 2500));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT, activity, 600));
      queue.addSequence(new Sequence(WAIT, activity, 1000));
      queue.addSequence(new Sequence(SET_IMAGE, imageBackground, R.drawable.mansion));
      queue.addSequence(new Sequence(FADE_FROM_BLACK, activity, 4000));
      queue.addSequence(new Sequence(TEST, activity));
   }
}
