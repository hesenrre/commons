package test;


import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
//import org.lainsoft.commons.beanutils.ValidatedBean;

public class BeanTest
    extends ParentTest{
    
    public static String validates_format_of[] = {"date :with => [\\d]{1,2}/[\\d]{1,2}/[\\d]{1,4}", "login :with => [\\d]{2,3}+"};
    public static String validates_presence_of = "id, password";
    public static String validates_numericality_of = "id, password";
    //public static String validates_length_of[] = {"password :is => 10", "login :in => 2..5"};

    private Float id;
    //private String login;
    private Float password;
    // private String date;
    //private Date creation;

    
    public void
    setId(Float id){
        this.id = id;
    }

    // public void
//     setCreation(Date creation){
//         this.creation = creation;
//     }

    public void
    password (Float password){
        this.password = password;
    }

    public Float
    password(){
        return password;
    }
    
    
    // public void
//     setLogin(String login){
//         this.login = login;
//     }
    

    public String
    toString(){
        return "BeanTest=[id="+id+",password="+password+"]";
    }

    public static void
    main(String []args)
        throws Exception{
        Map params = new TreeMap();        
        params.put("id","1");
        params.put("password","13");
        BeanTest test = new BeanTest();
        
        //test.setError("password", "password must be a number between 21 and 2002");
        System.out.println("accessors>"+test.write_attr_accessors());

        System.out.println("SAVE>"+test.save(params));

        System.out.println(test.errors());

        System.out.println(test);

    }

}
