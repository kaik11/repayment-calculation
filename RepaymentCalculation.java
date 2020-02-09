import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
* Calculation of repayment upon the termination of the insurance contract
*
* @author Kai K
* 
* Period of insurance 01/01/2020 00:00 - 31/12/2020 23:59
* The insurance policy was terminated on 05/06/2020 15:00
* Insurance premium at 150 EUR (100% paid)
* Service charge 7 EUR
*/ 
public class RepaymentCalculation {
    
    private static DecimalFormat df = new DecimalFormat("0.00"); // Display 2 decimal places
 
    public static void main(String[] args) {
        try {
            dateDiff("01/01/2020 00:00", "05/06/2020 15:00", "31/12/2020 23:59", "dd/MM/yyyy HH:mm");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
               e.printStackTrace();
        }
    }
    
    private static void dateDiff(String startDate, String actualEndDate, String endDate, String pattern) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
        Date sd = sdf.parse(startDate);
        Date aed = sdf.parse(actualEndDate);
        Date ed = sdf.parse(endDate);
		
		// Calculation of time intervals
        long diffInMillisActual = aed.getTime() - sd.getTime();
        long diffInMillisPeriod = ed.getTime() - sd.getTime();
        
		// TimeUnit conversion from Milliseconds to Days
        long daysDiffActual = TimeUnit.DAYS.convert(diffInMillisActual, TimeUnit.MILLISECONDS);
        long daysDiffPeriod = TimeUnit.DAYS.convert(diffInMillisPeriod, TimeUnit.MILLISECONDS);
		
		// Conversion from Long to Double
        double p = (double)daysDiffPeriod;
        double a = (double)daysDiffActual;
        
        double ip = 150; // Insurance premium
        double sc = 7; // Service charge
        double r = (ip - ((ip / p) * a)) - sc; // Repayment calculation
 
        System.out.println("This insurance was valid for " + daysDiffActual + " day(s)"); // This insurance was valid for 156 day(s)
		
        if (r < 0) {
			// Because cannot ask for more money from the customer in case of a refund
			System.out.println("The repayment amount: " + 0 + " EUR");
		} else {
		df.setRoundingMode(RoundingMode.UP); // Rounding up
        System.out.println("The repayment amount: " + df.format(r) + " EUR"); // The repayment amount: 78.90 EUR
		}
        
        System.out.println("Service charge: " + df.format(sc) + " EUR"); // Service charge: 7.00 EUR
    }
}