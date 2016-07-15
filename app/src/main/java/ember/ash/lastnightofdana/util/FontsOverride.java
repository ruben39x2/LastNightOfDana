package ember.ash.lastnightofdana.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;

public class FontsOverride {
   public static void overrideTypefaces(Activity activity){
      final String FONT = "crimefighter.ttf";
      try {
         ((TextView) activity.findViewById(R.id.text_middle_white))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
         ((TextView) activity.findViewById(R.id.text_headphones))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
         ((TextView) activity.findViewById(R.id.text_player_name))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
         ((TextView) activity.findViewById(R.id.text_dialog))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
         //((TextView) activity.findViewById(R.id.text_title))
           //      .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
         ((Button) activity.findViewById(R.id.button_play))
                 .setTypeface(Typeface.createFromAsset(activity.getAssets(), FONT));
      } catch (Exception e){ // Save our ass from the evil if something fails loading fonts
         e.printStackTrace();
      }
   }
}
