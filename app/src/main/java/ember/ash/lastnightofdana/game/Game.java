package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.Sequence;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

import static ember.ash.lastnightofdana.sequence.SequenceEnum.DIALOG_TEXT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_ALL_TO_BLACK;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_TO_BLACK;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.HIDE_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.NARRATE_TEXT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.PLAY_BACKGROUND_MUSIC;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.PLAY_DOOR_SOUND;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SET_IMAGE;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SHOW_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SLIDE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.STOP_BACKGROUND_MUSIC;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.WAIT;

public class Game {
   private static Game ourInstance = new Game();
   private Activity activity;
   private SequenceQueue queue;
   private SoundPool soundPool;
   private int idClickSound, idDoorSound;
   private MediaPlayer mediaPlayer;
   private int currentMusicId;

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
      idDoorSound = soundPool.load(activity, R.raw.door, 0);
   }

   public void playMusic(int resId, boolean loop){
      currentMusicId = resId;
      if (mediaPlayer == null) { // otherwise, the music is already being played
         mediaPlayer = MediaPlayer.create(activity, resId);
         mediaPlayer.setLooping(loop);
         mediaPlayer.start();
      }
   }

   public void stopMusic(){
      if (mediaPlayer != null) {
         mediaPlayer.release();
         mediaPlayer = null;
      }
   }

   public void resumeMusic(){ // support for resuming the game still not available
      mediaPlayer = MediaPlayer.create(activity, currentMusicId);
      mediaPlayer.setLooping(true);
      mediaPlayer.start();
   }

   public void playClick(){
      soundPool.play(idClickSound, 1, 1, 1, 0, 1);
   }

   public void playDoor(){
      soundPool.play(idDoorSound, 1, 1, 1, 0, 1);
   }

   public void start(){
      this.queue = new SequenceQueue();
      ViewsHolder.getInstance().getTextName().setText(R.string.dana);

      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK).setDuration(2000));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT).setDuration(400));
      queue.addSequence(new Sequence(WAIT).setDuration(2000));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT).setDuration(600));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(PLAY_BACKGROUND_MUSIC)
              .setMusicId(R.raw.music_dana)
              .setLooping(false));
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
      queue.addSequence(new Sequence(STOP_BACKGROUND_MUSIC));
      queue.addSequence(new Sequence(PLAY_BACKGROUND_MUSIC)
              .setMusicId(R.raw.music_strings1)
              .setLooping(true));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana_lying_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(1500));
      queue.addSequence(new Sequence(WAIT).setDuration(2000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND));
      queue.addSequence(new Sequence(WAIT).setDuration(1000));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer2())
              .setImageResource(R.drawable.maid));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer2())
              .setDuration(800));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText("Maid:\nHey there niggaaaa!"));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText("Maid:\nwanna die bro?"));


      //queue.addSequence(new Sequence(SET_IMAGE)
        //      .setView(ViewsHolder.getInstance().getImageLayer2())
          //    .setImageResource(R.drawable.dana_sitting_bed));
   }
}
