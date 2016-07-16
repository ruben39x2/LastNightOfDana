package ember.ash.lastnightofdana.game;

import android.util.Log;

import ember.ash.lastnightofdana.R;

public class GameLogic {
   public static SceneEnum decideSceneToPlay(SceneEnum currentScene, int choice){
      Log.d("AYAY", "currentScene" + currentScene);
      Log.d("AYAY", "choice" + choice);
      Log.d("AYAY", "op1" + R.id.button_option1);
      Log.d("AYAY", "op2" + R.id.button_option2);
      switch (currentScene){
         case DANA_TALKING_MAID:
            if (choice == R.id.button_option1) return SceneEnum.DANA_SEE_HAYMITCH;
            if (choice == R.id.button_option2) return SceneEnum.DANA_AVOID_HAYMITCH;
            break;
      }
      return null;
   }
}
