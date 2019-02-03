package org.algorrim.protobuff.sample.model;

import java.util.Objects;

/**
 * POJO class that contains a simple object.
 */
public class SimpleObject {

    private int id;
    private String name;

    public SimpleObject() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObject that = (SimpleObject) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "SimpleObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
