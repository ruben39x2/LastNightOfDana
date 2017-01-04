package ember.ash.lastnightofdana.game;

import ember.ash.lastnightofdana.R;
import ember.ash.lastnightofdana.sequence.ClearViewSequence;
import ember.ash.lastnightofdana.sequence.CrossfadeViewSequence;
import ember.ash.lastnightofdana.sequence.DialogTextSequence;
import ember.ash.lastnightofdana.sequence.FadeAllToBlackSequence;
import ember.ash.lastnightofdana.sequence.FadeViewInSequence;
import ember.ash.lastnightofdana.sequence.FadeViewOutSequence;
import ember.ash.lastnightofdana.sequence.HideChoicesSequence;
import ember.ash.lastnightofdana.sequence.HideEarphonesAlertSequence;
import ember.ash.lastnightofdana.sequence.NarrateTextSequence;
import ember.ash.lastnightofdana.sequence.PlayBackgroundMusicSequence;
import ember.ash.lastnightofdana.sequence.PlaySoundSequence;
import ember.ash.lastnightofdana.sequence.SequenceQueue;
import ember.ash.lastnightofdana.sequence.SetImageSequence;
import ember.ash.lastnightofdana.sequence.ShowChoicesSequence;
import ember.ash.lastnightofdana.sequence.ShowEarphonesAlertSequence;
import ember.ash.lastnightofdana.sequence.SlideViewInSequence;
import ember.ash.lastnightofdana.sequence.StopBackgroundMusicSequence;
import ember.ash.lastnightofdana.sequence.WaitForClickSequence;
import ember.ash.lastnightofdana.sequence.WaitSequence;

