package br.edu.ifpe.acsntrs.web.control;

import java.util.Map.Entry;

final public class MyEntry<K, V> implements Entry<K, V>
{
	private K key;
	private V value;
	
	public MyEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	@Override
	public K getKey()
	{
		return this.key;
	}

	@Override
	public V getValue()
	{
		return this.value;
	}

	@Override
	public V setValue(V value)
	{
		V old = this.value;
		this.value = value;
		return old;
	}

	public void setKey(K key)
	{
		this.key = key;
	}
}
