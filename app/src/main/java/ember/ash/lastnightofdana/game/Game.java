package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.Sequence;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

import static ember.ash.lastnightofdana.sequence.SequenceEnum.DIALOG_TEXT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_ALL_TO_BLACK;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.FADE_VIEW_OUT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.HIDE_CHOICES;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.HIDE_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.NARRATE_TEXT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.PLAY_BACKGROUND_MUSIC;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.PLAY_DOOR_SOUND;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SET_IMAGE;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SHOW_CHOICES;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SHOW_HEADPHONES_ALERT;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.SLIDE_VIEW_IN;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.STOP_BACKGROUND_MUSIC;
import static ember.ash.lastnightofdana.sequence.SequenceEnum.WAIT;

/**
 * This little lovely class is the heart of the game. It has all the scenes and sequences.
 * Game.java is a singleton that stores the current scene and has a sequenceQueue (between others)
 * to play the game. It has a playScene(sceneToPlayEnum) method that changes the currentSceneId
 * being played and adds to the sequenceQueue the sequences corresponding to that scene.
 *
 * How do we get the interactive choices working?
 *
 * When user press a choice button, it triggers onClickGameChoice() on the GameActivity.java.
 * There, we call the GameLogic class with the following parameters:
 * - button id (which choice)
 * - current scene being played id (retrieved by this singleton)
 *
 * The static method from GameLogic will then return a scene to play, and it will be played
 * from here, the Game.java class. All the sequences related to that scene will be enqueued and
 * played.
 *
 */
public class Game {
   private static Game ourInstance = new Game();
   private Activity activity;
   private SequenceQueue queue;
   private SoundPool soundPool;
   private int idClickSound, idDoorSound;
   private MediaPlayer mediaPlayer;
   private int currentMusicId;
   private SceneEnum currentScene;

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

   public SceneEnum getCurrentScene(){
      return currentScene;
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
      playScene(SceneEnum.DANA_INTRO);
      playScene(SceneEnum.DANA_TALKING_MAID);
   }

   public void playScene(SceneEnum scene){
      if (scene == null) {
         Toast.makeText(activity, "There was an unexpected error :(", Toast.LENGTH_SHORT).show();
         return;
      }
      currentScene = scene; // assign the current scene
      switch (scene){
         case DANA_INTRO: playDanaIntro(); break;
         case DANA_TALKING_MAID: playDanaMaid(); break;
         case DANA_SEE_HAYMITCH: playDanaSeeHaymitch(); break;
         case DANA_AVOID_HAYMITCH: playDanaAvoidHaymitch(); break;
      }
   }

   private void playDanaIntro(){
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
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getImageBackground())
              .setDuration(1500));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK).setDuration(1000));
   }

   private void playDanaMaid(){
      queue.addSequence(new Sequence(NARRATE_TEXT).setText(activity.getString(R.string.dana_intro1)));
      queue.addSequence(new Sequence(NARRATE_TEXT).setText(activity.getString(R.string.dana_intro2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
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
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro3)));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro4)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(WAIT).setDuration(1000));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana_sitting_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(500));
      queue.addSequence(new Sequence(WAIT).setDuration(1000));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro5)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro6)));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro7)));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro8)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro9)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_intro10)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(WAIT).setDuration(800));
      queue.addSequence(new Sequence(SHOW_CHOICES)
              .setChoice1(activity.getString(R.string.see_him))
              .setChoice2(activity.getString(R.string.avoid_him)));
   }

   private void playDanaSeeHaymitch(){
      queue.addSequence(new Sequence(HIDE_CHOICES));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_see_haymitch1)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_see_haymitch2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getImageLayer2())
              .setDuration(1000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND));
   }

   private void playDanaAvoidHaymitch(){
      queue.addSequence(new Sequence(HIDE_CHOICES));
      queue.addSequence(new Sequence(WAIT).setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(500));
      queue.addSequence(new Sequence(SET_IMAGE)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setImageResource(R.drawable.dana_lying_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN)
              .setView(ViewsHolder.getInstance().getImageLayer1())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_avoid_haymitch1)));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_avoid_haymitch2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT).setText(activity.getString(R.string.dana_avoid_haymitch3)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT)
              .setView(ViewsHolder.getInstance().getImageLayer2())
              .setDuration(1000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND));
   }
}
