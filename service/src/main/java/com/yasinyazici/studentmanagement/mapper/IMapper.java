package com.yasinyazici.studentmanagement.mapper;

public interface IMapper<K, V> {

    public K toKey(V value);

    public V toValue(K key);

}
