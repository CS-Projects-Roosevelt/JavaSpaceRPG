import java.util.*;
import java.io.*;

public class InputMapper {
   Input[] inputSources;
   ArrayList<String> inputNames;
   ArrayList<int[][]> inputAddresses;
   
   public InputMapper(Input[] sources) throws FileNotFoundException {
      inputSources = sources;
      
      inputNames = new ArrayList<String>();
      inputAddresses = new ArrayList<int[][]>();
      
      Scanner scanner = new Scanner(new File("inputMap.dat"));
      scanner.nextLine(); //skips first few lines intended for human reading
      scanner.nextLine();
      scanner.nextLine();
      
      while (scanner.hasNext()) {
         inputNames.add(scanner.nextLine());
         ArrayList<int[]> addresses = new ArrayList<int[]>();
         for (boolean loop = true; loop; loop = loop) { //loops until boolean loop is set to false
            Scanner lineScanner = new Scanner(scanner.nextLine());
            if (!lineScanner.hasNext()) {
               loop = false; //aborts loop if line is empty
            } else {
               addresses.add(new int[]{lineScanner.nextInt(), lineScanner.nextInt()}); //adds source and value in int[]
            }
         }
         inputAddresses.add(addresses.toArray(new int[2][addresses.size()])); //converts ArrayList addresses to an int[][] and adds to tmp_inputAddresses
      }
      //inputNames = tmp_inputNames.toArray(new String[tmp_inputNames.size()]); //places contents of ArrayList tmp_inputNames into in
   }
   
   //executes the tick method of all input sources
   public void tick() {
      for (int i = 0; i < inputSources.length; i++) {
         inputSources[i].tick();
      }
   }
   
   //returns if any of the inputs mapped to an action name are being pressed
   public boolean pressed(String inputName) {
      int inputIndex = -1; //placeholder index
      for (int i = 0; i < inputNames.size(); i++) {
         if (inputNames.get(i).equals(inputName)) {
            inputIndex = i;
            break;
         }
      }
      //if inputIndex is still less than 0, throw exception
      int[][] addressesAtIndex = inputAddresses.get(inputIndex);
      for (int i = 0; i < addressesAtIndex.length; i++) {
         if (inputSources[addressesAtIndex[i][0]].getPressed(addressesAtIndex[i][1])) {
            return true; //return true if any of the input addresses return true for being pressed
         }
      }
      return false; //else return false
   }
   
   //returns if any of the inputs mapped to an action name were first pressed this tick
   public boolean pressedNow(String inputName) {
      int inputIndex = -1; //placeholder index
      for (int i = 0; i < inputNames.size(); i++) {
         if (inputNames.get(i).equals(inputName)) {
            inputIndex = i;
            break;
         }
      }
      //if inputIndex is still less than 0, throw exception
      int[][] addressesAtIndex = inputAddresses.get(inputIndex);
      for (int i = 0; i < addressesAtIndex.length; i++) {
         if (inputSources[addressesAtIndex[i][0]].getPressedNow(addressesAtIndex[i][1])) {
            return true; //return true if any of the input addresses return true for being pressed
         }
      }
      return false; //else return false
   }
   //returns if any of the inputs mapped to an action name were first released this tick
   public boolean releasedNow(String inputName) {
      int inputIndex = -1; //placeholder index
      for (int i = 0; i < inputNames.size(); i++) {
         if (inputNames.get(i).equals(inputName)) {
            inputIndex = i;
            break;
         }
      }
      //if inputIndex is still less than 0, throw exception
      int[][] addressesAtIndex = inputAddresses.get(inputIndex);
      for (int i = 0; i < addressesAtIndex.length; i++) {
         if (inputSources[addressesAtIndex[i][0]].getReleasedNow(addressesAtIndex[i][1])) {
            return true; //return true if any of the input addresses return true for being pressed
         }
      }
      return false; //else return false
   }
}