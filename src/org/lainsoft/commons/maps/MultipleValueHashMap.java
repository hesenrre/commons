package org.lainsoft.commons.maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@SuppressWarnings("serial")
public class MultipleValueHashMap<K,V> extends HashMap<K,V> {
	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		if(this.containsKey(key)){
			if(this.get(key) instanceof Collection<?>){
				((Collection<V>)this.get(key)).add(value);
			}else{
				value = (V)createCollection(this.get(key), value);
			}
		}
		return super.put(key, value);
	}			
	private Collection<V>
	createCollection(final V old, final V last){
		return new ArrayList<V>(){
			private static final long serialVersionUID = -751409211270333531L;
			{
				add(old);
				add(last);
			}};
	}
}
