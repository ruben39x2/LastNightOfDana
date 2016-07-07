package ember.ash.lastnightofdana.sequence;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;

/**
 * This object represents a playable object. The sequence will be played when
 * the play() method is called and will trigger the listener when the sequence
 * finishes
 */
public class Sequence {
   private SequenceEnum type;
   private Activity activity;
   private SequenceListener listener;
   private boolean listenerNotified = false;
   private long duration;
   private View view;
   private int imageResource;
   private String text;


   public Sequence(SequenceEnum type, Activity activity){
      this.type = type;
      this.activity = activity;
      this.duration = 1000;
   }

   public Sequence(SequenceEnum type, Activity activity, long duration){
      this.type = type;
      this.activity = activity;
      this.duration = duration;
   }

   public Sequence(SequenceEnum type, View view, int imageResource){
      this.type = type;
      this.view = view;
      this.imageResource = imageResource;
   }

   public Sequence(SequenceEnum type, View view, long duration){
      this.type = type;
      this.view = view;
      this.duration = duration;
   }

   public Sequence(SequenceEnum type, Activity activity, String text){
      this.type = type;
      this.activity = activity;
      this.text = text;
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
         case FADE_FROM_BLACK:
            fadeFromBlack();
            break;
         case FADE_VIEW_TO_BLACK:
            fadeViewToBlack();
            break;
         case SET_IMAGE:
            setImage();
            break;
         case FADE_VIEW_IN:
            fadeInView();
            break;
         case EXPAND_TEXT_BAR:
            expandTextBar();
            break;
         case NARRATE_TEXT:
            narrateText();
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

   private synchronized void notifyWaiting(){
      if (!listenerNotified){
         if (listener != null){
            listener.onSequenceWaiting();
         }
         listenerNotified = true;
      }
   }

   private void fadeAllToBlack(){
      final ViewGroup parentViewGroup = (ViewGroup) activity.findViewById(R.id.layout_father);
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            for (int i = 0; i < parentViewGroup.getChildCount(); i++) {
               View view = parentViewGroup.getChildAt(i);
               view.setClickable(true);
               view.setVisibility(View.INVISIBLE);
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
      ImageView imageHeadphones = (ImageView) activity.findViewById(R.id.image_headphones);
      TextView textHeadphones = (TextView) activity.findViewById(R.id.text_headphones);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      imageHeadphones.setVisibility(View.VISIBLE);
      imageHeadphones.startAnimation(fade);
      textHeadphones.setVisibility(View.VISIBLE);
      textHeadphones.startAnimation(fade);
   }

   private void hideHeadphonesAlert(){
      final ImageView imageHeadphones = (ImageView) activity.findViewById(R.id.image_headphones);
      final TextView textHeadphones = (TextView) activity.findViewById(R.id.text_headphones);
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
            imageHeadphones.setVisibility(View.INVISIBLE);
            textHeadphones.setVisibility(View.INVISIBLE);
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
            activity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  notifyListener();
               }
            });
         }
      }).start();
   }

   private void fadeFromBlack(){
      ImageView imageBackground = (ImageView) activity.findViewById(R.id.image_background);
      Animation fade = new AlphaAnimation(0f, 1f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      imageBackground.setVisibility(View.VISIBLE);
      imageBackground.startAnimation(fade);
   }

   private void fadeViewToBlack(){
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            if (view instanceof ImageView) {
               ((ImageView) view).setImageDrawable(null);
            }
            view.setVisibility(View.INVISIBLE);
            notifyListener();
         }
      });
      view.startAnimation(fade);
   }

   private void setImage(){
      ((ImageView)view).setImageResource(imageResource);
      notifyListener();
   }

   private void fadeInView(){
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

   private void expandTextBar(){
      View imageBar = activity.findViewById(R.id.image_text_helper);
      Animation scale = new ScaleAnimation(
              0f, 1f, // Start and end values for the X axis scaling
              1f, 1f, // Start and end values for the Y axis scaling
              Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
              Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
      scale.setFillAfter(true); // Needed to keep the result of the animation
      scale.setDuration(duration);
      scale.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            notifyListener();
         }
      });
      imageBar.setVisibility(View.VISIBLE);
      imageBar.startAnimation(scale);
   }

   private void narrateText(){
      final TextView textViewNarration = (TextView) activity.findViewById(R.id.text_middle_white);
      final ImageView imageArrow = (ImageView) activity.findViewById(R.id.image_arrow_middle);
      textViewNarration.setVisibility(View.VISIBLE);
      new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 1; i <= text.length(); i++){
               final int stringIndexToShow = i;
               activity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     textViewNarration.setText(text.substring(0, stringIndexToShow));
                  }
               });
               try {
                  Thread.sleep(50);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
            activity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  imageArrow.setVisibility(View.VISIBLE);
                  imageArrow.startAnimation(getIntermitentAnimation());
               }
            });
            notifyWaiting();
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
