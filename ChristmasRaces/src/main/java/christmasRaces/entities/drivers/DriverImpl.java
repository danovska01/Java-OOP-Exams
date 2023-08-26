package christmasRaces.entities.drivers;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.entities.cars.BaseCar;
import christmasRaces.entities.cars.Car;

import java.util.ArrayList;
import java.util.List;

public class DriverImpl implements Driver{
    private String name;
   // private Car car;
    private int numberOfWins;
    private boolean canParticipate;
    private Car car;

    public DriverImpl(String name) {
        this.setName(name);
        this.canParticipate = false;
        this.car = null;
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty() || name.length() < 5) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_NAME,name, 5));
        }
        this.name = name;
    }

    public boolean setCanParticipate(){
        if(this.car != null){
          return canParticipate = true;
        }
        return canParticipate = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Car getCar() {
        //TODO
        return this.car;
    }

    @Override
    public int getNumberOfWins() {
        return this.numberOfWins;
    }

    @Override
    public void addCar(Car car) {
        if(car == null){
            throw new IllegalArgumentException(ExceptionMessages.CAR_INVALID);
        }
        this.car = car;
    }

    @Override
    public void winRace() {
        this.numberOfWins++;
    }

    @Override
    public boolean getCanParticipate() {
        return setCanParticipate();
    }
}
