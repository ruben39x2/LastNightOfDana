package ember.ash.lastnightofdana.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;

public class FontsOverride {
   public static void overrideTypefaces(Activity activity){
      try {
         ((TextView) activity.findViewById(R.id.text_headphones))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), "crimefighter.ttf"));
      } catch (Exception e){ // Save our ass from the evil if something fails loading fonts
         e.printStackTrace();
      }
   }
}
