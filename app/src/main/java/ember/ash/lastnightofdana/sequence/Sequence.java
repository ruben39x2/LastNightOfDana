package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.game.Game;
import ember.ash.lastnightofdana.game.ViewsHolder;

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

   public Sequence(SequenceEnum type){
      this.type = type;
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
      final ViewGroup parentViewGroup = (ViewGroup) ViewsHolder.getInstance().getViewFather();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            for (int i = 0; i < parentViewGroup.getChildCount(); i++) {
               View view = parentViewGroup.getChildAt(i);
               view.setClickable(true);
               view.setVisibility(View.GONE);
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
      ViewsHolder.getInstance().getImageHeadphones().setVisibility(View.VISIBLE);
      ViewsHolder.getInstance().getImageHeadphones().startAnimation(fade);
      ViewsHolder.getInstance().getTextHeadphones().setVisibility(View.VISIBLE);
      ViewsHolder.getInstance().getTextHeadphones().startAnimation(fade);
   }

   private void hideHeadphonesAlert(){
      final View imageHeadphones = ViewsHolder.getInstance().getImageHeadphones();
      final TextView textHeadphones = ViewsHolder.getInstance().getTextHeadphones();
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
            imageHeadphones.setVisibility(View.GONE);
            textHeadphones.setVisibility(View.GONE);
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
            Game.getInstance().getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  notifyListener();
               }
            });
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
      Animation animation = AnimationUtils.loadAnimation(Game.getInstance().getActivity(), android.R.anim.slide_in_left);
      animation.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      view.startAnimation(animation);
   }

   private void narrateText(){
      ViewsHolder.getInstance().getTextNarrate().setVisibility(View.VISIBLE);
      ViewsHolder.getInstance().getTextNarrate().setText(text);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(500);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            ViewsHolder.getInstance().getArrowMiddle().setVisibility(View.VISIBLE);
            ViewsHolder.getInstance().getArrowMiddle().startAnimation(getIntermitentAnimation());
            notifyWaitingNarration();
         }
      });
      ViewsHolder.getInstance().getTextNarrate().startAnimation(fade);
   }

   private void playBackgroundMusic(){
      Game.getInstance().playMusic(musicId, looping);
      notifyListener();
   }

   private void stopBackgroundMusic(){
      Game.getInstance().stopMusic();
      notifyListener();
   }

   private void playDoorSound(){
      Game.getInstance().playDoor();
      notifyListener();
   }

   private void dialogText(){
      final TextView textDialog = ViewsHolder.getInstance().getDialogText();
      final ImageView imageArrow = ViewsHolder.getInstance().getArrowDialog();
      final View layoutDialog = ViewsHolder.getInstance().getLayoutDialogText();
      textDialog.setText("");
      layoutDialog.setVisibility(View.VISIBLE);
      new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 1; i <= text.length(); i++){
               final int stringIndexToShow = i;
               Game.getInstance().getActivity().runOnUiThread(new Runnable() {
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
            Game.getInstance().getActivity().runOnUiThread(new Runnable() {
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
      ViewsHolder.getInstance().getButtonChoice1().setText(choice1);
      ViewsHolder.getInstance().getButtonChoice1().setVisibility(View.VISIBLE);
      ViewsHolder.getInstance().getButtonChoice1().startAnimation(
              ViewsHolder.getInstance().getButtonAnimation());
      ViewsHolder.getInstance().getButtonChoice2().setText(choice2);
      ViewsHolder.getInstance().getButtonChoice2().setVisibility(View.VISIBLE);
      ViewsHolder.getInstance().getButtonChoice2().startAnimation(
              ViewsHolder.getInstance().getButtonAnimation());
      notifyListener();
   }

   private void hideChoices(){
      ViewsHolder.getInstance().getButtonChoice1().clearAnimation();
      ViewsHolder.getInstance().getButtonChoice2().clearAnimation();
      ViewsHolder.getInstance().getButtonChoice1().setVisibility(View.GONE);
      ViewsHolder.getInstance().getButtonChoice2().setVisibility(View.GONE);
      notifyListener();
   }

   private Animation getIntermitentAnimation(){
      Animation anim = new AlphaAnimation(0f, 1f);
      anim.setDuration(250);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }
}
