# Weather Application

The application was made for a coding challenge. The application generates 5 random numbers and assigns them to Monday - Friday. The application also utilizes on the ambient sensor within the phone, displaying the temperature reading at the top of the application. 

A button is available for the user to switch between Celsius and fahrenheit, utlilzing native C code for the converstion calculations.  

### Prerequisities

For full functionality, the user should have an android device with minimum version 5.0 and the device should have a temperature sensor. 

As an added note, I did not have a temperature sensor on my phone. For any user who does not have a sensor, a message saying "No Temperatre Sensor Available" will appear at the top of the activity.  

### Installing

The application should be loaded with the most up to date native librarbies. If the libraries are an older version, run the following in the terminal: 
```
ndk-build
```

## Built With

* Android Studios
* NDK

## Authors

* **Shane Dharan** - *Sole Contributor*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
