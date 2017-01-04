package ember.ash.lastnightofdana.game;

import ember.ash.lastnightofdana.R;

public class GameLogic {
   public static SceneEnum decideSceneToPlay(SceneEnum currentScene, int choice){
      switch (currentScene){
         case DANA_TALKING_MAID:
            if (choice == R.id.button_option1) return SceneEnum.DANA_SEE_HAYMITCH;
            if (choice == R.id.button_option2) return SceneEnum.DANA_AVOID_HAYMITCH;
            break;
         case DANA_SLEEPING:
            if (choice == R.id.button_option1) return SceneEnum.DANA_GOES_TO_TOWN;
            if (choice == R.id.button_option2) return SceneEnum.DANA_GOES_TO_BED;
            break;
         case DANA_TALK_TO_HAYMITCH_1:
            if (choice == R.id.button_option1) return SceneEnum.DANA_WALK_WITH_HAYMITCH;
            if (choice == R.id.button_option2) return SceneEnum.DANA_GOES_TO_TOWN_TRANSITION;
            break;
      }
      return null;
   }
}
