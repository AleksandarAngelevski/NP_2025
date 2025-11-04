package auditoriski.auditoriska_1;

import java.util.Arrays;

public class Bank{ private Account[] accounts; private int totalAccounts; private int max;
  public Bank(int max){
    this.totalAccounts = 0;
    this.max = max;
    this.accounts = new Account[max];
}
  public void addAccount(Account account){
    if(totalAccounts == accounts.length){
      accounts = Arrays.copyOf(accounts, max*2);
    }
    accounts[totalAccounts++] = account;
  }
  public double totalAssest(){
    double sum=0;
    for(Account account : accounts){
      sum += account.getBalance();
    }
    return sum;
  }


  public void addInterest(){
    for(Account account : accounts){
      if(account instanceof InterestBearingAccount){
        InterestBearingAccount iba = (InterestBearingAccount) account;
        iba.addInterest();
      }
    }
  }
}
