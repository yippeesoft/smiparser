/**
 * Copyright 2011-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsmiparser.util.multimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

@SuppressWarnings("unchecked")
public class GenMultiMap<K, V> implements Serializable {

	static final long serialVersionUID = 1;

	protected MultiMap multiMap;

	public static <K, V> GenMultiMap<K, V> hashMap() {
		return new GenMultiMap<K, V>(MultiValueMap.decorate(new HashMap(),
				ArrayList.class));
	}

	public static <K, V> GenMultiMap<K, V> treeMap() {
		return new GenMultiMap<K, V>(MultiValueMap.decorate(new TreeMap(),
				ArrayList.class));
	}

	public GenMultiMap(MultiMap impl) {
		multiMap = impl;
	}

	public int size() {
		// olala this is tricky stuff: the Commons MultiMap size is the number
		// of entries in the map
		// but what we really want is the total number of symbols in our symbol
		// maps
		return multiMap.values().size();
	}

	public boolean isEmpty() {
		return multiMap.isEmpty();
	}

	public boolean containsKey(K key) {
		return multiMap.containsKey(key);
	}

	public boolean containsValue(V value) {
		return multiMap.containsValue(value);
	}

	public List<V> getAll(K key) {
		List<V> result = (List<V>) multiMap.get(key);
		if (result == null) {
			result = Collections.emptyList();
		}
		return result;
	}

	public V getOne(K key) throws IllegalArgumentException {
		List<V> all = (List<V>) multiMap.get(key);
		if (all == null) {
			return null;
		}
		if (all.size() > 1) {
			throw new IllegalArgumentException(
					"More than one element was found for key: " + key);
		}
		return all.get(0);
	}

	public List<V> put(K key, Collection<V> value) {
		List<V> result = new ArrayList<V>();
		for (V v : value) {
			Object insertedObject = multiMap.put(key, v);
			if (insertedObject != null) {
				result.add((V) insertedObject);
			}
		}
		return result;
	}

	public V put(K key, V value) {
		return (V) multiMap.put(key, value);
	}

	public Collection<V> remove(K key) {
		return (Collection<V>) multiMap.remove(key);
	}

	public void putAllMultiMap(Map<? extends K, ? extends Collection<V>> t) {
		multiMap.putAll(t);
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public void clear() {
		multiMap.clear();
	}

	public Set<K> keySet() {
		return multiMap.keySet();
	}

	public Collection<V> values() {
		return multiMap.values();
	}

	public Set<Map.Entry<K, List<V>>> entrySet() {
		return multiMap.entrySet();
	}
}
