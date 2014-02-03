import java.awt.Point;
/*import java.util.ArrayList;
import java.util.List;
*/
import java.util.*; //todo: shrink this down
import java.lang.Math;

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
        double height;

        public PNode(Point p, PNode prev, double c, double h)
        {
            cost = c;
            previous = prev;
            //next = n;
            pt = p;
            height = h;
        }
    }

    public int getLeastNodes(Point c, Point goalPt)
    {
        int x = c.x;
        int y = c.y;
        Point testPt = new Point(x, y);
        int xStep, yStep;
        int counter;
      
        if (goalPt.x > testPt.x)
            xStep = 1;
        else
            xStep = -1;

        if (goalPt.y > testPt.y)
            yStep = 1;
        else
            yStep = -1;

        counter = 0;
        
        //while neither coordinate is equal to the corresponding goal coordinates
        while(testPt.x != goalPt.x && testPt.y != goalPt.y)
        {
            testPt.x += xStep;
            testPt.y += yStep;
            counter ++;
        }
        if (goalPt.x != testPt.x)
            counter += (goalPt.x - testPt.x) * xStep;
        else
            counter += (goalPt.y - testPt.y) * yStep;

        return counter;

    }
    public double heuristic(Point c, Point gp, TerrainMap map)
    {
    	int x = c.x;
        int y = c.y;
        Point cpt = new Point(x, y);
        int leastNodes = getLeastNodes(cpt, gp);
        double curHeight = map.getTile(cpt);
        double goalHeight = map.getTile(gp);
        double diff = goalHeight - curHeight;
        double baseStep = Math.floor(diff / leastNodes);
        double n = diff % leastNodes;
        if(diff < 0)
            n = (-1 * (diff)) % leastNodes;
        
        return (n * Math.exp(baseStep + 1) + (leastNodes - n) * Math.exp(baseStep));
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


        //make 2d array to check if coordinates closed 
        boolean Closed[][] = new boolean[map.getWidth()][map.getHeight()];
        


        // Holds the resulting path
        ArrayList<Point> path = new ArrayList<Point>();

        //get start and end point
        final Point StartPoint = map.getStartPoint();
        final Point GoalPoint = map.getEndPoint();

        
        Point Neighbors[];
        
        PNode CurrentNode = new PNode(StartPoint, null, 0, map.getTile(StartPoint));

    

        Closed[CurrentNode.pt.x][CurrentNode.pt.y] = true;

        int count = 0;
        while(!(CurrentNode.pt.x == GoalPoint.x && CurrentNode.pt.y == GoalPoint.y))
        {
            
            Neighbors = map.getNeighbors(CurrentNode.pt);
            for(int i = 0; i < Neighbors.length; i++)
            {
                if (!(Closed[Neighbors[i].x][Neighbors[i].y]))
                {

                    Point nPt = Neighbors[i];
                    //double curHeight = map.getTile(CurrentNode.pt);
                    double neighHeight = map.getTile(nPt);
                    int nLnodes = getLeastNodes(nPt, GoalPoint);
                    int cLnodes = getLeastNodes(CurrentNode.pt, GoalPoint);
                    double neighborCost = map.getCost(CurrentNode.pt, nPt);
                    double prevCost = CurrentNode.cost;
                    double curHeur = heuristic(CurrentNode.pt, GoalPoint, map);
                    double heur = heuristic(nPt, GoalPoint, map);
                    double cost = neighborCost + prevCost + heur;
                    PNode p = new PNode(nPt, CurrentNode, cost, map.getTile(nPt));
                    if (curHeur > heur + neighborCost)
                    {
                    	System.out.println(neighHeight);
                        System.out.println("WTF" + CurrentNode.pt + " " + nPt);
                        System.out.println(cLnodes);
                        System.out.println(nLnodes);
                    }
                    pnQueue.add(p); // test to see if actually contains anything
                    //System.out.println("Added node with cost: " + p.cost + " coordinates: " + p.pt + "Visited?" + Closed[nPt.x][nPt.y]);

                }
                
            }
          
            CurrentNode = pnQueue.poll();
            while (Closed[CurrentNode.pt.x][CurrentNode.pt.y])
            {
                CurrentNode = pnQueue.poll();
            }

            Closed[CurrentNode.pt.x][CurrentNode.pt.y] = true;
           

        }
        path = getPrev(CurrentNode, path);
        return path;
    }
     public ArrayList<Point> getPrev(PNode node, ArrayList<Point> path)
    {
        if (node.previous == null)
        {
           path.add(new Point(node.pt));
           return path; 
        }
            
        else
        {
            path = getPrev(node.previous, path);
            path.add(new Point(node.pt));
            return path;
        }
    }
}
