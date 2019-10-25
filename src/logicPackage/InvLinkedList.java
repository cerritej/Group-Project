//package logicPackage;
//
///***********************************************************************
// * The invLinkedList class contains the overall structure of the
// * in-game inventory that will be present in the GUI.
// *
// * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
// * @version (10/25/18)
// ***********************************************************************/
//public class invLinkedList {
//    private Node head;
//
// static class Node {
//     int data;
//     Node next;
//
//     Node(int d)
//     {
//         data = d;
//         next = null;
//     }
// }
//
// public static invLinkedList insert(invLinkedList list, int data)
// {
//     Node new_node = new Node(data);
//     new_node.next = null;
//
//     if (list.head == null) {
//         list.head = new_node;
//     }
//     else {
//
//         Node last = list.head;
//         while (last.next != null) {
//             last = last.next;
//         }
//
//         last.next = new_node;
//     }
//     return list;
// }
//
// public static invLinkedList remove(int data) {
//	 Node temp = this.head;
//	 while(temp.next != data) {
//		 temp = temp.next;
//	 }
//	 temp.next = temp.next.next;
//	 return list;
// }
//
// public static void printList(invLinkedList list)
// {
//     Node currNode = list.head;
//     System.out.print("Inventory: ");
//
//     while (currNode != null) {
//    	 System.out.print(currNode.data + " ");
//         currNode = currNode.next;
//     }
// }
//}
