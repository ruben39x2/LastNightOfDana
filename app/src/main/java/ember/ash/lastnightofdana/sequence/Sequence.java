package ember.ash.lastnightofdana.sequence;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;
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
   private String text;

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
         case FADE_VIEW_TO_BLACK:
            fadeViewToBlack();
            break;
         case SET_IMAGE:
            setImage();
            break;
         case FADE_VIEW_IN:
            fadeInView();
            break;
         case SLIDE_VIEW_IN:
            slideInView();
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

   private void fadeViewToBlack(){
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
      final TextView textViewNarration = (TextView) Game.getInstance().getActivity().findViewById(R.id.text_middle_white);
      final ImageView imageArrow = (ImageView) Game.getInstance().getActivity().findViewById(R.id.image_arrow_middle);
      textViewNarration.setVisibility(View.VISIBLE);
      new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 1; i <= text.length(); i++){
               final int stringIndexToShow = i;
               Game.getInstance().getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     textViewNarration.setText(text.substring(0, stringIndexToShow));
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
