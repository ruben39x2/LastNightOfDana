package ember.ash.lastnightofdana.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;

public class FontsOverride {
   public static void overrideTypefaces(Activity activity){
      ((TextView)activity.findViewById(R.id.text_headphones))
              .setTypeface(Typeface.createFromAsset(activity.getAssets(), "anime.ttf"));
   }
}
