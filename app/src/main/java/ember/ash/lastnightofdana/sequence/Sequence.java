package ember.ash.lastnightofdana.sequence;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.game.Game;

/**
 * This object represents a playable object. The sequence will be played when
 * the play() method is called and will trigger the listener when the sequence
 * finishes
 */
public class Sequence {
   private SequenceEnum type;
   private SequenceListener listener;
   private boolean listenerNotified = false;
   private long duration;
   private View view;
   private int imageResource;
   private String text, choice1, choice2;
   private int musicId;
   private boolean looping, asynchronous;
   private Game game;

   public Sequence(SequenceEnum type, Game game){
      this.type = type;
      this.game = game;
   }

   public Sequence setDuration(long duration){
      this.duration = duration;
      return this;
   }

   public Sequence setView(View view){
      this.view = view;
      return this;
   }

   public Sequence setImageResource(int imageResource){
      this.imageResource = imageResource;
      return this;
   }

   public Sequence setText(String text){
      this.text = text;
      return this;
   }

   public Sequence setMusicId(int musicId){
      this.musicId = musicId;
      return this;
   }

   public Sequence setLooping(boolean looping){
      this.looping = looping;
      return this;
   }

   public Sequence setAsynchronous(){
      this.asynchronous = true;
      return this;
   }

   public Sequence setChoice1(String choice1){
      this.choice1 = choice1 + " "; // hotfix for the win
      return this;
   }

   public Sequence setChoice2(String choice2){
      this.choice2 = choice2 + " "; // problems with the font...
      return this;
   }

   public Sequence setGameObject(Game game){
      this.game = game;
      return this;
   }

   public void setListener(SequenceListener listener){
      this.listener = listener;
   }

   public void play(){
      switch (type){
         case FADE_ALL_TO_BLACK:
            fadeAllToBlack();
            break;
         case SHOW_HEADPHONES_ALERT:
            showHeadphonesAlert();
            break;
         case HIDE_HEADPHONES_ALERT:
            hideHeadphonesAlert();
            break;
         case WAIT:
            waitBro();
            break;
         case FADE_VIEW_OUT:
            fadeViewOut();
            break;
         case SET_IMAGE:
            setImage();
            break;
         case FADE_VIEW_IN:
            fadeViewIn();
            break;
         case SLIDE_VIEW_IN:
            slideInView();
            break;
         case NARRATE_TEXT:
            narrateText();
            break;
         case PLAY_BACKGROUND_MUSIC:
            playBackgroundMusic();
            break;
         case STOP_BACKGROUND_MUSIC:
            stopBackgroundMusic();
            break;
         case PLAY_DOOR_SOUND:
            playDoorSound();
            break;
         case DIALOG_TEXT:
            dialogText();
            break;
         case SHOW_CHOICES:
            showChoices();
            break;
         case HIDE_CHOICES:
            hideChoices();
            break;
         case CROSSFADE_VIEW:
            crossfadeView();
            break;
      }
   }

   /**
    * This method is always called only once by each instance of Sequence, even if
    * multiple animation listeners call it.
    */
   private synchronized void notifyListener(){
      if (!listenerNotified){
         if (listener != null){
            listener.onSequenceFinished();
         }
         listenerNotified = true;
      }
   }

   private synchronized void notifyWaitingNarration(){
      if (!listenerNotified){
         if (listener != null){
            listener.onSequenceWaitingForNarration();
         }
         listenerNotified = true;
      }
   }

   private synchronized void notifyWaitingDialog(){
      if (!listenerNotified){
         if (listener != null){
            listener.onSequenceWaitingForDialog();
         }
         listenerNotified = true;
      }
   }

