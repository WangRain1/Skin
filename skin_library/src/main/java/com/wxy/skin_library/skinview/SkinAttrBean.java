package com.wxy.skin_library.skinview;

import java.util.Objects;

public class SkinAttrBean {

    String typeName;
    String entryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkinAttrBean that = (SkinAttrBean) o;
        return Objects.equals(typeName, that.typeName) &&
                Objects.equals(entryName, that.entryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, entryName);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }
}
