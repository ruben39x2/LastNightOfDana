package ember.ash.lastnightofdana.game;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import ember.ash.lastnightofdana.R;

public class ViewsHolder {
   private static ViewsHolder ourInstance = new ViewsHolder();
   private View viewFather;
   private TextView textName;
   private View imageHeadphones;
   private TextView textHeadphones;
   private View imageBackground;
   private View imageLayer1;

   private View imageMiddleBar;

   public static ViewsHolder getInstance() {
      return ourInstance;
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




   public View getImageMiddleBar(){
      if (imageMiddleBar == null){
         imageMiddleBar = Game.getInstance().getActivity().findViewById(R.id.image_middle_bar);
      }
      return imageMiddleBar;
   }


}
