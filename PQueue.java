import java.util.*;

	public class PQueue{
		public static void main(String[] Args){
			PriorityQueue<PNode> pnQueue = new PriorityQueue<PNode>(10, new Comparator<PNode>(){
				public int compare(PNode p1, PNode p2){ 
					if(p1.cost >= p2.cost){return 1;} 
					else{return -1;}
				}
			});
			PNode p = new PNode();
			p.cost = 5;
			PNode q = new PNode();
			q.cost = 2;
			PNode r = new PNode();
			r.cost = 10;
			PNode s = new PNode();
			s:s.cost = 1;
			pnQueue.add(p);
			pnQueue.add(q);
			pnQueue.add(r);
			pnQueue.add(s);
			PNode node = pnQueue.poll();
			while(node != null)
			{
				System.out.println(node.cost);
				node = pnQueue.poll();
			}
				
		}
	}