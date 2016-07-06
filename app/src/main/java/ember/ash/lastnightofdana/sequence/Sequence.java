package ember.ash.lastnightofdana.sequence;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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


   public Sequence(SequenceEnum type, Activity activity){
      this.type = type;
      this.activity = activity;
      this.duration = 3000;
   }

   public Sequence(SequenceEnum type, Activity activity, long duration){
      this.type = type;
      this.activity = activity;
      this.duration = duration;
   }

   public void setListener(SequenceListener listener){
      this.listener = listener;
   }

   public void play(){
      switch (type){
         case FADE_TO_BLACK:
            fadeAllToBlack(duration);
            break;
         case SHOW_HEADPHONES_ALERT:
            showHeadphonesAlert(duration);
            break;
         case HIDE_HEADPHONES_ALERT:
            hideHeadphonesAlert(duration);
            break;
         case WAIT:
            waitBro(duration);
            break;
         case TEST:
            test();
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

   private void fadeAllToBlack(final long duration){
      final ViewGroup parentViewGroup = (ViewGroup) activity.findViewById(R.id.layout_father);
      Animation fade = new AlphaAnimation(1f, 0f);
      fade.setDuration(duration);
      fade.setAnimationListener(new AnimationEndListener() {
         @Override
         public void onAnimationEnd(Animation animation) {
            for (int i = 0; i < parentViewGroup.getChildCount(); i++){
               parentViewGroup.getChildAt(i).setClickable(true);
               parentViewGroup.getChildAt(i).setVisibility(View.INVISIBLE);
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

   private void showHeadphonesAlert(long duration){
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

   private void hideHeadphonesAlert(long duration){
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

   private void waitBro(final long duration){
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

   private void test(){
      String str = Math.random() + "hehe";
      Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
      notifyListener();
   }
}
