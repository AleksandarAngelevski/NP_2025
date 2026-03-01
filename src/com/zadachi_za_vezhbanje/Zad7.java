package zadachi_za_vezhbanje;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
class Timee implements Comparable<Timee>{
    private Integer hour;
    private Integer minutes;
    Timee(String time){
        this.hour = (time.contains("."))?Integer.parseInt(time.split("\\.")[0]) : Integer.parseInt(time.split(":")[0]);
        this.minutes = (time.contains("."))? Integer.parseInt(time.split("\\.")[1]) : Integer.parseInt(time.split(":")[1]);
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(Timee timee) {
        if(this.hour.equals(timee.getHour())){
            return this.minutes.compareTo(timee.getMinutes());
        }else{

            return this.hour.compareTo(timee.getHour());
        }
    }

    public void print(TimeFormat type){
        if(type.equals(TimeFormat.FORMAT_24)){
            System.out.printf("%2d:%02d%n",this.hour,this.minutes);
        }else{
            int newHour = (this.hour>12 || this.hour==0)?Math.abs(this.hour-12) : this.hour;
            System.out.printf("%s:%s %s%n",(newHour<10)?String.format("%2d",newHour):newHour,(this.minutes<10)?String.format("0%d",this.minutes):this.minutes,(this.hour>=12)?"PM": "AM");
        }
    }
}
class TimeTable{
    List<Timee> times = new ArrayList<>();
    TimeTable(){}

    void readTimes(InputStream inputStream)throws UnsupportedFormatException, InvalidTimeException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] timesArr = line.split(" ");
                for (String time : timesArr){

                    if(!(time.contains(".") || time.contains(":"))) throw new UnsupportedFormatException(time);
                    if(is_valid(time)){
                        this.times.add(new Timee(time));
                    }
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            Collections.sort(times);
        }
    }
    void writeTimes(OutputStream outputStream, TimeFormat format){
        for (Timee time : times ){
            time.print(format);
        }
    }

    boolean is_valid(String time) throws InvalidTimeException {
        String[] hrs_mins;
        if(time.contains(".")){
            hrs_mins = time.split("\\.");
        }
        else{
            hrs_mins = time.split(":");
        }
        if(!(Integer.parseInt(hrs_mins[0])>=0 && Integer.parseInt(hrs_mins[0])<=23)){
            throw new InvalidTimeException(time);
        }
        if(!(Integer.parseInt(hrs_mins[1])>=0 && Integer.parseInt(hrs_mins[1])<=59)){
            throw  new InvalidTimeException(time);
        }
        return true;
    }
}
class UnsupportedFormatException extends Exception{
    UnsupportedFormatException(String time){
        super(time);
    }
}
class InvalidTimeException extends Exception{
    InvalidTimeException(String time){
        super(time);
    }
}
public class Zad7 {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
