package models;

import export.*;

public class Conclusion extends Node {
	private final String restriction;

	public Conclusion(String alias, String label, String restriction) {
		super(alias, label);
		this.restriction = restriction;
	}

	@Override
	public void accept(JDVisitor jDVisitor) {
		jDVisitor.visitConclusion(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((restriction == null) ? 0 : restriction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conclusion other = (Conclusion) obj;
		if (restriction == null) {
			if (other.restriction != null)
				return false;
		} 
		else {
			if (!restriction.equals(other.restriction))
				return false;
		}
		return true;
	}

	public String getRestriction() {
		return restriction;
	}
}
