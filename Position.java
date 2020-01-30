public interface Position {
   public double[] getCoords();
   default public double[] getVelocity() {
      return new double[] {0, 0};
   }
   default public double[] getAcceleration() {
      return new double[] {0, 0};
   }
}