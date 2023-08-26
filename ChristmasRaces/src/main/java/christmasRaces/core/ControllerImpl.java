package christmasRaces.core;


import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.*;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.*;

import java.util.*;


public class ControllerImpl implements Controller {
    CarRepository carRepository;
    RaceRepository raceRepository;
    DriverRepository driverRepository;

    public ControllerImpl(DriverRepository driverRepository, CarRepository carRepository, RaceRepository raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driver) {
        if (driverRepository.getByName(driver) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driver));
        }
        Driver driver1 = new DriverImpl(driver);
        driverRepository.add(driver1);
        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        Car car = null;
        switch (type) {
            case "Muscle":
                if (this.carRepository.getAll().stream().anyMatch(a -> a.getModel().equals(model) && a.getHorsePower() == horsePower)) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
                }

                car = new MuscleCar(model, horsePower);
                this.carRepository.add(car);
                break;
            case "Sports":
                if (this.carRepository.getAll().stream().anyMatch(a -> a.getModel().equals(model) && a.getHorsePower() == horsePower)) {
                    throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
                }
                car = new SportsCar(model, horsePower);
                this.carRepository.add(car);
                break;

        }

        return String.format(OutputMessages.CAR_CREATED, car.getClass().getSimpleName(), model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        if (driverRepository.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        if (this.carRepository.getAll().stream().noneMatch(a -> a.getModel().equals(carModel))) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }
        Car car = carRepository.getByName(carModel);
        driverRepository.getByName(driverName).addCar(car);

        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Driver driver = driverRepository.getByName(driverName);

        if (raceRepository.getAll().stream().noneMatch(a -> a.getName().equals(raceName))) {
            // if(race == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }


        raceRepository.getAll().stream()
                .filter(r -> r.getName().equals(raceName))
                .findFirst().orElse(null)
                .addDriver(driver);

        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        if (raceRepository.getAll().stream().noneMatch(r -> r.getName().equals(raceName))) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        Race race = raceRepository.getByName(raceName);
        if (race.getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }
        Map<String, Double> winners = new HashMap<>();
        List<String> sortedDrivers = new ArrayList<>();


        race.getDrivers().forEach(d -> winners.put(d.getName(), d.getCar().calculateRacePoints(race.getLaps())));

        winners
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(e -> sortedDrivers.add(e.getKey()));

        return String.format(OutputMessages.DRIVER_FIRST_POSITION, sortedDrivers.get(0), raceName) +
                System.lineSeparator() +
                String.format(OutputMessages.DRIVER_SECOND_POSITION, sortedDrivers.get(1), raceName) +
                System.lineSeparator() +
                String.format(OutputMessages.DRIVER_THIRD_POSITION, sortedDrivers.get(2), raceName);

    }

    @Override
    public String createRace(String name, int laps) {
        if (raceRepository.getAll().stream().anyMatch(a -> a.getName().equals(name))) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }
        RaceImpl race = new RaceImpl(name, laps);
        raceRepository.add(race);
        return String.format(OutputMessages.RACE_CREATED, name);
    }
}
