package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
 * Game.java represents an object that stores the current scene and has a sequenceQueue
 * to play the game. It has a playScene(sceneToPlayEnum) method that changes the currentSceneId
 * being played and adds to the sequenceQueue the sequences corresponding to that scene.
 *
 * How do we get the interactive choices working?
 *
 * When user press a choice button, it triggers onClickGameChoice() on the GameActivity.java.
 * There, we call the GameLogic class with the following parameters:
 * - button id (which choice)
 * - current scene being played id (retrieved by this object, contained in the activity)
 *
 * The static method from GameLogic will then return a scene to play. Then, the
 * method onClickGameChoice() will use the playScene(sceneToPlayEnum) of the game object
 * that will trigger actions here, the Game.java class.
 * All the sequences related to that scene will be enqueued and played.
 */
public class Game {
   private Activity activity;
   private SequenceQueue queue;
   private SoundPool soundPool;
   private int idClickSound, idDoorSound;
   private MediaPlayer mediaPlayer;
   private int currentMusicId;
   private SceneEnum currentScene = SceneEnum.MAIN_MENU;

   public Game(Activity activity) {
      this.activity = activity;
      this.queue = new SequenceQueue(this);
      loadSounds();
   }

   public Activity getActivity(){
      return activity;
   }

   public SceneEnum getCurrentScene(){
      return currentScene;
   }

   public void release(){
      this.activity = null;
      queue.removeAllAndStop();
   }

