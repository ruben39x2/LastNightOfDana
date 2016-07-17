package ember.ash.lastnightofdana.game;

import ember.ash.lastnightofdana.R;

public class GameLogic {
   public static SceneEnum decideSceneToPlay(SceneEnum currentScene, int choice){
      switch (currentScene){
         case DANA_TALKING_MAID:
            if (choice == R.id.button_option1) return SceneEnum.DANA_SEE_HAYMITCH;
            if (choice == R.id.button_option2) return SceneEnum.DANA_AVOID_HAYMITCH;
            break;
      }
      return null;
   }
}
