
class ClassA {
    
	private String message ; // public attribute via setters and getters
    private String bark ;
    protected String foo ;
    protected String bar ;
    String test ; // package scope
    
	public String getMessage() { 
		return this.message ; 
	}
    
	public void setMessage( String msg ) { 
		this.message = msg ; 
	}

    public void testMethod() {
        // do test...
    }
}