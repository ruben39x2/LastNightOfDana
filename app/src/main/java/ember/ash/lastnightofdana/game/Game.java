package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.content.Context;

import ember.ash.lastnightofdana.sequence.Sequence;
import ember.ash.lastnightofdana.sequence.SequenceEnum;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

/**
 * Created by Rubén Montero Vázquez on 06/07/2016
 */
public class Game {
   private Activity activity;
   private SequenceQueue queue = new SequenceQueue();

   public Game(Activity activity){
      this.activity = activity;
      queue.addSequence(new Sequence(SequenceEnum.TEST, activity));
      queue.addSequence(new Sequence(SequenceEnum.FADE_TO_BLACK, activity));
      queue.addSequence(new Sequence(SequenceEnum.TEST, activity));
      queue.addSequence(new Sequence(SequenceEnum.TEST, activity));



   }
}
