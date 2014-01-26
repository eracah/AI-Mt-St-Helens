import java.util.*;

	public class main{
		public static void main(String[] args){
			PriorityQueue<PNode> pnQueue = new PriorityQueue<PNode>(10, new Comparator<PNode>(){
				public PNode compare(PNode p1, PNode p2){ 
					if(p1.cost >= p2.cost){return p2;} 
					else{return p1;}
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
			System.out.println(pnQueue);
		}
	}
