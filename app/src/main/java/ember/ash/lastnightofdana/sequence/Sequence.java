package ember.ash.lastnightofdana.sequence;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import ember.ash.lastnightofdana.R;

/**
 * Created by Rubén Montero Vázquez on 06/07/2016
 */
public class Sequence {

   public static void fadeAllToBlack(final Activity activity, final long duration){
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
               }
            });
         }
      }).start();
   }

}
