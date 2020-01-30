import java.awt.Image;

public class SpritePackage implements Position {
   
   private String filename;
   private int x;
   private int y;
   private int sizeMultiplierX;
   private int sizeMultiplierY;
   private int layer;
   
   public SpritePackage(int iniLayer, String iniFilename, int iniX, int iniY, int iniMulX, int iniMulY) {
      filename = iniFilename;
      x = iniX;
      y = iniY;
      sizeMultiplierX = iniMulX;
      sizeMultiplierY = iniMulY;
      layer = iniLayer;
   }
   public SpritePackage(int iniLayer, String iniFilename, int iniX, int iniY, int iniMul) {
      this(iniLayer, iniFilename, iniX, iniY, iniMul, iniMul);
   }
   public SpritePackage(int iniLayer, String iniFilename, int iniX, int iniY) {
      this(iniLayer, iniFilename, iniX, iniY, 1);
   }
   public SpritePackage(String iniFilename, int iniX, int iniY, int iniMul) {
      this(0, iniFilename, iniX, iniY, iniMul, iniMul);
   }
   public SpritePackage(String iniFilename, int iniX, int iniY) {
      this(0, iniFilename, iniX, iniY, 1);
   }
   
   public double[] getCoords() {
      return new double[] {(double) x, (double) y};
   }
   
   public String getFilename() {
      return filename;
   }
   
   public int[] getSizeMultipliers() {
      return new int[] {sizeMultiplierX, sizeMultiplierY};
   }
   
   public int getWidthMultiplier() {
      return sizeMultiplierX;
   }
   
   public int getHeightMultiplier() {
      return sizeMultiplierY;
   }
   
   public int getLayer() {
      return layer;
   }
   
   public Image getImage() {
      return Client.sprite(filename);
   }
}