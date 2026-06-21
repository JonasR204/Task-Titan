package model;

public final class Utility {
    public static int[] leapYearMonthDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int totalDaysInLeapYear = 366;
    public static int[] normalYearMonthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int totalDaysInYear = 365;

    public static void CopyArray (int[] a, int[]b){
        if(a.length != b.length){
            System.out.println("Can't copy with incompatible lengths!");
            return;
        }
        for(int i = 0; i < a.length; i++){
            a[i] = b[i];
        }
    }

    public static boolean CheckDateForValidity(int[] date){
        if(date.length != 3){
            ShowErrorMessage(/*say it was an error at date*/);
            return false;
        }

        if(date[0] <= 0 || date[1] <= 0 || date[1] > 12 || date[2] < 0){
            ShowErrorMessage(/*say it was an error at date*/);
            return false;
        }

        if(IsLeapYear(date[2])){
            for(int i = 1; i <= 12; i++){
                if(date[1] == i){
                    if(date[0] > leapYearMonthDays[i-1]){
                        ShowErrorMessage(/*say it was an error at date*/);
                        return false;
                    }
                }
            }
        }else{
            for(int i = 1; i <= 12; i++){
                if(date[1] == i){
                    if(date[0] > normalYearMonthDays[i-1]){
                        ShowErrorMessage(/*say it was an error at date*/);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int GetDaysOfADate(int[] date){
        if(CheckDateForValidity(date)) {
            for(int i = 0; i <= date[2]; i++){
                if(IsLeapYear(date[2])){

                }
            }
        }
        return 0;
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
