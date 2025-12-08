import java.util.HashMap;

class Main {
     public static void main(String[] args) {
          MyHashMap<String, Integer> map = new MyHashMap<>();

          // Put
          System.out.println("Put A: " + map.put("A", 1));
          System.out.println("Put A (Collision) : "+ map.put("A", 111)); // value updated
          System.out.println("Put B: " + map.put("B", 2));
          System.out.println("PutIfAbsent B: " + map.putIfAbsent("B", 20));
          System.out.println("PutIfAbsent C: " + map.putIfAbsent("C", 3));
          System.out.println("After Put : \n"+map);



          // Replace
          System.out.println("Replace A with 10: " + map.replace("A", 10));
          System.out.println("After Replaced : \n"+map);


          // Remove
          System.out.println("Remove B: " + map.remove("B"));
          System.out.println("Check B is Removed : "+ map.get("B"));
          System.out.println("After Removed : \n"+map);


          // Get
          System.out.println("Get A: " + map.get("A"));
          System.out.println("Get B: " + map.get("B"));

          // Clone
          MyHashMap<String, Integer> clonedMap = map.clone();
          System.out.println("Cloned map: " + clonedMap);


          System.out.println("Size: " + map.size());
          System.out.println("Is empty? " + map.isEmpty());
  
          // clear()
          map.clear();
          System.out.println("After clear: " + map);
     }
}
