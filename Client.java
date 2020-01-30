import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Client {
   
   public static boolean running = true;
   public static int tickCount = 0;
   public static Image[] sprites;
   public static String[] spriteFilenames;
   public static final int STARTING_LEVEL = 0;
   public static final int STARTING_ROOM = 0;
   public static final String SPRITE_FOLDER = "sprites";
   public static final int[] DIMENSIONS = {320, 240};
   
   public static void main(String[] args) throws FileNotFoundException {
      //create canvas
      DisplayCanvas canvas = new DisplayCanvas(DIMENSIONS[0], DIMENSIONS[1], 2, 2, "Space RPG");
      
      //set up sprites and spriteFilenames arrays
      File folder = new File(SPRITE_FOLDER);
      File[] spriteFiles = folder.listFiles();
      sprites = new Image[spriteFiles.length];
      spriteFilenames = new String[spriteFiles.length];
      for (int i = 0; i < spriteFiles.length; i++) {
         sprites[i] = ((new ImageIcon(SPRITE_FOLDER + "/" + spriteFiles[i].getName())).getImage());
         spriteFilenames[i] = spriteFiles[i].getName();
      }
      
      //set up input object
      InputMapper input = new InputMapper(new Input[]{canvas.getInputListener()});
      
      //create current Level and Room
      Level[] allLevels = createLevels();
      Level thisLevel = allLevels[STARTING_LEVEL];
      Room thisRoom = thisLevel.getRoom(STARTING_ROOM);
      
      //sets up camera
      Camera camera = new Camera(0, 0);
      
      //sets up empty hitbox array
      Hitbox[] hitboxes = new Hitbox[0];
      
      //set up tick interval
      int delay = 10/3; //milliseconds
      ActionListener taskPerformer = new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
             tick(canvas, canvas.getGraphics(), input, thisRoom, camera, hitboxes);
         }
      };
      new javax.swing.Timer(delay, taskPerformer).start();
   }
   
   public static boolean tick(DisplayCanvas canvas, Graphics g, InputMapper input, Room thisRoom, Camera camera, Hitbox[] hitboxes) {
      tickCount++;
      //System.out.println("tick");
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, canvas.getDimensions()[0], canvas.getDimensions()[1]);
      
      input.tick();
      
      ArrayList<Hitbox> newHitboxes = new ArrayList<Hitbox>();
      
      ArrayList<SpritePackage> spritesToDraw = new ArrayList<SpritePackage>();
      
      Actor[] actorArray = thisRoom.getActors();
      Actor[] lastActorArray;
      for (int a = 0; a < actorArray.length; a++) {
         actorArray[a].tick(hitboxes, input, newHitboxes, spritesToDraw, camera); //delete if tick returns false (add later)
      }
      
      hitboxes = newHitboxes.toArray(new Hitbox[newHitboxes.size()]);
      
      camera.tick();
      drawSpriteArray(canvas, g, spritesToDraw.toArray(new SpritePackage[spritesToDraw.size()]), camera);
      canvas.refresh();
      
      return true;
   }
   
   //creates and stores all levels
   public static Level[] createLevels() {
      return new Level[]{
         new Level(new Room[]{
            new Room(new Actor[]{
               new Player(0, 0)
            })
         })
      };
   }
   
   //draws sprite given camera positioning
   public static void drawSprite(DisplayCanvas canvas, Graphics g, String filename, int x, int y, int width, int height, Camera cam) {
      double[] camCoords = cam.getCoords();
      int[] camDisplace = new int[]{(int) camCoords[0], (int) camCoords[1]};
      if (Math.abs(camDisplace[0] - x) < (width + DIMENSIONS[0]) / 2 && Math.abs(camDisplace[1] - y) < (height + DIMENSIONS[1]) / 2) {
         camDisplace[0] = DIMENSIONS[0] / 2 - camDisplace[0];
         camDisplace[1] = DIMENSIONS[1] / 2 - camDisplace[1];
         Image img = sprite(filename);
         double[] zoomMultiplier = new double[] {canvas.getDimensions()[0] / DIMENSIONS[0] * cam.getZoom(), canvas.getDimensions()[1] / DIMENSIONS[1] * cam.getZoom()};
         int realX = (int) ((x + camDisplace[0] - Math.abs(width / 2)) * zoomMultiplier[0]);
         if (width < 0) {
            realX += width * -zoomMultiplier[0];
         }
         int realY = (int) ((y + camDisplace[1] - Math.abs(height / 2)) * zoomMultiplier[1]);
         if (height < 0) {
            realY += height * -zoomMultiplier[1];
         }
         g.drawImage(img, realX, realY, (int) (width * zoomMultiplier[0]), (int) (height * zoomMultiplier[1]), null);
      }
   }
   //draws sprite with default width & height
   public static void drawSprite(DisplayCanvas canvas, Graphics g, String filename, int x, int y, Camera cam) {
      drawSprite(canvas, g, filename, x, y, sprite(filename).getWidth(null), sprite(filename).getHeight(null), cam);
   }
   public static void drawSprite(DisplayCanvas canvas, Graphics g, SpritePackage sprt, Camera cam) {
      drawSprite(canvas, g, sprt.getFilename(), (int) sprt.getCoords()[0], (int) sprt.getCoords()[1], sprite(sprt.getFilename()).getWidth(null) * sprt.getWidthMultiplier(), sprite(sprt.getFilename()).getHeight(null) * sprt.getHeightMultiplier(), cam);
   }
   
   //returns an image object for a given filename from the sprites folder
   public static Image sprite(String filename) {
      for (int i = 0; i < spriteFilenames.length; i++) {
         if (spriteFilenames[i].equals(filename)) {
            return sprites[i];
         }
      }
      return sprite("null.png"); //default "null" sprite returned if filename has no matches
   }
   
   //prints several SpritePackages in an order based on their assigned layer value
   public static void drawSpriteArray(DisplayCanvas canvas, Graphics g, SpritePackage[] sprites, Camera cam) {
      if (sprites.length > 0) {
         int minLayer = sprites[0].getLayer();
         int maxLayer = minLayer;
         for (int i = 1; i < sprites.length; i++) {
            minLayer = Math.min(minLayer, sprites[i].getLayer());
            maxLayer = Math.max(maxLayer, sprites[i].getLayer());
         }
         for (int l = minLayer; l <= maxLayer; l++) {
            for (int i = 0; i < sprites.length; i++) {
               if (sprites[i].getLayer() == l) {
                  drawSprite(canvas, g, sprites[i], cam);
               }
            }
         }
      }
   }
}