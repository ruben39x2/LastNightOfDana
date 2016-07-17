package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.widget.ImageView;

public class SetImageSequence extends Sequence {
   private SequenceListener listener;
   private View view;
   private int imageResource;

   public SetImageSequence(int imageResource, View view){
      this.imageResource = imageResource;
      this.view = view;
   }

   @Override
   public void play() {
      setImage();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void setImage(){
      ((ImageView)view).setImageResource(imageResource);
      notifyListener();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
