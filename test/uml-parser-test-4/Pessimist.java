
public class Pessimist extends ConcreteObserver {

    public Pessimist( ConcreteSubject sub )
    {
        super( sub ) ;
    }

    public void update() {
        if ( subject.getState().equalsIgnoreCase("The Price of gas is at $5.00/gal") )
        {
            observerState = "This is the beginning of the end of the world!" ;
        }
        else if ( subject.getState().equalsIgnoreCase( "The New iPad is out today" ) )
        {
            observerState = "Not another iPad!"  ;
        }
        else
        {
            observerState = ":(" ;
        }
	}
	 
}
 
