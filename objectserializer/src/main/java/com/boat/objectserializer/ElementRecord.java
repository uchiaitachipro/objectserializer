package com.boat.objectserializer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by uchia on 2019/1/25.
 */

public final class ElementRecord implements Parcelable {

    public static final String META_DATA_KEY = "$_meta_key";

    public static final byte ELEMENT_AIDL_SUPPORT = 1;
    public static final byte ELEMENT_OBJECT = 2;
    public static final byte ELEMENT_MAP = 3;
    public static final byte ELEMENT_ARRAY = 4;

    private final byte elementType;
    private final boolean isWrapperType;
    private final int elementCount;
    private final String[] elementsKey;

    public ElementRecord(
            byte elementType,
            boolean isWrapperType,
            int elementCount,
            String[] elementKey) {
        this.elementType = elementType;
        this.elementCount = elementCount;
        this.elementsKey = elementKey;
        this.isWrapperType = isWrapperType;
    }

    protected ElementRecord(Parcel in) {
        elementType = in.readByte();
        isWrapperType = in.readByte() != 0;
        elementCount = in.readInt();
        elementsKey = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(elementType);
        dest.writeByte((byte) (isWrapperType ? 1 : 0));
        dest.writeInt(elementCount);
        dest.writeStringArray(elementsKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ElementRecord> CREATOR = new Creator<ElementRecord>() {
        @Override
        public ElementRecord createFromParcel(Parcel in) {
            return new ElementRecord(in);
        }

        @Override
        public ElementRecord[] newArray(int size) {
            return new ElementRecord[size];
        }
    };

    public byte getElementType() {
        return elementType;
    }

    public boolean isWrapperType() {
        return isWrapperType;
    }

    public int getElementCount() {
        return elementCount;
    }

    public String[] getElementsKey() {
        return elementsKey;
    }
}
