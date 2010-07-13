package org.lainsoft.commons.collections.list;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collection;

public class TypedList
    implements List{
    
    private final List l;

    public TypedList(){
        l = new ArrayList();
    }
    
    public TypedList(Collection c){
        l = new ArrayList(c);
    }
    
    public void add(int i,Object o){ l.add(i,o); }
    public boolean add(Object o){ return l.add(o); }
    public boolean addAll(Collection c){ return l.addAll(c); }
    public boolean addAll(int index, Collection c){ return l.addAll(index, c); }
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
    public Object set(int index, Object element){ return l.set(index,element); }
    public int size(){ return l.size(); }
    public List subList(int fromIndex, int toIndex){ return l.subList(fromIndex, toIndex); }
    public Object[] toArray(){ return l.toArray(); }
    public Object[] toArray(Object[] a){ return l.toArray(a); }
    
    public List
    getDecoratedList(Class c){
        List list = new ArrayList();
        for (int i=0; i<size();i++){
            if (c.getName().equals(get(i).getClass().getName())){
                list.add(get(i));
            }
        }
        return list;
    }
}
