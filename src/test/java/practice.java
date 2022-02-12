import java.util.Scanner;

public class practice {

    public static boolean isPrimeNumber(int num){

        if(num<=1){
            return false;
        }
        for(int i=2;i < num;i++){
             if(num%i == 0){
                 return false;
             }
        }
         return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("enter the number");
        int number = in.nextInt();
        System.out.println(isPrimeNumber(number));
    }
}
