package christmasRaces;

import christmasRaces.core.ControllerImpl;
import christmasRaces.core.EngineImpl;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.races.Race;
import christmasRaces.io.ConsoleReader;
import christmasRaces.io.ConsoleWriter;
import christmasRaces.repositories.CarRepository;
import christmasRaces.repositories.DriverRepository;
import christmasRaces.repositories.RaceRepository;
import christmasRaces.repositories.interfaces.Repository;

public class Main {
    public static void main(String[] args) {
        CarRepository carRepository = new CarRepository();
        RaceRepository raceRepository = new RaceRepository();
        DriverRepository driverRepository = new DriverRepository();

        Controller controller = new ControllerImpl(driverRepository, carRepository, raceRepository);

        ConsoleReader reader = new ConsoleReader();
        ConsoleWriter writer = new ConsoleWriter();
        EngineImpl engine = new EngineImpl(reader, writer, controller);
        engine.run();
    }
}