   private void loadSounds(){
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
      this.getTextName().setText(R.string.dana);
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
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK, this).setDuration(2000));
      queue.addSequence(new Sequence(SHOW_HEADPHONES_ALERT, this).setDuration(400));
      queue.addSequence(new Sequence(WAIT, this).setDuration(2000));
      queue.addSequence(new Sequence(HIDE_HEADPHONES_ALERT, this).setDuration(600));
      queue.addSequence(new Sequence(WAIT, this).setDuration(500));
      queue.addSequence(new Sequence(PLAY_BACKGROUND_MUSIC, this)
                      .setMusicId(R.raw.music_dana)
                      .setLooping(false)
      );
      queue.addSequence(new Sequence(SET_IMAGE, this)
                      .setView(this.getImageBackground())
                      .setImageResource(R.drawable.mansion)
      );
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
                      .setView(this.getImageBackground())
                      .setDuration(2000)
      );
      queue.addSequence(new Sequence(SET_IMAGE, this)
                      .setView(this.getImageLayer1())
                      .setImageResource(R.drawable.dana1)
      );
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
                      .setView(this.getImageLayer1())
                      .setDuration(800)
      );
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
                      .setView(this.getImageMiddleBar())
                      .setDuration(600)
      );
      queue.addSequence(new Sequence(SLIDE_VIEW_IN, this)

              .setView(this.getTextName()));
      queue.addSequence(new Sequence(WAIT, this).setDuration(1500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getImageBackground())
              .setDuration(1500));
      queue.addSequence(new Sequence(WAIT, this).setDuration(500));
      queue.addSequence(new Sequence(FADE_ALL_TO_BLACK, this).setDuration(1000));
   }

   private void playDanaMaid(){
      queue.addSequence(new Sequence(NARRATE_TEXT, this).setText(activity.getString(R.string.dana_intro1)));
      queue.addSequence(new Sequence(NARRATE_TEXT, this).setText(activity.getString(R.string.dana_intro2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getTextNarrate())
              .setDuration(500));
      queue.addSequence(new Sequence(SET_IMAGE, this)
              .setView(this.getImageBackground())
              .setImageResource(R.drawable.restroom));
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
              .setView(this.getImageBackground())
              .setDuration(1500));
      queue.addSequence(new Sequence(STOP_BACKGROUND_MUSIC, this));
      queue.addSequence(new Sequence(PLAY_BACKGROUND_MUSIC, this)
                      .setMusicId(R.raw.music_strings1)
                      .setLooping(true)
      );
      queue.addSequence(new Sequence(SET_IMAGE, this)
              .setView(this.getImageLayer1())
              .setImageResource(R.drawable.dana_lying_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
              .setView(this.getImageLayer1())
              .setDuration(1500));
      queue.addSequence(new Sequence(WAIT, this).setDuration(2000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND, this));
      queue.addSequence(new Sequence(WAIT, this).setDuration(1000));
      queue.addSequence(new Sequence(SET_IMAGE, this)
              .setView(this.getImageLayer2())
              .setImageResource(R.drawable.maid));
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
              .setView(this.getImageLayer2())
              .setDuration(800));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro3)));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro4)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(WAIT, this).setDuration(1000));
  /*    queue.addSequence(new Sequence(CROSSFADE_VIEW)
              .setView(this.getImageLayer1())
              .setImageResource(R.drawable.dana_sitting_bed)
              .setDuration(1000)
              );// this was good, but causes memory exceed on old phones*/
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getImageLayer1())
              .setDuration(200));
      queue.addSequence(new Sequence(SET_IMAGE, this)
              .setView(this.getImageLayer1())
              .setImageResource(R.drawable.dana_sitting_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
              .setView(this.getImageLayer1())
              .setDuration(200));
      queue.addSequence(new Sequence(WAIT, this).setDuration(1000));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro5)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro6)));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro7)));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro8)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro9)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_intro10)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(WAIT, this).setDuration(800));
      queue.addSequence(new Sequence(SHOW_CHOICES, this)
              .setChoice1(activity.getString(R.string.see_him))
              .setChoice2(activity.getString(R.string.avoid_him)));
   }

   private void playDanaSeeHaymitch(){
      queue.addSequence(new Sequence(HIDE_CHOICES, this));
      queue.addSequence(new Sequence(WAIT, this).setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_see_haymitch1)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_see_haymitch2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getImageLayer2())
              .setDuration(1000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND, this));
   }

   private void playDanaAvoidHaymitch(){
      queue.addSequence(new Sequence(HIDE_CHOICES, this));
      queue.addSequence(new Sequence(WAIT, this).setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getImageLayer1())
              .setDuration(200));
      queue.addSequence(new Sequence(SET_IMAGE, this)
              .setView(this.getImageLayer1())
              .setImageResource(R.drawable.dana_lying_bed));
      queue.addSequence(new Sequence(FADE_VIEW_IN, this)
              .setView(this.getImageLayer1())
              .setDuration(200));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_avoid_haymitch1)));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_avoid_haymitch2)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(DIALOG_TEXT, this).setText(activity.getString(R.string.dana_avoid_haymitch3)));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getLayoutDialogText())
              .setDuration(500));
      queue.addSequence(new Sequence(FADE_VIEW_OUT, this)
              .setView(this.getImageLayer2())
              .setDuration(1000));
      queue.addSequence(new Sequence(PLAY_DOOR_SOUND, this));
   }








   public RelativeLayout getLayoutFather(){
      return (RelativeLayout) activity.findViewById(R.id.layout_father);
   }


   public TextView getTextName(){
      return (TextView) activity.findViewById(R.id.text_player_name);
   }

   public ImageView getImageHeadphones(){
      return (ImageView) activity.findViewById(R.id.image_headphones);
   }

   public TextView getTextHeadphones(){
      return (TextView) activity.findViewById(R.id.text_headphones);
   }


   public ImageView getImageBackground(){
      return (ImageView) activity.findViewById(R.id.image_background);
   }

   public ImageView getImageLayer1(){
      return (ImageView) activity.findViewById(R.id.image_layer1);
   }

   public ImageView getImageLayer2(){
      return (ImageView) activity.findViewById(R.id.image_layer2);
   }

   public ImageView getImageLayer3(){
      return (ImageView) activity.findViewById(R.id.image_layer3);
   }

   public RelativeLayout getLayoutStars(){
      return (RelativeLayout) activity.findViewById(R.id.layout_stars);
   }

   public View getLayoutDialogText(){
      return activity.findViewById(R.id.layout_dialog);
   }

   public TextView getDialogText(){
      return (TextView) activity.findViewById(R.id.text_dialog);
   }

   public View getImageMiddleBar(){
      return activity.findViewById(R.id.image_middle_bar);
   }

   public TextView getTextNarrate(){
      return (TextView) activity.findViewById(R.id.text_middle_white);
   }

   public ImageView getArrowMiddle(){
      return (ImageView) activity.findViewById(R.id.image_arrow_middle);
   }

   public ImageView getArrowDialog(){
      return (ImageView) activity.findViewById(R.id.image_arrow_dialog);
   }

   public Button getButtonChoice1(){
      return (Button) activity.findViewById(R.id.button_option1);
   }

   public Button getButtonChoice2(){
      return (Button) activity.findViewById(R.id.button_option2);
   }

   public Animation getButtonAnimation(){
      Animation anim = new AlphaAnimation(0.9f, 0.5f);
      anim.setDuration(700);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }
}
