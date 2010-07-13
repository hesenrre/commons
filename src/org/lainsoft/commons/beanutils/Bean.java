package org.lainsoft.commons.beanutils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


import org.lainsoft.commons.collections.list.SuffixedStringList;

public abstract class Bean{
    
    private static List write_common_prefixes = Arrays.asList(new String[]{"set","set_","add","put",""});
    private static List read_common_prefixes = Arrays.asList(new String[]{"get","get_",""});
    private Map _write_accessors;
    private Map _read_accessors;
    
    public Map
    write_attr_accessors(){
        return _write_accessors = (_write_accessors) == null ? search_for_accessors(this.getClass(),write_common_prefixes,true) : _write_accessors;
    }

    public Map
    read_attr_accessors(){
        return _read_accessors = (_read_accessors == null) ? search_for_accessors(this.getClass(),read_common_prefixes,false) : _read_accessors;
    }

    private Map
    search_for_accessors(Class current, List prefixes,boolean write){
        
        if (current.getName().equals("java.lang.Object") || current.getName().equals("org.lainsoft.commons.beanutils.Bean"))
            return new TreeMap();
                
        Field fields[] = current.getDeclaredFields();
        Map accessors = new TreeMap();
        
        accessors.putAll(search_for_accessors(current.getSuperclass(), prefixes, write));            
        for(int i=0; i < fields.length; i++){
            Method accessor;
            if ((accessor = search_for_accessor(fields[i].getName(), prefixes, current.getMethods(), write)) != null){
                accessors.put(fields[i].getName(),accessor);
            }
        }        
        return accessors;
    }


    public Map
    getValuesMap(){
        Map accessors = write_attr_accessors();
        Map values = new TreeMap();
        for(Iterator it = accessors.keySet().iterator();it.hasNext();){
            try{
                String key = (String)it.next();
                Field field = this.getClass().getDeclaredField(key);
                field.setAccessible(true);
                if (field.get(this) != null && !(field.get(this) instanceof Bean)){
                    values.put(key, (field.get(this) instanceof Date) ? new SimpleDateFormat("MMddyyyy").format((Date)field.get(this)) : field.get(this).toString());
                }
            }catch(NoSuchFieldException e){
                e.printStackTrace();
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return values;
    }
    

    public Object 
    get(String field)
        throws NoSuchMethodException, IllegalAccessException,InvocationTargetException{
        Map accessors = read_attr_accessors();
        
        if (accessors.containsKey(field)){
            Method method = (Method)accessors.get(field);
            return method.invoke(this, new Object[0]);
        }
        throw new NoSuchMethodException("Method for accessing "+field+" does not exist");
    }

    private Method
    search_for_accessor(String field, List prefixes, Method[] methods,boolean write){
        SuffixedStringList list = SuffixedStringList.newInstance(field.toLowerCase(),prefixes);
        for(int i=0; i < methods.length; i++){            
            if (list.contains(methods[i].getName().toLowerCase()) &&
                (write ? 
                 methods[i].getReturnType().getName().equals("void")
                 : methods[i].getParameterTypes().length <= 0))
                return methods[i];
        }
        return null;
    }           
}
