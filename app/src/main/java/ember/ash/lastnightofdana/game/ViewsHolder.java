package ember.ash.lastnightofdana.game;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;

public class ViewsHolder {
   private static ViewsHolder ourInstance = new ViewsHolder();
   private View viewFather, imageHeadphones, imageBackground,
           imageLayer1, imageLayer2, imageLayer3,
           layoutDialogText, imageMiddleBar;
   private TextView textName, textHeadphones, textNarrate, textDialog;
   private ImageView imageArrowMiddle, imageArrowDialog;
   private Button choice1, choice2;

   public static ViewsHolder getInstance() {
      return ourInstance;
   }

   public static void clearViews(){
      ourInstance = new ViewsHolder();
   }

   private ViewsHolder() {
   }

   public View getViewFather(){
      if (viewFather == null){
         viewFather = Game.getInstance().getActivity().findViewById(R.id.layout_father);
      }
      return viewFather;
   }

   public TextView getTextName(){
      if (textName == null){
         textName = (TextView) Game.getInstance().getActivity().findViewById(R.id.text_player_name);
      }
      return textName;
   }

   public View getImageHeadphones(){
      if (imageHeadphones == null){
         imageHeadphones = Game.getInstance().getActivity().findViewById(R.id.image_headphones);
      }
      return imageHeadphones;
   }

   public TextView getTextHeadphones(){
      if (textHeadphones == null){
         textHeadphones = (TextView) Game.getInstance().getActivity().findViewById(R.id.text_headphones);
      }
      return textHeadphones;
   }


   public View getImageBackground(){
      if (imageBackground == null){
         imageBackground = Game.getInstance().getActivity().findViewById(R.id.image_background);
      }
      return imageBackground;
   }

   public View getImageLayer1(){
      if (imageLayer1 == null){
         imageLayer1 = Game.getInstance().getActivity().findViewById(R.id.image_layer1);
      }
      return imageLayer1;
   }

   public View getImageLayer2(){
      if (imageLayer2 == null){
         imageLayer2 = Game.getInstance().getActivity().findViewById(R.id.image_layer2);
      }
      return imageLayer2;
   }

   public View getImageLayer3(){
      if (imageLayer3 == null){
         imageLayer3 = Game.getInstance().getActivity().findViewById(R.id.image_layer3);
      }
      return imageLayer3;
   }

   public View getLayoutDialogText(){
      if (layoutDialogText == null){
         layoutDialogText = Game.getInstance().getActivity().findViewById(R.id.layout_dialog);
      }
      return layoutDialogText;
   }

   public TextView getDialogText(){
      if (textDialog == null){
         textDialog = (TextView) Game.getInstance().getActivity().findViewById(R.id.text_dialog);
      }
      return textDialog;
   }

   public View getImageMiddleBar(){
      if (imageMiddleBar == null){
         imageMiddleBar = Game.getInstance().getActivity().findViewById(R.id.image_middle_bar);
      }
      return imageMiddleBar;
   }

   public TextView getTextNarrate(){
      if (textNarrate == null){
         textNarrate = (TextView) Game.getInstance().getActivity().findViewById(R.id.text_middle_white);
      }
      return textNarrate;
   }

   public ImageView getArrowMiddle(){
      if (imageArrowMiddle == null){
         imageArrowMiddle = (ImageView) Game.getInstance().getActivity().findViewById(R.id.image_arrow_middle);
      }
      return imageArrowMiddle;
   }

   public ImageView getArrowDialog(){
      if (imageArrowDialog == null){
         imageArrowDialog = (ImageView) Game.getInstance().getActivity().findViewById(R.id.image_arrow_dialog);
      }
      return imageArrowDialog;
   }

   public Button getButtonChoice1(){
      if (choice1 == null){
         choice1 = (Button) Game.getInstance().getActivity().findViewById(R.id.button_option1);
      }
      return choice1;
   }

   public Button getButtonChoice2(){
      if (choice2 == null){
         choice2 = (Button) Game.getInstance().getActivity().findViewById(R.id.button_option2);
      }
      return choice2;
   }

   public Animation getButtonAnimation(){
      Animation anim = new AlphaAnimation(0.9f, 0.5f);
      anim.setDuration(700);
      anim.setRepeatCount(Animation.INFINITE);
      anim.setRepeatMode(Animation.REVERSE);
      return anim;
   }

}
