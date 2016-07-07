package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.Sequence;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.*;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

/**
 * This objects represents a game
 */
public class Game {
   private Activity activity;
   private SequenceQueue queue;

   public Game(Activity activity){
      this.activity = activity;
      this.queue = new SequenceQueue(activity);
   }

   public void start(){
      View imageBackground =  activity.findViewById(R.id.image_background);
      View imageLayer1 = activity.findViewById(R.id.image_layer1);
      TextView textName = (TextView)activity.findViewById(R.id.text_player_name);
      textName.setText("Dana");

      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK, activity, 2000));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT, activity, 400));
      queue.addSequence(new Sequence(WAIT, activity, (long) 2000));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT, activity, 600));
      queue.addSequence(new Sequence(WAIT, activity, 500));
      queue.addSequence(new Sequence(SET_IMAGE, imageBackground, R.drawable.mansion));
      queue.addSequence(new Sequence(FADE_FROM_BLACK, activity, 800));
      queue.addSequence(new Sequence(SET_IMAGE, imageLayer1, R.drawable.dana1));
      queue.addSequence(new Sequence(FADE_VIEW_IN, imageLayer1, (long) 800));
      queue.addSequence(new Sequence(EXPAND_TEXT_BAR, activity, 1000));
      queue.addSequence(new Sequence(FADE_VIEW_IN, textName, (long) 500));
      queue.addSequence(new Sequence(WAIT, activity, 1500));
      queue.addSequence(new Sequence(FADE_VIEW_TO_BLACK, imageBackground, (long) 1500));
      queue.addSequence(new Sequence(WAIT, activity, 500));
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK, activity, 1000));
      queue.addSequence(new Sequence(NARRATE_TEXT, activity, "You are Dana, a young wealthy lady whose father is the lord of the county"));
      queue.addSequence(new Sequence(NARRATE_TEXT, activity, "You have always lived very protected, but you know that an important fate awaits you"));

   }

}
