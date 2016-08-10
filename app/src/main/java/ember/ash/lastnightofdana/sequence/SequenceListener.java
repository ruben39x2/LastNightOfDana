package ember.ash.lastnightofdana.sequence;

import android.view.View;

/**
 * Easy as f***
 */
public interface SequenceListener {
   void onSequenceFinished();

   void onSequenceWaitingForClick(View viewToClick);
}
