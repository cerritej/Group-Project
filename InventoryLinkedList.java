package package1;


public class InventoryLinkedList { 

 Node head;


 static class Node { 

     int data; 
     Node next; 


     Node(int d) 
     { 
         data = d; 
         next = null; 
     } 
 } 


 public static InventoryLinkedList insert(InventoryLinkedList list, int data) 
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
 
}
