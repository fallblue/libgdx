package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;

/** Extend this class to implement a material attribute.
 * Register the attribute type by statically calling the {@link #register(String)} method, 
 * whose return value should be used to instantiate the attribute. 
 * A class can implement multiple types 
 * @author Xoppa */
public abstract class Attribute {
	/** The registered type aliases */
	private final static Array<String> types = new Array<String>();
	
	/** @return The ID of the specified attribute type, or zero if not available */
	public final static long getAttributeType(final String alias) {
		for (int i = 0; i < types.size; i++)
			if (types.get(i).compareTo(alias)==0)
				return 1L << i;
		return 0;
	}
	
	/** @return The alias of the specified attribute type, or null if not available. */
	public final static String getAttributeAlias(final long type) {
		int idx = -1;
		while (type != 0 && ++idx < 63 && (((type >> idx) & 1) == 0));
		return (idx >= 0 && idx < types.size) ? types.get(idx) : null;
	}
	
	/** Use {@link Attribute#register(String)} instead */ 
	protected final static long register(final String alias) {
		long result = getAttributeType(alias);
		if (result > 0)
			return result;
		types.add(alias);
		return 1L << (types.size - 1);
	}
	
	/** The type of this attribute */
	public final long type;
	protected Attribute(final long type) {
		this.type = type;
	}
	
	/** @return An exact copy of this attribute */
	public abstract Attribute copy();
	
	protected abstract boolean equals(Attribute other);
	
	@Override
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof Attribute)) return false;
		final Attribute other = (Attribute)obj;
		if (this.type != other.type) return false;
		return equals(other);
	}
	
	@Override
	public String toString () {
		return getAttributeAlias(type);
	}
}