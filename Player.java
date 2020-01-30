import java.util.ArrayList;

public class Player implements Position, Actor {
   private double x;
   private double y;
   
   public Player(double iniX, double iniY) {
      x = iniX;
      y = iniY;
   }
   
   public boolean tick (Hitbox[] hitboxes, InputMapper input, ArrayList<Hitbox> newHitboxes, ArrayList<SpritePackage> spritesToDraw, Camera camera) {
      if (input.pressed("right")) {
         x += 1;
      }
      if (input.pressed("left")) {
         x -= 1;
      }
      if (input.pressed("up")) {
         y -= 1;
      }
      if (input.pressed("down")) {
         y += 1;
      }
      spritesToDraw.add(new SpritePackage(0, "testText.png", (int) x, (int) y));
      
      return true;
   }
   
   public double[] getCoords() {
      return new double[] {x, y};
   }
   
   public String getType() {
      return "Player";
   }
   
   public SpritePackage[] getSprites() {
      return new SpritePackage[0];
   }
}