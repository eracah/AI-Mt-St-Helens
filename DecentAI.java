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

        public PNode(Point p, PNode prev, double c)
        {
            cost = c;
            previous = prev;
            //next = n;
            pt = p;
        }
    }

    public int getLeastNodes(Point curPt, Point goalPt)
    {
        int xStep, yStep;
        int counter;
        Point testPt = curPt;
        if (goalPt.x > curPt.x)
            xStep = 1;
        else
            xStep = -1;

        if (goalPt.y > curPt.y)
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
            counter += (goalPt.x - curPt.x)*xStep;
        else
            counter += (goalPt.y - curPt.y)*yStep;

        return counter;

    }
    public double heuristic(Point c, Point gp, TerrainMap map)
    {
    	Point cpt = new Point(c.x, c.y);
        int leastNodes = getLeastNodes(c, gp);
        double curHeight = map.getTile(cpt);
        double goalHeight = map.getTile(gp);
        double diff = goalHeight - curHeight;
        double baseStep = Math.floor(diff / leastNodes);
        double n = diff % leastNodes;
        if(diff < 0)
            n = (-1 * (diff)) % leastNodes;
        
        return (n * Math.exp(baseStep + 1) + (leastNodes - n) * Math.exp(baseStep));

        // double exponent = diff / leastNodes;
        // return Math.exp(exponent) * leastNodes;
    	//return (Math.sqrt(Math.pow((c.pt.x - gp.x), 2) + Math.pow((c.pt.y - gp.y), 2)));// + Math.pow((map.getTile(c.pt) - map.getTile(gp)), 2)));
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

        //System.out.println("Goal:" + GoalPoint.x +" " + GoalPoint.y);

         //Holds the neighbors
        Point Neighbors[];
        
        PNode CurrentNode = new PNode(StartPoint, null, 0);

        //Point pt1 = new Point(4,3);
        //Point pt2 = new Point(12,9);


        //int num = getLeastNodes(pt1, pt2);
        //System.out.println(num);

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
                    Point cPt = new Point(CurrentNode.pt.x,CurrentNode.pt.y);
                    double curHeight = map.getTile(CurrentNode.pt);
                    double neighHeight = map.getTile(nPt);
                    double neighborCost = map.getCost(CurrentNode.pt, nPt);
                    double prevCost = CurrentNode.cost;
                    double heur = heuristic(cPt, GoalPoint, map);
                    double cost = neighborCost + prevCost + heur;
                    //double temp = map.getTile(CurrentNode.pt);
                    PNode p = new PNode(nPt, CurrentNode, cost);
                    pnQueue.add(p); // test to see if actually contains anything
                    //System.out.println("Added node with cost: " + p.cost + " coordinates: " + p.pt + "Visited?" + Closed[nPt.x][nPt.y]);

                }
                
            }
           
        
            double prevCost = CurrentNode.cost;
            

            PNode prevNode = new PNode(CurrentNode.pt, null, prevCost);

            CurrentNode = pnQueue.poll();
            while (Closed[CurrentNode.pt.x][CurrentNode.pt.y])
            {
                CurrentNode = pnQueue.poll();
            }
            
            if (prevCost > CurrentNode.cost)
                System.out.println("Popped node with cost: " + CurrentNode.cost + " coordinates: " + CurrentNode.pt);

          

            
            

            Closed[CurrentNode.pt.x][CurrentNode.pt.y] = true;
            //System.out.println("PNode with cost = " + CurrentNode.cost + " is at the top of the min heap");
            //AI is rechecking traversed nodes. Implement a structure (hash table?) that keeps track of closed nodes.

        }
        path = getPrev(CurrentNode, path);
        /*for (int i = 0; i < path.length; i++)
        {
            System.out.println(path[i])
        }*/
        
        
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
