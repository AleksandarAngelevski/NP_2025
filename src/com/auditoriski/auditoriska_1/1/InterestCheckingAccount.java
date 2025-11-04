package auditoriski.auditoriska_1;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{
  public static final double INTEREST_RATE = 0.03;
  public InterestCheckingAccount(String holderName,String accountNumber, Double balance){
    super(holderName, accountNumber, balance);
  }
  public void addInterest(){
    this.increaseBalance(this.getBalance()*INTEREST_RATE);
  }
}
