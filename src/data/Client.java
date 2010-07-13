
package data;

import java.util.*;
import org.lainsoft.commons.beanutils.ValidatedBean;


public class Client
    extends ValidatedBean{
        
    public static String validates_presence_of = "name";

    public String name;
    
    public void
    name(String name){
        this.name = name;
    }
    
    public String
    name(){
        return name;
    }

    public String
    toString(){
        return "Client[name="+name+"]";
    }

    public static void
    main(String []args)
        throws Exception{
        Map params = new TreeMap();        
        params.put("name","12");
        Client test = new Client();
        
        test.save(params);
        System.out.println(test.errors());
        System.out.println(test);

    }

}
