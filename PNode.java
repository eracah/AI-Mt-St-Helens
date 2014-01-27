import java.util.*;
import java.awt.Point;

//class PNode;

 public class PNode
 {
 		double cost;
 		PNode previous;
 		Point pt;

 		public PNode(Point coord, PNode pv, double c)
 		{
 			cost = c;
 			previous = pv;
 			pt = coord;
 		}
 
 }