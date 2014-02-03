import java.awt.Point;
/*import java.util.ArrayList;
import java.util.List;
*/
import java.util.*; //todo: shrink this down
import java.lang.Math;

//Evan Racah and Cameron Massoudi

public class AStarExp_995919248_996939201 implements AIModule{
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
            pt = p;
            height = h;
        }
    }

    public int getLeastNodes(final Point pt1, final Point pt2)
    {
       
        int x = pt1.x;
        int y = pt1.y;
        Point testPt = new Point(x, y);

        int xStep, yStep;
        int counter;
      
        if (pt2.x > testPt.x)
            xStep = 1;
        else
            xStep = -1;

        if (pt2.y > testPt.y)
            yStep = 1;
        else
            yStep = -1;

        counter = 0;
        
        //while neither coordinate is equal to the corresponding goal coordinates
        while(testPt.x != pt2.x && testPt.y != pt2.y)
        {
            testPt.x += xStep;
            testPt.y += yStep;
            counter ++;
        }
        if (pt2.x != testPt.x)
            counter += (pt2.x - testPt.x) * xStep;
        else
            counter += (pt2.y - testPt.y) * yStep;

        return counter;

    }
    public double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2)
    {
    	
        int leastNodes = getLeastNodes(pt1, pt2);
        return leastNodes * Math.exp(-6.52); 
        // double curHeight = map.getTile(pt1);
        // double goalHeight = map.getTile(pt2);
        // double diff = map.getTile(pt2) - map.getTile(pt1);
        // double baseStep = Math.floor(diff / leastNodes);
        // double n = diff % leastNodes;
        // if(diff < 0)
        //     n = (-1 * (diff)) % leastNodes;
        
        // return (n * Math.exp(baseStep + 1) + (leastNodes - n) * Math.exp(baseStep));
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
        
        while(!(CurrentNode.pt.x == GoalPoint.x && CurrentNode.pt.y == GoalPoint.y))
        {
            
            Neighbors = map.getNeighbors(CurrentNode.pt);
            for(int i = 0; i < Neighbors.length; i++)
            {
                if (!(Closed[Neighbors[i].x][Neighbors[i].y]))
                {

                    Point nPt = Neighbors[i];
                    double cost = map.getCost(CurrentNode.pt, nPt) + CurrentNode.cost + getHeuristic(map, nPt, GoalPoint);
                    PNode p = new PNode(nPt, CurrentNode, cost, map.getTile(nPt));                  
                    pnQueue.add(p); 
                    

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
