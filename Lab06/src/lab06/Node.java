/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab06;

/**
 *
 * @author 14321005
 */
public class Node {
    public Object element;
  public Node next;
  public Node prev;
  
  public Node(Object e, Node n, Node p){
    element =e ;
    next = n;
    prev =p;
    
  }
}
