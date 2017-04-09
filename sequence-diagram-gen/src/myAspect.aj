
public aspect myAspect {

	pointcut function() : call(void AOPDemo.method(*,*));
	
	after(): function(){
		System.out.println("AspectJ after");
	}
}
