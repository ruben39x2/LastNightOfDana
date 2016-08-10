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
import ember.ash.lastnightofdana.sequence.FadeAllToBlackSequence;
import ember.ash.lastnightofdana.sequence.SequenceQueue;

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
   private int idClickSound, idDoorSound, idDuruSound;
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
      queue.removeAllAndStop();
   }

   private void loadSounds(){
      soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
      idClickSound = soundPool.load(activity, R.raw.click, 0);
      idDoorSound = soundPool.load(activity, R.raw.door, 0);
      idDuruSound = soundPool.load(activity, R.raw.duruduru, 0);
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

   public int getIdDoorSound(){
      return idDoorSound;
   }

   public int getIdDuruSound() {
      return idDuruSound;
   }

   public void playClick(){
      soundPool.play(idClickSound, 1, 1, 1, 0, 1);
   }

   public void playSound(int id){
      soundPool.play(id, 1, 1, 1, 0, 1);
   }

   public void start(){
      getTextName().setText(R.string.dana);
      playScene(SceneEnum.DANA_INTRO);
      playScene(SceneEnum.DANA_TALKING_MAID);
   }

   /**
    * the heart of the game.
    *
    * @param scene the scene to play
    */
   public void playScene(SceneEnum scene){
      if (scene == null) {
         Toast.makeText(activity, "There was an unexpected error :(", Toast.LENGTH_SHORT).show();
         return;
      }
      currentScene = scene; // assign the current scene
      switch (scene){
         case DANA_INTRO: Scenes.enqueueDanaIntro(this, queue); break;
         case DANA_TALKING_MAID: Scenes.enqueueDanaMaid(this, queue); break;
         case DANA_SEE_HAYMITCH: Scenes.enqueueDanaSeeHaymitch(this, queue); break;
         case DANA_TALK_HAYMITCH1: Scenes.enqueueDanaTalkToHaymitch1(this, queue); break;
         case DANA_AVOID_HAYMITCH: Scenes.enqueueDanaAvoidHaymitch(this, queue); break;
         case DANA_SLEEPING: Scenes.enqueueDanaSleeping(this, queue); break;
         case DANA_GOES_TO_BED: Scenes.enqueueDanaGoesToBedAgain(this, queue); break;
         case DANA_GOES_TO_TOWN: Scenes.enqueueDanaGoesToTown(this, queue); break;
      }
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
      return null;//(RelativeLayout) activity.findViewById(R.id.layout_stars);
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
