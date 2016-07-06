package ember.ash.lastnightofdana.sequence;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import ember.ash.lastnightofdana.R;

/**
 * Created by Rubén Montero Vázquez on 06/07/2016
 */
public class Sequence {
   private SequenceEnum type;
   private Activity activity;
   private SequenceListener listener;

   public Sequence(SequenceEnum type, Activity activity){
      this.type = type;
      this.activity = activity;
   }

   public void setListener(SequenceListener listener){
      this.listener = listener;
   }

   public void play(){
      switch (type){
         case FADE_TO_BLACK:
            fadeAllToBlack(5000);
            break;
         case TEST:
            test();
            break;
      }
   }




   private void fadeAllToBlack(final long duration){
      final ViewGroup parentViewGroup = (ViewGroup) activity.findViewById(R.id.layout_father);
      Animation fadeToAlpha = new AlphaAnimation(1f, 0f);
      fadeToAlpha.setDuration(duration);

      // Make nothing clickable and animate it
      for (int i = 0; i < parentViewGroup.getChildCount(); i++){
         parentViewGroup.getChildAt(i).setClickable(false);
         parentViewGroup.getChildAt(i).startAnimation(fadeToAlpha);
      }

      // Wait 2 seconds and then make everything invisible to keep it... invisible... huh...
      new Thread(new Runnable() { // Typical russian thread
         @Override
         public void run() {
            try {
               Thread.sleep(duration);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            activity.runOnUiThread(new Runnable() { // Here we apply the desired invisibility
               @Override
               public void run() {
                  for (int i = 0; i < parentViewGroup.getChildCount(); i++){
                     parentViewGroup.getChildAt(i).setClickable(true);
                     parentViewGroup.getChildAt(i).setVisibility(View.INVISIBLE);
                  }
                  listener.onSequenceFinished();
               }
            });
         }
      }).start();
   }

   public void test(){
      String str = Math.random() + "hehe";
      Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
      listener.onSequenceFinished();
   }
}
