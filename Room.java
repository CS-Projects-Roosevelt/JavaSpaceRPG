import java.util.*;

public class Room {
   private ArrayList<Actor> actors;
   private List list;
   //private Background bg;
   public Room(Actor[] a) {
      actors = arrayActorToArrayList(a);
   }
   public Actor[] getActors() {
      return actors.toArray(new Actor[0]);
   }
   private ArrayList<Actor> arrayActorToArrayList(Actor[] actorArr) {
      ArrayList<Actor> ret = new ArrayList<Actor>();
      for (int i = 0; i < actorArr.length; i++) {
         ret.add(actorArr[i]);
      }
      return ret;
   }
   public void garbageCollect() {
      int i = 0;
      while (i < actors.size()) {
         if (actors.get(i).shouldDelete()) {
            actors.remove(i);
         }
         else {
            i++;
         }
      }
   }
}