public class Customer {
    private Car[] vehicles;
    ArrayList<Car> carList = new ArrayList<Car>();
    public Customer(){
        vehicles = new Car[2];
        vehicles[0] = new Car("Audi");
        vehicles[1] = new Car("Mercedes");
        carList.add(new Car("BMW"));
        carList.add(new Car("Chevy"));
    }
}