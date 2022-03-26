package com.yc.camera.model;

import java.io.Serializable;

public class SizeModel implements Serializable {
    public int width;
    public int height;

    public SizeModel(int w, int h) {
        this.width = w;
        this.height = h;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SizeModel)) {
            return false;
        } else {
            SizeModel s = (SizeModel) obj;
            return this.width == s.width && this.height == s.height;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
