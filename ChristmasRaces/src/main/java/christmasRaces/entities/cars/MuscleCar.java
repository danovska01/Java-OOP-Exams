package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar{

    public MuscleCar(String model, int horsePower) {
        super(model, horsePower, 5000);
    }



    @Override
    public void setHorsePower(int horsePower) {
        if(horsePower < 400 || horsePower > 600) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }
        super.setHorsePower(horsePower);
    }

}
