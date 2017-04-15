import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.Pointcut;


public aspect SequenceGen {

	private int callDepth;
	private StringBuffer plantUmlString = new StringBuffer();

	//pointcut traced() : !within(TracingAspect) && execution(public * *.*(..)) ;
	//pointcut traced() : !within(SequenceGen) && execution(* *.*(..)) ;
	pointcut traced() : !within(SequenceGen) && execution(* *.*(..));

	before() : traced() {
		print("Before", thisJoinPoint);
		callDepth++;
	}

	after() : traced() {
		callDepth--;
		print("After", thisJoinPoint);
	}

	private void print(String prefix, JoinPoint m) {

		if(callDepth % 2 == 0){
			plantUmlString.append(m.getSignature().getDeclaringTypeName() + "\n");
		}else {
			plantUmlString.append(m.getSignature().getDeclaringTypeName() + "->");
		}
//		System.out.print(m.getSignature() + "   " + m.getSignature().getName() + "     " + m.getSignature().getDeclaringTypeName());
//		
//		
//		System.out.print( " args : [ " ) ;
//		for (Object obj : m.getArgs())
//		{
//			System.out.print( obj + " " ) ;
//		}
//		System.out.println("]") ;
		System.out.println(plantUmlString);
	}
	
	
	
	
}
