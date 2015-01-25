/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * The WeatherManager retrieves through an API the weather for the events
 *
 * @author edo
 */
@Startup
@Singleton
public class WeatherManager {
    
    private static final String api1 = "";
    private static final String api2 = "";
    private OpenWeatherMap owm;
    
    @Resource
    private TimerService ts;
    
    @PostConstruct
    private void init() {
        owm = new OpenWeatherMap(api1 + api2);
        ScheduleExpression every10Minutes = new ScheduleExpression().minute(10);
        ts.createCalendarTimer(every10Minutes, new TimerConfig("", false));
        System.out.println("WeatherManager created");
    }
    
    @Schedule(minute = "*/10", persistent = false)
    public void checkWeatherAuto() throws IOException {
        byte days = 10;
        DailyForecast df;
        String city = "Milano";
        df = owm.dailyForecastByCityName(city, days);
        
                
    }
    
    @Timeout
    public void checkWeatherProg() {
        
    }
    
    
    public static void main(String[] args) throws IOException {
        
        
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, api1 + api2);

        // getting current weather data for the "London" city
        CurrentWeather cwd = owm.currentWeatherByCityName("Milano");

        //printing city name from the retrieved data
        System.out.println("City: " + cwd.getCityName());
        //index - Index of Weather instance in the list.
        System.out.println(cwd.getWeatherInstance(0).getWeatherName());
        System.out.println(cwd.getRawResponse());
        
    }
}
