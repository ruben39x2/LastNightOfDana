package ember.ash.lastnightofdana.sequence;

/**
 * This object represents a playable object. The sequence will be played when
 * the play() method is called and will trigger the listener when the sequence
 * finishes
 */
public abstract class Sequence {
   public abstract void setListener(SequenceListener listener);

   public abstract void play();
}
