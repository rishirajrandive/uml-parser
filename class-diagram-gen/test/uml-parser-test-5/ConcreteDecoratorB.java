
public class ConcreteDecoratorB extends Decorator {

    private String addedState;

    public ConcreteDecoratorB( Component c)
    {
        super( c ) ;
    }

    public String operation()
    {
        addedState = super.operation() ;
        return addedBehavior( addedState ) ;
    }

    private String addedBehavior(String in) {
        return "<h1>" + addedState + "</h1>" ;
    }

}
