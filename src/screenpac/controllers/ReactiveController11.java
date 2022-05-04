package screenpac.controllers;

import screenpac.extract.Constants;
import screenpac.features.NearestPillDistance;
import screenpac.features.Utilities;
import screenpac.model.GameStateInterface;
import screenpac.model.Node;



public class ReactiveController implements AgentInterface, Constants {
	
    NearestPillDistance npd;

    public ReactiveController() {
        npd = new NearestPillDistance();
    }
      

    public int action(GameStateInterface gs) 
    {
        // choose the adjacent current with the
        // nearest pill
        // check that copying works!
        gs = gs.copy();
        Node current = gs.getPacman().current;
       
        npd.score(gs, current);
        int gost = 0; int indice = 0;
        
        
        gost = GMPerto(gs);
        Node gnode = gs.getGhosts()[gost].current;
        
        int distGMP = gs.getMaze().dist(gnode, current);
        
        
       if (distGMP<= 7 && gs.getGhosts()[gost].edible() == false)
       {
    	   	int direction = GMLonge(gs, gost);
    	            	      	
        	Node next = current.adj.get(direction);         
        	int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
    	   
            return dir;
        	
        }
       
       if (gs.getGhosts()[gost].edible())
       {
   		Node next = Utilities.getClosest(current.adj, gnode, gs.getMaze());
   	    int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
           return dir;
   		}
        else
        {
            
        	Node next = Utilities.getClosest(current.adj, npd.closest, gs.getMaze());           
        	int dir = Utilities.getWrappedDirection(current, next, gs.getMaze());
        	return dir;
        }
    }
    
    public int GMPerto (GameStateInterface gs)
    {
    	int gg = 0; int mperto = 1000; int gost = 0; int dd;
    	
    	Node current = gs.getPacman().current;
    	
    	
    	for (gg = 0 ; gg<=3; gg++)
        {
        	Node gnode = gs.getGhosts()[gg].current;
        	dd = gs.getMaze().dist(gnode, current);
        	if(dd <= mperto)
        	{
        		mperto = dd;
        		gost = gg;
        	}
        }
    	return gost;
    }
    
    public int GMLonge (GameStateInterface gs, int gost)
    {
    	int gg = 0; int dd; int i = 0; int res = 0;
    	
    	
    	Node current = gs.getPacman().current;
    	Node gostgnode = gs.getGhosts()[gost].current;
    	
    	
    	for (gg = 0 ; gg< current.adj.size(); gg++)
        {
        	
    		Node caminho = current.adj.get(gg);
        	dd = gs.getMaze().dist(caminho, gostgnode);
        	
        	if(dd >= i)
        	{
        		i = dd;
        		res = gg;
        	}
        }
    	return res;
    }
}
