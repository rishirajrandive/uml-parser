
public class Tester {

    public static void main(String[] args)
    {
        Component obj = new ConcreteDecoratorB( new ConcreteDecoratorA( new ConcreteComponent() ) ) ;
        String result = obj.operation() ;
        System.out.println( result );
    }
}
