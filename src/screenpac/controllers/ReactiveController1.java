package screenpac.controllers;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import screenpac.extract.Constants;
import java.util.Random;
import java.util.*;
import screenpac.extract.Constants;
import screenpac.features.NearestPillDistance;
import screenpac.features.Utilities;
import screenpac.model.GameStateInterface;
import screenpac.model.MazeInterface;
import screenpac.model.Node;


public class ReactiveController implements AgentInterface, Constants {
	/*
    This simple pill eater + Yaser & Lilia modification 
    heads for the nearest pill each time
      
     */
    NearestPillDistance npd;

    public ReactiveController() {
        npd = new NearestPillDistance();
    }

    public int action(GameStateInterface gs) {
        // choose the adjacent current with the
        // nearest pill
        // check that copying works!
        gs = gs.copy();
        Node current = gs.getPacman().current;
        npd.score(gs, current);
        
        ArrayList<ghostNode> ghosts = new ArrayList<ghostNode>();
        
        for (int gg = 0 ; gg<=3; gg++){ // to set all 4 ghost No.+Node+distance 
        	ghostNode GN = new ghostNode();
        	Node gnode = gs.getGhosts()[gg].current;
        	int dd = gs.getMaze().dist(gnode, current);
			GN.setghost(gg , gnode , dd);
			ghosts.add(GN);        	
        }
        
               
        for (int gg = 0 ; gg<=3; gg++){
        	
        	Node gnode = gs.getGhosts()[gg].current;
        	int dd = gs.getMaze().dist(gnode, current);
        
        	if (gs.getGhosts()[gg].edible() && dd<60){
        		
        		Node next = Utilities.getClosest(current.adj, gnode, gs.getMaze());
        		int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
        		return dir;
        	}
        
        	if (Math.abs(dd)<=7){
        	
        		Node next = Utilities.getfar(current.adj, gnode, gs.getMaze());
        		int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
        		return dir;
        	
        	}
        	
        
        }
         
        		
        Node next = Utilities.getClosest(current.adj, npd.closest, gs.getMaze()); 
        
        int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
        return dir;
   }
    
    
    

 
}


class ghostNode{
	private int ghostno;
	private Node gNode;
	private int dist;
	
	public void setghost(int gn, Node gno, int dis){
		ghostno=gn;
		gNode=gno;
		dist = dis;
	}
	public Node getgNode() {
		return gNode;		
	}
	public int getgNo(){
		return ghostno;
	}
	public int getgdist(){
		return dist;
	}
	
}

class fUnctions {
	
	public ghostNode orderbydist(ArrayList<ghostNode> glist){
		// ordering the list
//		ArrayList<ghostNode> ordlist = new ArrayList<ghostNode>();
				
			ghostNode minnode = new ghostNode();
			int di = Integer.MAX_VALUE;
			Node gni = new Node(0,0);
			minnode.setghost(10, gni, di);
		
			Iterator<ghostNode> iterator = glist.iterator();  
			int gindex = 0;
		
			while(iterator.hasNext()){
									
					if (minnode.getgdist() > iterator.next().getgdist()) {
						minnode = glist.get(gindex);
					
					}
						
				gindex++;			
			} // end While loop 
			
			
		return minnode;
	}
	
//	public static Node getfar(ArrayList<Node> nodes, Node target, MazeInterface maze) {
//    	// this Yaser getfar
//        // if the target current is null then print a warning
//        if (target == null) {
//            System.out.println("Warning: null target in Utilities.getClosest()");
//            return nodes.get(rand.nextInt(nodes.size()));
//        }
//        double best = Double.MIN_VALUE;
//        // selected current
//        Node sel = null;
//        for (Node n : nodes) {
//            int d = maze.dist(n, target);
//            if (d > best) {
//                best = d;
//                sel = n;
//            }
//        }
//        sel.col = Color.red;
//        return sel;
//    }
	
	
//	public Node PPN(){
//		ArrayList<Node> n = gs.getMaze().getPowers(); // minute
//		for (Node n : gs.getMaze().getPowers()) {
//	    // check the state of this pill
//	    // by querying the BitSet of pill states
//	    if (gs.getPills().get(n.pillIndex)) {
//	        // this pill is on, but is it closer?
//	        if (gs.getMaze().dist(node, n) < minDist) {
//	            minDist = gs.getMaze().dist(node, n);
//	            closest = n;
//	        }
//	    }
//		}		
//		
//	}
    
	
}



