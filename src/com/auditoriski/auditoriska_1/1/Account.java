package auditoriski.auditoriska_1;

public abstract class Account{
  private String holderName;
  private String accountNumber;
  private Double balance;
  Account(){}
  Account(String holderName,String accountNumber,Double balance){
    this.holderName = holderName;
    this.accountNumber = accountNumber;
    this.balance = balance;
  }
  public Double getBalance(){
    return this.balance;
  }
  public void increaseBalance(Double amount){
    this.balance += amount;
  }
  public void decreaseBalance(Double amount){
    this.balance -= amount;
  }
}

