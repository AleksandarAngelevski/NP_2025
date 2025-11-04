package auditoriski.auditoriska_2;

public class Addition implements Operation{

  @Override
  public Integer apply(int num1,int num2){
    return num1 + num2;
  }
}
