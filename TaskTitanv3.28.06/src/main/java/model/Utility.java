package model;
import java.time.LocalDate;


public final class Utility {
    public static int[] leapYearMonthDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int totalDaysInLeapYear = 366;
    public static int[] normalYearMonthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int totalDaysInYear = 365;

    public static int GetDaysBetweenDates(LocalDate date, LocalDate deadline){
        return  DaysSinceZero(deadline) - DaysSinceZero(date);
    }

    public static int DaysSinceZero(LocalDate date){
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        int i = 0;
        int totalDays = day;

        if(IsLeapYear(year)){
            for(int j = 0; j < 12; j++){
                if(month == j + 1){
                    break;
                }
                totalDays += leapYearMonthDays[j];
            }
        }else{
            for(int j = 0; j < 12; j++){
                if(month == j + 1){
                    break;
                }
                totalDays += normalYearMonthDays[j];
            }
        }
        for(int a = 0; a < year; a++){
            if(IsLeapYear(year)){
                totalDays += totalDaysInLeapYear;
            }
            else{
                totalDays += totalDaysInYear;
            }
        }
        return totalDays;
    }


    public static boolean IsLeapYear(int year){
        boolean isLeap = false;
        if(year < 0) {
            ShowErrorMessage(/*say it was an error at date*/);
            return isLeap;
        }

        if((year % 4) == 0){
            isLeap = true;
            if((year % 100) == 0){
                if((year % 400) == 0){
                    isLeap = true;
                }else{
                    isLeap = false;
                }
            }
        }

        return isLeap;
    }

    public static void ShowErrorMessage(/*error Type*/){
        //Show Via GUI an error message
    }

}