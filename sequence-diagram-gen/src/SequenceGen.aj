import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.Pointcut;


public aspect SequenceGen {

	private int callDepth;

	//pointcut traced() : !within(TracingAspect) && execution(public * *.*(..)) ;
	//pointcut traced() : !within(SequenceGen) && execution(* *.*(..)) ;
	pointcut traced() : !within(SequenceGen) && execution(* *.*(..));

	before() : traced() {
		print("Before", thisJoinPoint);
		callDepth++;
	}

	after() : traced() {
		callDepth--;
		//print("After", thisJoinPoint);
	}

	private void print(String prefix, JoinPoint m) {
		for (int i = 0; i < callDepth; i++) {
			System.out.print(".");
		} 
		
		System.out.print(prefix + ": " + m.toString() + " " + m.getSignature() );
		System.out.print( " args : [ " ) ;
		for (Object obj : m.getArgs())
		{
			System.out.print( obj + " " ) ;
		}
		System.out.println("]") ;
	}
	
	
}
