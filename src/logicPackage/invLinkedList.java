

/***********************************************************************
 * The invLinkedList class contains the overall structure of the
 * in-game inventory that will be present in the GUI.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (10/25/18)
 ***********************************************************************/
public class invLinkedList {
    private Node head;

 static class Node {
     int data;
     Node next;

     Node(int d)
     {
         data = d;
         next = null;
     }
 }

 public static invLinkedList insert(invLinkedList list, int data)
 {
     Node new_node = new Node(data);
     new_node.next = null;

     if (list.head == null) {
         list.head = new_node;
     }
     else {

         Node last = list.head;
         while (last.next != null) {
             last = last.next;
         }

         last.next = new_node;
     }
     return list;
 }

 public static invLinkedList deleteByKey(invLinkedList list, int key) 
 { 
     // Store head node 
     Node currNode = list.head, prev = null; 

     // 
     // CASE 1: 
     // If head node itself holds the key to be deleted 

     if (currNode != null && currNode.data == key) { 
         list.head = currNode.next; // Changed head 

         System.out.println(key + " found and deleted"); 

         return list; 
     } 

     // CASE 2: 
     // If the key is somewhere other than at head 

     while (currNode != null && currNode.data != key) { 

         prev = currNode; 
         currNode = currNode.next; 
     } 

     if (currNode != null) { 
        
         prev.next = currNode.next; 

         System.out.println(key + " found and deleted"); 
     } 

     
     // CASE 3: The key is not present 
    
     if (currNode == null) { 
         // Display the message 
         System.out.println(key + " not found"); 
     } 

     
     return list; 
 } 

 public static void printList(invLinkedList list)
 {
     Node currNode = list.head;
     System.out.print("Inventory: ");

     while (currNode != null) {
    	 System.out.print(currNode.data + " ");
         currNode = currNode.next;
     }
 }
}
