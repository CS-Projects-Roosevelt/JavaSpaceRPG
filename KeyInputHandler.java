import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

public class KeyInputHandler implements Input, KeyListener {
   boolean[] keyBooleans;
   int[] keyInts;
   
   public KeyInputHandler() {
      keyBooleans = new boolean[0x100];
      keyInts = new int[0x100];
   }
   
   @Override
   public void keyTyped(KeyEvent e) {
   }
   
   @Override
   public void keyPressed(KeyEvent e) {
      //System.out.println("pressed " + e.getKeyCode());
      keyBooleans[e.getKeyCode()] = true;
   }
   
   @Override
   public void keyReleased(KeyEvent e) {
      //System.out.println("released " + e.getKeyCode());
      keyBooleans[e.getKeyCode()] = false;
   }
   
   //returns full list of all key states represented by ints -1 through 2
   public int[] getKeyArray() {
      return keyInts;
   }
   
   //updates keyInt array every tick
   public void tick() {
      for (int i = 0; i < keyInts.length; i++) {
         if (keyInts[i] <= 0 && keyBooleans[i]) {
            keyInts[i] = 2;
         }
         else if (keyInts[i] >= 1 && keyBooleans[i]) {
            keyInts[i] = 1;
         }
         else if (keyInts[i] >= 1 && !keyBooleans[i]) {
            keyInts[i] = -1;
         }
         else {
            keyInts[i] = 0;
         }
      }
   }
   
   //returns true if a key of a given value is pressed, false if not
   public boolean getPressed(int value) {
      return keyInts[value] > 0;
   }
   
   //return true if a key of a given value was first pressed on this tick, false if not
   public boolean getPressedNow(int value) {
      return keyInts[value] == 2;
   }
   
   //return true if a key of a given value was first released on this tick, false if not
   public boolean getReleasedNow(int value) {
      return keyInts[value] == -1;
   }
}