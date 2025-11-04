package auditoriski.auditoriska_1;
import java.util.function.DoubleToLongFunction;

public class PlatinumCheckingAccount extends InterestCheckingAccount{
  PlatinumCheckingAccount(String holderName, String accountNumber, Double balance){
    super(holderName, accountNumber, balance);
  }
  @Override
  public void addInterest() {
      // TODO Auto-generated method stub
      increaseBalance(InterestCheckingAccount.INTEREST_RATE *2);

  }
}
