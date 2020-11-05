package com.example.googlemapforyala;

public class Data
{
    String location;
    String AnimalName;
    String AnimalActivity;


    public Data()
    {

    }

    public Data(String location, String animalName, String animalActivity)
    {
        this.location = location;
        AnimalName = animalName;
        AnimalActivity = animalActivity;
    }

    public String getLocation()
    {
        return location;
    }

    public String getAnimalName()
    {
        return AnimalName;
    }

    public String getAnimalActivity()
    {
        return AnimalActivity;
    }
}

