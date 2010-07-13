package org.lainsoft.commons.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;


public abstract class ValidatedBean
    extends Bean{
    
    private Log log = LogFactory.getLog(this.getClass());

    private Map errors;
    private static List numerals = 
        Arrays.asList(new String[]{"byte", "double", "float", "integer", "int", "long", "short"});
    
    
    public Map
    errors(){        
        errors = errors == null ? new TreeMap() : errors;
        return Collections.unmodifiableMap(errors);
    }

    private List
    extract_fields_for(String validation_field){
        List fields = new ArrayList();
        try{
            log.debug("Field in extract_fields_for>validation_field>"+validation_field);
            Field field = this.getClass().getDeclaredField(validation_field);
            log.debug("Field in extract_fields_for>"+field);
            String vp = (vp = (String)field.get(this)) == null ? "" : vp;
            fields = Arrays.asList(vp.split("[\\s]{0,},[\\s]{0,}"));
            
        }catch(NoSuchFieldException nsfe){
            log.debug("No "+validation_field+" rule found in "+this.getClass().getName());
        }catch(IllegalAccessException iae){
            log.debug("Couldn't access to "+validation_field+" rule, please check that has public access");
        }catch(ClassCastException cce){
            log.debug(validation_field+" rule must be String");
        }
        return fields;
    }
    
    private Map
    extract_fields_for(String validation_field, String delimiting_pattern){
        Map fields = new TreeMap();
        List formats = new ArrayList();
        try{
            Field field = this.getClass().getDeclaredField(validation_field);
            String[] temp_vals = (temp_vals = (String[]) field.get(this)) == null ? new String[0] : temp_vals;
            formats = Arrays.asList(temp_vals);
        }catch(NoSuchFieldException nsfe){
            log.debug("No "+validation_field+" rule found "+this.getClass().getName());
        }catch(IllegalAccessException eae){
            log.debug("Couldn't access to "+validation_field+" rule, please check that has public access");            
        }catch(ClassCastException cce){
            log.debug(validation_field+" rule must be String Array");            
        }        
        for(Iterator it = formats.iterator(); it.hasNext();){
            String format = it.next().toString();                
            String []patterns = (patterns = format.split(delimiting_pattern)) == null ? new String[0]: patterns;
            if (patterns.length == 2){
                fields.put(patterns[0], parse_value(patterns[1]));
            }
        }
        return fields;
    }
        
    
    public boolean
    save(Map params)
        throws InvocationTargetException, NoSuchMethodException{
        params = params == null ? new TreeMap() : params;
        errors = errors == null ? new TreeMap() : errors;
        boolean result = true;
        for (Iterator it = params.keySet().iterator(); it.hasNext();){
            String param = it.next().toString();
            String value = params.get(param).toString();
            result = ((validates_length_in(param,value) && validates_format_in(param,value) && validates_numericality_in(param,value) && save(param,value)) && result);
        }
        
        return validates_presence_in(params) && result && errors.size() < 1;
    }
    
    private boolean
    save(String param, String value)
        throws InvocationTargetException, NoSuchMethodException{
        
        Method method = (Method)write_attr_accessors().get(param);
        
        if (method == null){
            log.debug("No accessor for '"+param+"' in "+this.getClass().getName());
            return true;
        }else{
        
            Class [] params = method.getParameterTypes();
            
            if (params.length < 1 || params.length > 1) return false;
            
            
            String param_type = 
                (param_type = params[0].getName()).lastIndexOf(".") > 0 ? 
                param_type.substring(param_type.lastIndexOf(".")+1) 
                : param_type;
            
            List args = new ArrayList();
            if(param_type.equals("String")){
                args.add(value);
            }else if (numerals.contains(param_type.toLowerCase())){// && is_number(value)){
                try{                    
                    args.add(get_numeral_for(param_type,value));
                }catch(NumberFormatException e){
                    log.error("param>"+param+" \tvalue>"+value,e);
                    errors.put(param,new String[]{"8","must be a number"});
                    return false;
                }
            }else if (param_type.equals("Date")){
                try{
                    args.add (new SimpleDateFormat("MMddyyyy").parse(value));
                }catch(ParseException pe){
                    errors.put(param, new String[]{"10","invalid date format, format must be MMddyyyy"});
                    return false;
                }
            }else{
                args.add(null);
            }
            
            try{
                method.invoke(this,args.toArray());
                return true;
            }catch(IllegalAccessException iae){
                errors.put(this.getClass().getName(),new String[]{"0","Couldn't save parameter "+param+" because it's accessor may be private"});
                return false;
            }
        }
    }
    
    private Object
    get_numeral_for(String param_type, String value)
        throws NumberFormatException{
        value = value.trim().equals("") ? "0" : value;
        if (param_type.equalsIgnoreCase("int") || param_type.equalsIgnoreCase("integer"))
            return new Integer(Fraction.getFraction(value).intValue());
        else if (param_type.equalsIgnoreCase("byte"))
            return Byte.valueOf(value);
        else if (param_type.equalsIgnoreCase("double"))
            return new Double(Fraction.getFraction(value).doubleValue());
        else if (param_type.equalsIgnoreCase("float"))
            return new Float(Fraction.getFraction(value).floatValue());
        else if (param_type.equalsIgnoreCase("long"))
            return new Long(Fraction.getFraction(value).longValue());
        else if (param_type.equalsIgnoreCase("short"))
            return new Short(Fraction.getFraction(value).shortValue());
        return null;
    }


    private boolean
    validates_length_in(String param, String value){
        Map _length_of = extract_fields_for("validates_length_of","[\\s]{0,}:");
        if (_length_of.containsKey(param)){
            Map deteminants = (Map)_length_of.get(param);
            try {
                if (deteminants.containsKey("maximum") && value.length() > Integer.parseInt(deteminants.get("maximum").toString())){
                    errors.put(param,new String[]{"1","is bigger than "+deteminants.get("maximum")});
                    return false;
                }else if (deteminants.containsKey("minimum") && value.length() < Integer.parseInt(deteminants.get("minimum").toString())){
                    errors.put(param,new String[]{"2","is smaller than "+deteminants.get("minimum")});
                    return false;
                }else if(deteminants.containsKey("is") && value.length() != Integer.parseInt(deteminants.get("is").toString())){
                    errors.put(param,new String[]{"3","value must be "+deteminants.get("is")});
                    return false;
                }else if(deteminants.containsKey("in")){
                    String[] range = deteminants.get("in").toString().split("[\\s]{0,}\\.\\.[\\s]{0,}");
                    if (range.length == 2){
                        int x = Integer.parseInt(range[0]);
                        int y = Integer.parseInt(range[1]);
                        if (x < y ? (value.length() < x || value.length() > y) : (value.length() < y || value.length() > x)){
                            errors.put(param,new String[]{"4","is not in the range "+ (x<y?x:y) +".."+(x>y?x:y)});
                            return false;
                        }
                    }else{
                        errors.put(param,new String[]{"5","invalid declared range, please verify"});
                        return false;
                    }
                }
            }catch (NumberFormatException nfe){
                errors.put(param, new String[]{"6","invalid length or range, please verify"});
                return false;
            }            
        }
        return true;
    }
    
    
    private boolean
    validates_format_in(String param, String value){
        Map _format_of = extract_fields_for("validates_format_of","[\\s]{0,}:with[\\s]{0,}=>[\\s]{0,}");
        if(_format_of.containsKey(param) && value !=null && !value.trim().equals("") && !value.matches(_format_of.get(param).toString())){
            errors.put(param, new String[]{"7","does not match with the pattern"});
            return false;
        }
        return true;
    }
    
    private boolean
    validates_numericality_in(String param, String value){
        List _numericality_of = extract_fields_for("validates_numericality_of");
        if(_numericality_of.contains(param) && value !=null && !value.trim().equals("") && !is_number(value)){
            errors.put(param,new String[]{"8","must be a number"});
            return false;
        }
        return true;
    }
    
    private boolean
    validates_presence_in(Map params){
        List _presence_of = extract_fields_for("validates_presence_of");
        
        boolean result = true;
        for(Iterator it = _presence_of.iterator(); it.hasNext();){
            String field = (String)it.next();
            if (!field.equals("") && (!params.containsKey(field) || empty(params.get(field)))){
                result = false;
                errors.put(field,new String[]{"9","can't be empty"});
            }
        }
        log.debug("Errors in "+this.getClass().getName()+">"+human_readable_errors());
        return result;
    }

    private boolean
    empty(Object obj){
        return obj == null || (obj instanceof String && obj.toString().trim().equals(""));
    }

    
    private boolean
    is_number(Object num){
        return num == null ? true : num instanceof Number || (num instanceof String && (num.toString().trim().equals("") || num.toString().trim().matches("([-]?)([\\d]+[\\.]?[\\d]{0,}|[\\d]+[\\s]{0,}[\\d]+/[\\d]+)")));
    }

    private Object
    parse_value(String value){
        List temp = Arrays.asList(value.split("[\\s]{0,}=>[\\s]{0,}"));
        if (temp.size() == 2){
            Map result = new TreeMap();
            result.put(temp.get(0),temp.get(1));
            return result;
        }
        return value;
    }


    public void
    setError(String field, String msg){
        errors = errors == null ? new TreeMap() : errors;
        
        errors.put(field, new String[]{"11",msg});
    }
    
    
    public Map 
    human_readable_errors(){
        Map hre = new TreeMap(errors);
        for (Iterator it = hre.keySet().iterator(); it.hasNext();){
            String key = (String)it.next();
            hre.put(key, Arrays.asList((String[])hre.get(key)));
        }
        return hre;
    }
}
