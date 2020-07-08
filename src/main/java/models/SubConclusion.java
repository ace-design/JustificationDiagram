package models;

import export.*;

public class SubConclusion extends Node {
    private String restriction;

    public SubConclusion(String alias, String label) {
        super(alias, label);
    }

    @Override
    public void accept(JDVisitor jDVisitor) {
        jDVisitor.visitSubConclusion(this);
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubConclusion other = (SubConclusion) obj;
		if (restriction == null) {
			if (other.restriction != null)
				return false;
		} else {
			if (!restriction.equals(other.restriction))
				return false;
		}
		return true;
	}

	public String getRestriction() {
		return restriction;
	}
}
 