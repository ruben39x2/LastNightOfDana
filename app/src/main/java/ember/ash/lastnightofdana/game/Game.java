package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.Sequence;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_ALL_TO_BLACK;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_TO_BLACK;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.HIDE_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.NARRATE_TEXT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SET_IMAGE;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SHOW_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SLIDE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.WAIT;

public class Game {
   private static Game ourInstance = new Game();
   private Activity activity;
   private SequenceQueue queue;
   private SoundPool soundPool;
   private int idClickSound;

   public static Game getInstance() {
      return ourInstance;
   }

   private Game() {
   }

   public void setActivity(Activity activity){
      this.activity = activity;
   }

   public Activity getActivity(){
      return activity;
   }

   public void loadSounds(){
      soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
      idClickSound = soundPool.load(activity, R.raw.click, 0);
   }

   public void playClick(){
      soundPool.play(idClickSound, 1, 1, 1, 0, 1);
   }

   public void start(){
      this.queue = new SequenceQueue();
      ViewsHolder.getInstance().getTextName().setText(R.string.dana);

      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK).setDuration(2000));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT).setDuration(400));
      queue.addSequence(new Sequence(WAIT).setDuration(2000));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT).setDuration(600));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setImageResource(R.drawable.mansion));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setDuration(2000));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana1));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(800));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageMiddleBar())
              .setDuration(600));
      queue.addSequence(new Sequence(SLIDE_VIEW_IN).setView(ViewsHolder.getInstance().getTextName()));
      queue.addSequence(new Sequence(WAIT).setDuration(1500));
      queue.addSequence(new Sequence(FADE_VIEW_TO_BLACK)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setDuration(1500));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK).setDuration(1000));
      queue.addSequence(new Sequence(NARRATE_TEXT).setText(activity.getString(R.string.dana_intro1)));
      queue.addSequence(new Sequence(NARRATE_TEXT).setText(activity.getString(R.string.dana_intro2)));
      queue.addSequence(new Sequence(FADE_VIEW_TO_BLACK)
              .setView(ViewsHolder.getInstance().getTextNarrate())
              .setDuration(500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setImageResource(R.drawable.restroom));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setDuration(1500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana_lying_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(1500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana_sitting_bed));
   }
}
