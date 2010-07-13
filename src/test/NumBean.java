package test;

import java.util.*;
import org.lainsoft.commons.beanutils.ValidatedBean;
import org.apache.commons.lang.math.Fraction;

public class NumBean
    extends ValidatedBean{
    
    public double fl;
    
    public void 
    fl(double fl){
        this.fl = fl;
    }

    public double 
    fl(){
        return fl;
    }
    
    public String
    toString(){
        return "NumBean=(fl="+fl+")";
    }
    
    public static void
    main(String []args)
        throws Exception{
        Map map = new HashMap();
        map.put("fl",args[0]);
        NumBean test = new NumBean();
        //System.out.println("frac>"+Fraction.getFraction(args[0]));

        System.out.println("test>"+test);
        test.save(map);
        System.out.println("test>"+test);
        System.out.println("errors>"+test.human_readable_errors());

    }        
}
