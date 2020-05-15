package models;

import export.*;

public interface Visitable {
    void accept(JDVisitor JDVisitor);
}
