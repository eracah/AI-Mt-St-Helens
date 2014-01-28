import java.awt.Point;
/*import java.util.ArrayList;
import java.util.List;
*/
import java.util.*; //todo: shrink this down

/// A sample AI that takes a very suboptimal path.
/**
 * This is a sample AI that moves as far horizontally as necessary to reach the target,
 * then as far vertically as necessary to reach the target.  It is intended primarily as
 * a demonstration of the various pieces of the program.
 * 
 * @author Leonid Shamis
 */
public class DecentAI implements AIModule{
    public class PNode
    {
        double cost;
        PNode previous;
        Point pt;

        public PNode(Point p, PNode prev, double c)
        {
            cost = c;
            previous = prev;
            pt = p;
        }
    }

    /// Creates the path to the goal.
    public List<Point> createPath(final TerrainMap map)
    {
        PriorityQueue<PNode> pnQueue = new PriorityQueue<PNode>(10, new Comparator<PNode>(){
            public int compare(PNode p1, PNode p2){ 
                if(p1.cost >= p2.cost){return 1;} 
                else{return -1;}
                }
            });

        // Holds the resulting path
        final ArrayList<Point> path = new ArrayList<Point>();

        //get start and end point
        final Point StartPoint = map.getStartPoint();
        final Point GoalPoint = map.getEndPoint();

         //Holds the neighbors
        Point Neighbors[];

        PNode CurrentNode = new PNode(StartPoint, null, 0);
        
        int count = 0;
        while(CurrentNode.pt != GoalPoint && count < 20)
        {
            count++;
            Neighbors = map.getNeighbors(CurrentNode.pt);
            for(int i = 0; i < Neighbors.length; i++)
            {
                Point nPt = Neighbors[i];
                double cost = map.getCost(CurrentNode.pt, nPt) + CurrentNode.cost; //+Heuristic
                PNode p = new PNode(nPt, CurrentNode, cost);
                pnQueue.add(p); // test to see if actually contains anything
                System.out.println("Added node with cost: " + p.cost + " coordinates: " + p.pt);
            }
            CurrentNode = pnQueue.remove();
            //System.out.println("PNode with cost = " + CurrentNode.cost + " is at the top of the min heap");
            //AI is rechecking traversed nodes. Implement a structure (hash table?) that keeps track of closed nodes.

        }

        System.out.println("CurrentNode = " + CurrentNode.pt + " \n GoalNode = " + GoalPoint);

        ///path.add(new Point(CurrentPoint));

        
        return path;
    }
}
