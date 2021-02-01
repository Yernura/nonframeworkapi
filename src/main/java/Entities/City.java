package Entities;

/*
Класс для представления сущности данных. Это простая сущность.
Но мы можем легко изменить это с помощью реляционного объекта JPA / Hibernate для подключения к базе данных.
 */
public class City {
    private String zipCode;
    private String weatherReport;
    private String timeZone;

    public City(String zipCode, String weatherReport, String timeZone) {
        this.zipCode = zipCode;
        this.weatherReport = weatherReport;
        this.timeZone = timeZone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWeatherReport() {
        return weatherReport;
    }

    public void setWeatherReport(String weatherReport) {
        this.weatherReport = weatherReport;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
