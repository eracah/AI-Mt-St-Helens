//Comment
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
public class DecentAI implements AIModule
{
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
        
        while(CurrentNode.pt != GoalPoint )
        {
            Neighbors = map.getNeighbors(CurrentNode.pt);
            for(int i = 0; i < Neighbors.length; i++ )
            {
                Point nPt = Neighbors[i];
                double cost = map.getCost(CurrentNode.pt, nPt) + CurrentNode.cost; //+Heuristic
                PNode p = new PNode(nPt, CurrentNode, cost);
                pnQueue.add(p); // test to see if actually contains anything
            }
            //pop from pqueue
        }


        ///path.add(new Point(CurrentPoint));

        
        return path;
    }
}
