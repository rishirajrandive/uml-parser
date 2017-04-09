
public class Optimist extends ConcreteObserver {
 
    public Optimist( ConcreteSubject sub )
    {
        super( sub ) ;
    }
    
	public void update() {
	    if ( subject.getState().equalsIgnoreCase("The Price of gas is at $5.00/gal")      )
        {
             observerState = "Great! It's time to go green." ;
        }
        else if ( subject.getState().equalsIgnoreCase( "The New iPad is out today" ) )
        {
            observerState = "Apple, take my money!" ;
        }
        else
        {
            observerState = ":)" ;
        }
	}
	 
}
 


