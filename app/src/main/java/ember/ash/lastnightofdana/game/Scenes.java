package ember.ash.lastnightofdana.game;

import ember.ash.lastnightofdana.R;
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
      queue.addSequence(new FadeAllToBlackSequence(1000, game));
   }

   public static void enqueueDanaMaid(Game game, SequenceQueue queue){
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
   }
}
