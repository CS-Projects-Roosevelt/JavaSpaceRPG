public interface Input {
   public boolean getPressed(int value);
   public boolean getPressedNow(int value);
   public boolean getReleasedNow(int value);
   public void tick();
}