   private void fadeAllToBlack(){
      final ViewGroup parentViewGroup = game.getLayoutFather();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            for (int i = 0; i < parentViewGroup.getChildCount(); i++) {
               View view = parentViewGroup.getChildAt(i);
               view.setClickable(true);
               view.setVisibility(View.GONE);
               //if (view instanceof ImageView) ((ImageView)view).setImageDrawable(null); // clean memory
            }
            notifyListener();
         }
      });

      // Make nothing clickable and animate it
      for (int i = 0; i < parentViewGroup.getChildCount(); i++){
         if (parentViewGroup.getChildAt(i).getVisibility() == View.VISIBLE) {
            parentViewGroup.getChildAt(i).setClickable(false);
            parentViewGroup.getChildAt(i).startAnimation(fade);
         }
      }
   }

   private void showHeadphonesAlert(){
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      game.getImageHeadphones().setImageResource(R.drawable.earphones);
      game.getImageHeadphones().setVisibility(View.VISIBLE);
      game.getImageHeadphones().startAnimation(fade);
      game.getTextHeadphones().setVisibility(View.VISIBLE);
      game.getTextHeadphones().startAnimation(fade);
   }

   private void hideHeadphonesAlert(){
      final ImageView imageHeadphones = game.getImageHeadphones();
      final TextView textHeadphones = game.getTextHeadphones();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            imageHeadphones.setVisibility(View.GONE);
            textHeadphones.setVisibility(View.GONE);
            imageHeadphones.setImageDrawable(null); //clean memory
            notifyListener();
         }
      });
      imageHeadphones.startAnimation(fade);
      textHeadphones.startAnimation(fade);
   }

   private void waitBro(){
      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(duration);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            if (game.getActivity() != null) {
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     notifyListener();
                  }
               });
            }
         }
      }).start();
   }

   private void fadeViewOut(){
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            if (view instanceof ImageView) {
               ((ImageView) view).setImageDrawable(null);
            }
            view.setVisibility(View.GONE);
            notifyListener();
         }
      });
      view.startAnimation(fade);
      if (asynchronous) notifyListener();
   }

   private void setImage(){
      ((ImageView)view).setImageResource(imageResource);
      notifyListener();
   }

   private void fadeViewIn(){
      view.setVisibility(View.VISIBLE);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      view.startAnimation(fade);
   }

   private void slideInView(){
      view.setVisibility(View.VISIBLE);
      Animation animation = AnimationUtils.loadAnimation(game.getActivity(), android.R.anim.slide_in_left);
      animation.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      view.startAnimation(animation);
   }

   private void narrateText(){
      game.getTextNarrate().setVisibility(View.VISIBLE);
      game.getTextNarrate().setText(text);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(500);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            game.getArrowMiddle().setVisibility(View.VISIBLE);
            game.getArrowMiddle().startAnimation(getIntermitentAnimation());
            notifyWaitingNarration();
         }
      });
      game.getTextNarrate().startAnimation(fade);
   }

   private void playBackgroundMusic(){
      game.playMusic(musicId, looping);
      notifyListener();
   }

   private void stopBackgroundMusic(){
      game.stopMusic();
      notifyListener();
   }

   private void playDoorSound(){
      game.playDoor();
      notifyListener();
   }

   private void dialogText(){
      final TextView textDialog = game.getDialogText();
      final ImageView imageArrow = game.getArrowDialog();
      final View layoutDialog = game.getLayoutDialogText();
      textDialog.setText("");
      layoutDialog.setVisibility(View.VISIBLE);
      new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 1; i <= text.length(); i++){
               final int stringIndexToShow = i;
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     textDialog.setText(text.substring(0, stringIndexToShow));
                  }
               });
               try {
                  Thread.sleep(35);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
            game.getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  imageArrow.setVisibility(View.VISIBLE);
                  imageArrow.startAnimation(getIntermitentAnimation());
               }
            });
            notifyWaitingDialog();
         }
      }).start();
   }

   private void showChoices() {
      game.getButtonChoice1().setText(choice1);
      game.getButtonChoice1().setVisibility(View.VISIBLE);
      game.getButtonChoice1().startAnimation(
              game.getButtonAnimation());
      game.getButtonChoice2().setText(choice2);
      game.getButtonChoice2().setVisibility(View.VISIBLE);
      game.getButtonChoice2().startAnimation(
              game.getButtonAnimation());
      notifyListener();
   }

   private void hideChoices(){
      game.getButtonChoice1().clearAnimation();
      game.getButtonChoice2().clearAnimation();
      game.getButtonChoice1().setVisibility(View.GONE);
      game.getButtonChoice2().setVisibility(View.GONE);
      notifyListener();
   }

   private void crossfadeView(){
      Drawable[] images = new Drawable[2];
      images[0] = ((ImageView) view).getDrawable();
      ((ImageView) view).setImageDrawable(null); // clear the content of the imageview to optimize memory
      images[1] = ContextCompat.getDrawable(game.getActivity(), imageResource);

      TransitionDrawable crossfader = new TransitionDrawable(images);
      ((ImageView) view).setImageDrawable(crossfader);

      crossfader.setCrossFadeEnabled(true);
      crossfader.startTransition((int) duration);

      // ... and wait manually for the end (there is no onCompletionListener for transitionDrawable)
      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(duration);
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     notifyListener();
                  }
               });
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }

   private Animation getIntermitentAnimation(){
      Animation anim = new AlphaAnimation(0f, 1f);
      anim.setDuration(250);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }
}
