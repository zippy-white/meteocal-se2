/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.enums.EventType;
import it.polimi.se2.meteocal.utilities.DateHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private static final byte days = 5;
    private List<Event> eventList;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EventManager evm;

    @Resource
    private TimerService ts;

    @PostConstruct
    private void init() {
        owm = new OpenWeatherMap(api1 + api2);
        ScheduleExpression every10Minutes = new ScheduleExpression().minute(10);
        ts.createCalendarTimer(every10Minutes, new TimerConfig("", false));
        System.out.println("WeatherManager created");
    }
    
    //Schedule weather check every 10  minutes
    @Schedule(second = "*", minute = "*/10", hour = "*", persistent = false)
    public void checkWeatherAuto() throws IOException {
        Date now = Calendar.getInstance().getTime();
        System.out.println("Automatic timer expired. Checking weather | Time: " + now);
        checkEventsWeather();
    }

    @Timeout
    public void checkWeatherProg() throws IOException {
        Date now = Calendar.getInstance().getTime();
        System.out.println("Programmatic timer expired. Checking weather | Time: " + now);
        checkEventsWeather();
    }

    private List<Event> getActiveEvents() {
        try {
            List<Event> el = em.createNamedQuery(Event.findAll, Event.class)
                    .getResultList();
            List<Event> copy = new ArrayList<>(el);
            //Remove events that are not scheduled between today and five days from today
            for (Event e : el) {
                if (e.getEventDate().before(DateHelper.getTodaysDate())
                        || e.getEventDate().after(DateHelper.getDateAfterBaseDate(DateHelper.getTodaysDate(), days))) {
                    copy.remove(e);
                }
            }
            return copy;
        } catch (Exception e) {
            System.out.println("WEATHER_MANAGER: Could not retrieve event list!");
            System.err.println(e);
            return null;
        }

    }

    private void checkEventsWeather() throws IOException {
        Date today = DateHelper.getTodaysDate();
        eventList = getActiveEvents();
        //If we have events to update
        if (eventList != null) {
            for (Event e : eventList) {
                //What is the distance of the event from today
                int delta = e.getEventDate().compareTo(today);
                DailyForecast df = owm.dailyForecastByCityName(e.getLocation(), days);
                //We have meteo data
                if (df.isValid()) {
                    //Weather main condition
                    String weather = df.getForecastInstance(delta).getWeatherInstance(0).getWeatherName();
                    e.setWeather(weather);
                    evm.updateEvent(e);
                    //Is it the case to notify users of bad weather
                    if (e.getType() == EventType.OUTDOOR && isBadWeather(weather)) {
                        System.out.println("Bad weather on event: " + e.getName());
                        evm.notifyBadWeather(e);
                    }
                }
            }
        }
    }

    private Boolean isBadWeather(String weather) {
        //Rain, Snow and Thunderstorm are categorized as bad weather
        return weather.contains("Rain") || weather.contains("Snow") || weather.contains("Thunderstorm");
    }

}
