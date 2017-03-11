
public interface Subject {
 
	public abstract void attach(Observer obj);
	public abstract void detach(Observer obj);
	public abstract void notifyObservers();
}
 
