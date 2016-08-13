package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.widget.ImageView;

public class ClearViewSequence extends Sequence {
   private SequenceListener listener;
   private View view;

   public ClearViewSequence(View view){
      this.view = view;
   }

   @Override
   public void play() {
      clearView();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void clearView(){
      ((ImageView)view).setImageDrawable(null);
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
