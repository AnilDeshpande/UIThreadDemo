package uithreadsdemo.youtube.com.uithreaddemo;

public class Util {

    static boolean isNumberPrime(int number){
        boolean flag = false;
        for(int i = 2; i <= number/2; ++i)
        {
            // condition for nonprime number
            if(number % i == 0)
            {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
