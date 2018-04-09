package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CreditHelper {
    private static final Double INTRESTS = 0.075;
    private static final Double PROVISION = 0.015;
    private static Double installment, credit, costs;

    public static Double getINTRESTS() {
        return INTRESTS;
    }

    public static Double getPROVISION() {
        return PROVISION;
    }

    public static Double getInstallment() {
        return installment;
    }

    public static Double getCredit() {
        return credit;
    }

    public static Double getCosts() {
        return costs;
    }

    public static void calculateCredit(double months, double amount) {

        Double factor = 1 + (1.0/12) * (INTRESTS + PROVISION);
        System.out.println(factor);
        Double installment = (amount * Math.pow(factor, months) * (factor-1)) / (Math.pow( factor, months) -1);


        BigDecimal bd = new BigDecimal(installment);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        CreditHelper.installment = bd.doubleValue();

        BigDecimal bd2 = new BigDecimal(installment * months);
        bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
        CreditHelper.credit = bd2.doubleValue();

        BigDecimal bd3 = new BigDecimal(CreditHelper.credit - amount);
        bd3 = bd3.setScale(2, RoundingMode.HALF_UP);
        CreditHelper.costs = bd3.doubleValue();

//        System.out.println(CreditHelper.installment);
//        System.out.println(CreditHelper.credit);
//        System.out.println(CreditHelper.costs);
    }
}
