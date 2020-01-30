import java.util.ArrayList;

public interface Actor {
   public String getType(); //obsolete?
   public SpritePackage[] getSprites(); //obsolete?
   public boolean tick(Hitbox[] hitboxes, InputMapper input, ArrayList<Hitbox> newHitboxes, ArrayList<SpritePackage> spritesToDraw, Camera camera);
}