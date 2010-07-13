package org.lainsoft.commons.collections.list;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collection;


public class SuffixedStringList
    implements List{
    
    private String suffix;
    private final List l;
    
    public static SuffixedStringList
    newInstance(String suffix){
        return new SuffixedStringList(suffix, null);
    }
    
    public static SuffixedStringList
    newInstance(String suffix, Collection c){
        return new SuffixedStringList(suffix, c);
    }
    
    private SuffixedStringList(String suffix, Collection c){
        this.suffix = suffix;
        l = new ArrayList(c);
        suffix_list();
    }
    
    public void 
    add(int i,Object o){
        if (o instanceof String){
            l.add(i,o+suffix);
            return;
        }
        throw new IllegalArgumentException("Object is an instance of "+o.getClass().getName()+" and must be a String");        
    }
    
    public boolean 
    add(Object o){ 
        if (o instanceof String){
            return l.add(o+suffix);
        }
        throw new IllegalArgumentException("Object is an instance of "+o.getClass().getName()+" and must be a String");
    }
    
    public boolean 
    addAll(Collection c){
        boolean r = l.addAll(c);
        suffix_list();
        return r;
    }
    
    public boolean 
    addAll(int index, Collection c){ 
        boolean r = l.addAll(index, c); 
        suffix_list();
        return r;
    }
    public void clear() { l.clear(); }
    public boolean contains(Object o){ return l.contains(o); }
    public boolean containsAll(Collection c){ return l.containsAll(c); }
    public boolean equals(Object o){ return l.equals(o); }
    public Object get(int index){ return l.get(index); }
    public int hashCode(){ return l.hashCode(); }
    public int indexOf(Object o){ return l.indexOf(o); }
    public boolean isEmpty() { return l.isEmpty(); }
    public Iterator iterator() { return l.iterator(); }
    public int lastIndexOf(Object o){ return l.lastIndexOf(o); }
    public ListIterator listIterator(){ return l.listIterator(); }
    public ListIterator listIterator(int index){return l.listIterator(index); }
    public Object remove(int index){ return l.remove(index); }
    public boolean remove(Object o){ return l.remove(o); }
    public boolean removeAll(Collection c) { return l.removeAll(c); }
    public boolean retainAll(Collection c){ return l.retainAll(c); }
    
    public Object 
    set(int index, Object element){ 
        if (element instanceof String)
            return l.set(index,element);
        throw new IllegalArgumentException("Object is an instance of "+element.getClass().getName()+" and must be a String");
    }
    
    public int size(){ return l.size(); }
    public List subList(int fromIndex, int toIndex){ return l.subList(fromIndex, toIndex); }
    public Object[] toArray(){ return l.toArray(); }
    public Object[] toArray(Object[] a){ return l.toArray(a); }
    
    private void
    suffix_list()
        throws IllegalArgumentException{
        if (l==null)
            return;
        for(int i=0; i < l.size(); i++){
            if (l.get(i) instanceof String){
                String content = l.get(i).toString();
                l.set(i, (content.lastIndexOf(suffix) >=0 && (content.length() - content.lastIndexOf(suffix))==suffix.length()) ? content : content+suffix);
            }else{
                throw new IllegalArgumentException("Object in list is an instance of"+l.get(i).getClass().getName()+" and must be a String");
            }
        }
    }
    
    public String
    toString(){
        return l.toString();
    }
}
