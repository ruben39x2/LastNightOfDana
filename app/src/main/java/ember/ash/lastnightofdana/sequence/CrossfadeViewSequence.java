package ember.ash.lastnightofdana.sequence;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import ember.ash.lastnightofdana.game.Game;

public class CrossfadeViewSequence extends Sequence {
   private SequenceListener listener;
   private View view;
   private long duration;
   private Game game;
   private int imageResource;

   public CrossfadeViewSequence(long duration, View view, int imageResource, Game game){
      this.duration = duration;
      this.view = view;
      this.imageResource = imageResource;
      this.game = game;
   }

   @Override
   public void play() {
      crossfadeView();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void crossfadeView(){
      Drawable[] images = new Drawable[2];
      images[0] = ((ImageView) view).getDrawable();
      ((ImageView) view).setImageDrawable(null); // clear the content of the imageview to optimize memory
      images[1] = ContextCompat.getDrawable(game.getActivity(), imageResource);

      TransitionDrawable crossfader = new TransitionDrawable(images);
      ((ImageView) view).setImageDrawable(crossfader);

      crossfader.setCrossFadeEnabled(true);
      crossfader.startTransition((int) duration);

      // ... and wait manually for the end (there is no onCompletionListener for transitionDrawable)
      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(duration);
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     notifyListener();
                  }
               });
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }

   private void notifyListener(){
      listener.onSequenceFinished();
   }
}
