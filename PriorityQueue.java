import java.util.*;

	public class PriorityQueue{
		public static void PQueueTest(String[] args){
			PriorityQueue<PNode> pnQueue = new PriorityQueue<PNode>(10, new Comparator<PNode>(){
				public int compare(PNode p1, PNode p2){ 
					if(p1.cost >= p2.cost){return 1;} 
					else{return -1;}
				}
			});
			System.out.println("Hello World");
			PNode p = new PNode();
			p.cost = 5;
			System.out.println("p.cost = ");
			System.out.println(p.cost);
			PNode q = new PNode();
			q.cost = 2;
			System.out.println("q.cost = ");
			System.out.println(q.cost);
			pnQueue.add(p);
			pnQueue.add(q);
			for(int i = 0; i < size(pnQueue); i++){
				System.out.println(pnQueue.poll());
			}
		}
	}