public class Scenes {
   public static void enqueueDanaIntro(Game game, SequenceQueue queue){
      queue.addSequence(new FadeAllToBlackSequence(2000, game));
      queue.addSequence(new ShowEarphonesAlertSequence(400, game));
      queue.addSequence(new WaitSequence(2000, game));
      queue.addSequence(new HideEarphonesAlertSequence(600, game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new PlayBackgroundMusicSequence(R.raw.music_dana, false, game));
      queue.addSequence(new SetImageSequence(R.drawable.mansion, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(2000, game.getImageBackground()));
      queue.addSequence(new SetImageSequence(R.drawable.dana1, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(600, game.getImageMiddleBar()));
      queue.addSequence(new SlideViewInSequence(game.getTextName(), game));
      queue.addSequence(new WaitSequence(1500, game));
      queue.addSequence(new FadeViewOutSequence(1500, game.getImageBackground()));
      queue.addSequence(new WaitSequence(500, game));
      game.playScene(SceneEnum.DANA_TALKING_MAID);
   }

   public static void enqueueDanaMaid(Game game, SequenceQueue queue){
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
      queue.addSequence(new NarrateTextSequence(game.getActivity().getString(R.string.dana_intro1), game));
      queue.addSequence(new NarrateTextSequence(game.getActivity().getString(R.string.dana_intro2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getTextNarrate()));
      queue.addSequence(new SetImageSequence(R.drawable.restroom, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(1500, game.getImageBackground()));
      queue.addSequence(new StopBackgroundMusicSequence(game));
      queue.addSequence(new PlayBackgroundMusicSequence(R.raw.music_strings1, true, game));
      queue.addSequence(new SetImageSequence(R.drawable.dana_lying_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(1500, game.getImageLayer1()));
      queue.addSequence(new WaitSequence(2000, game));
      queue.addSequence(new PlaySoundSequence(game.getIdDoorSound(), game));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new SetImageSequence(R.drawable.maid, game.getImageLayer2()));
      queue.addSequence(new FadeViewInSequence(1000, game.getImageLayer2()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro3), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro4), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1000, game));
      // huhu
      //queue.addSequence(new CrossfadeViewSequence(500, game.getImageLayer1(), R.drawable.dana_sitting_bed, game));
      // Crossfade Here would be great =)
      queue.addSequence(new FadeViewOutSequence(300, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.dana_sitting_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(300, game.getImageLayer1()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro5), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro6), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro7), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro8), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro9), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_intro10), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(700, game));
      queue.addSequence(new ShowChoicesSequence(
              game.getActivity().getString(R.string.see_him),
              game.getActivity().getString(R.string.avoid_him),
              game));
   }

   public static void enqueueDanaSeeHaymitch(Game game, SequenceQueue queue){
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_see_haymitch1), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_see_haymitch2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeViewOutSequence(1000, game.getImageLayer2()));
      queue.addSequence(new PlaySoundSequence(game.getIdDoorSound(), game));
      queue.addSequence(new WaitSequence(500, game));
      game.playScene(SceneEnum.DANA_SALUTES_HAYMITCH);
   }

   public static void enqueueDanaAvoidHaymitch(Game game, SequenceQueue queue){
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new FadeViewOutSequence(400, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.dana_lying_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(400, game.getImageLayer1()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_avoid_haymitch1), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_avoid_haymitch2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_avoid_haymitch3), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeViewOutSequence(1000, game.getImageLayer2()));
      queue.addSequence(new PlaySoundSequence(game.getIdDoorSound(), game));
      queue.addSequence(new WaitSequence(2000, game));
      game.playScene(SceneEnum.DANA_SLEEPING);
   }

   public static void enqueueDanaSleeping(Game game, SequenceQueue queue){
      queue.addSequence(new WaitForClickSequence(game.getImageLayer1()));
      queue.addSequence(new PlaySoundSequence(game.getIdDuruSound(), game));
      queue.addSequence(new FadeViewOutSequence(300, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.dana_sitting_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(300, game.getImageLayer1()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_sleeping1), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_sleeping2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(700, game));
      queue.addSequence(new ShowChoicesSequence(
              game.getActivity().getString(R.string.wake_up),
              game.getActivity().getString(R.string.sleep),
              game));
   }

   public static void enqueueDanaGoesToBedAgain(Game game, SequenceQueue queue){
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new FadeViewOutSequence(300, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.dana_lying_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(300, game.getImageLayer1()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_to_bed1), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_to_bed2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      game.playScene(SceneEnum.DANA_SLEEPING);
   }

   public static void enqueueDanaGoesToTown(Game game, SequenceQueue queue) {
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_to_town1), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1500, game));
      queue.addSequence(new PlaySoundSequence(game.getIdStepsSound(), game));
      queue.addSequence(new FadeAllToBlackSequence(500, game));
      game.playScene(SceneEnum.DANA_GOES_TO_TOWN_ENTRY);
   }

   public static void enqueueDanaSalutesHaymitch(Game game, SequenceQueue queue){
      queue.addSequence(new FadeAllToBlackSequence(800, game));
      queue.addSequence(new StopBackgroundMusicSequence(game));
      queue.addSequence(new SetImageSequence(R.drawable.pasilloexteriorhaymitch, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageBackground()));
      queue.addSequence(new WaitSequence(600, game));
      queue.addSequence(new PlayBackgroundMusicSequence(R.raw.music_charming, true, game));
      queue.addSequence(new WaitSequence(2000, game));
      queue.addSequence(new PlaySoundSequence(game.getIdDoorSound2(), game));
      queue.addSequence(new WaitSequence(2300, game));
      queue.addSequence(new SetImageSequence(R.drawable.danapasillo1, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageLayer1()));
      queue.addSequence(new WaitSequence(600, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch1), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(600, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new CrossfadeViewSequence(200, game.getImageLayer1(), R.drawable.danapasillo2, game));
      queue.addSequence(new ClearViewSequence(game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.pasilloexteriordana, game.getImageBackground()));
      queue.addSequence(new SetImageSequence(R.drawable.haymitchpasillo1, game.getImageLayer1()));
      queue.addSequence(new WaitSequence(700, game));
      queue.addSequence(new CrossfadeViewSequence(200, game.getImageLayer1(), R.drawable.haymitchpasillo2, game));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new FadeViewOutSequence(300, game.getImageBackground()));
      queue.addSequence(new FadeViewOutSequence(300, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.besomano3, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageLayer1()));
      queue.addSequence(new CrossfadeViewSequence(400, game.getImageLayer1(), R.drawable.besomano4, game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new FadeAllToBlackSequence(500, game));
      game.playScene(SceneEnum.DANA_TALK_TO_HAYMITCH_1);

   }

   public static void enqueueDanaTalkToHaymitch1(Game game, SequenceQueue queue){
      queue.addSequence(new SetImageSequence(R.drawable.haymitchpasillo1, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(1000, game.getImageLayer1()));
      queue.addSequence(new SetImageSequence(R.drawable.danapasillo1, game.getImageLayer2()));
      queue.addSequence(new FadeViewInSequence(300, game.getImageLayer2()));
      queue.addSequence(new SetImageSequence(R.drawable.pasilloexterior, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(300, game.getImageBackground()));
      queue.addSequence(new WaitSequence(300, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch3), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch4), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch5), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch6), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch7), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch8), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch9), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch10), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch11), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(300, game));
      queue.addSequence(new ShowChoicesSequence(
              game.getActivity().getString(R.string.id_love_it),
              game.getActivity().getString(R.string.sorry_i_cant),
              game));
   }

   public static void enqueueDanaWalkWithHaymitch(Game game, SequenceQueue queue){
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch12), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_talk_haymitch13), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeAllToBlackSequence(1500, game));
      queue.addSequence(new SetImageSequence(R.drawable.paseo, game.getImageBackground()));
      queue.addSequence(new PlaySoundSequence(game.getIdStepsSound(), game));
      queue.addSequence(new FadeViewInSequence(3000, game.getImageBackground()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new SetImageSequence(R.drawable.danayhaymitchpaseo, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(500, game.getImageLayer1()));

      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch1), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch3), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch4), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch5), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch6), game));
      queue.addSequence(new PlaySoundSequence(game.getIdBirdsSound(), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(2000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch7), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(1000, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch8), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch9), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch10), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch11), game));
      queue.addSequence(new PlaySoundSequence(game.getIdBirdsSound(), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new FadeAllToBlackSequence(2000, game));
      queue.addSequence(new SetImageSequence(R.drawable.haymitch1, game.getImageLayer1()));
      queue.addSequence(new StopBackgroundMusicSequence(game));
      queue.addSequence(new FadeViewInSequence(300, game.getImageLayer1()));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch12), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
      queue.addSequence(new PlaySoundSequence(game.getIdStepsSound(), game));
      queue.addSequence(new WaitSequence(5000, game));
      queue.addSequence(new SetImageSequence(R.drawable.restroom, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(500, game.getImageBackground()));
      queue.addSequence(new PlaySoundSequence(game.getIdDoorSound(), game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new SetImageSequence(R.drawable.dana_sitting_bed, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(500, game.getImageLayer1()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_walk_haymitch13), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
      // enqueue feast
   }

   public static void enqueueDanaGoesToTownTransition(Game game, SequenceQueue queue){
      queue.addSequence(new HideChoicesSequence(game));
      queue.addSequence(new WaitSequence(500, game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_transition1), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_transition2), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_transition3), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_transition4), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeViewOutSequence(500, game.getImageLayer1()));
      queue.addSequence(new PlaySoundSequence(game.getIdStepsSound(), game));
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
      game.playScene(SceneEnum.DANA_GOES_TO_TOWN_ENTRY);
   }

   public static void enqueueDanaGoesToTownEntry(Game game, SequenceQueue queue){
      queue.addSequence(new StopBackgroundMusicSequence(game));
      queue.addSequence(new SetImageSequence(R.drawable.mansion2, game.getImageBackground()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageBackground()));
      queue.addSequence(new SetImageSequence(R.drawable.dana1, game.getImageLayer1()));
      queue.addSequence(new FadeViewInSequence(800, game.getImageLayer1()));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_entry1), game));
      queue.addSequence(new DialogTextSequence(game.getActivity().getString(R.string.dana_goes_town_entry2), game));
      queue.addSequence(new FadeViewOutSequence(500, game.getLayoutDialogText()));
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
      // play dana town
   }

}
