package test;

import org.lainsoft.commons.beanutils.ValidatedBean;

public class ParentTest
    extends ValidatedBean{

    public int number;

    public void
    setNumber(int number){
        this.number = number;
    }
    
    public int
    getNumber(){
        return number;
    }
    
}
