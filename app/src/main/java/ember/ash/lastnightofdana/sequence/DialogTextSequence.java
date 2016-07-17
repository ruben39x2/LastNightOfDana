package ember.ash.lastnightofdana.sequence;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.game.Game;

public class DialogTextSequence extends Sequence {
   private SequenceListener listener;
   private String text;
   private Game game;

   public DialogTextSequence(String text, Game game){
      this.text = text;
      this.game = game;
   }

   @Override
   public void play() {
      dialogText();
   }

   @Override
   public void setListener(SequenceListener listener) {
      this.listener = listener;
   }

   private void dialogText(){
      final TextView textDialog = game.getDialogText();
      final ImageView imageArrow = game.getArrowDialog();
      final View layoutDialog = game.getLayoutDialogText();
      textDialog.setText("");
      layoutDialog.setVisibility(View.VISIBLE);
      new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 1; i <= text.length(); i++){
               final int stringIndexToShow = i;
               game.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     textDialog.setText(text.substring(0, stringIndexToShow));
                  }
               });
               try {
                  Thread.sleep(35);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
            game.getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  imageArrow.setVisibility(View.VISIBLE);
                  imageArrow.startAnimation(getIntermitentAnimation());
               }
            });
            notifyWaitingDialog();
         }
      }).start();
   }

   private void notifyWaitingDialog(){
      listener.onSequenceWaitingForDialog();
   }

   private Animation getIntermitentAnimation(){
      Animation anim = new AlphaAnimation(0f, 1f);
      anim.setDuration(250);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }
}
