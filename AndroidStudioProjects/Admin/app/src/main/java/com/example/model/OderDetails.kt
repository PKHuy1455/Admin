package com.example.model

import android.os.Parcel
import android.os.Parcelable

class OderDetails(): Parcelable {
    var userIid:String? = null
    var userName:String? = null
    var foodNames:MutableList<String>?= null
    var foodImages:MutableList<String>? = null
    var foodPrices:MutableList<String>? = null
    var address:String? =null
    var totalPrice:String? = null
    var phoneNumber:String? = null
    var oderAccepted:Boolean = false
    var paymentReceived: Boolean = false
    var itemPushKey:String? =null
    var currentTiem:Long = 0

    constructor(parcel: Parcel) : this() {
        userIid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        oderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTiem = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userIid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (oderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTiem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OderDetails> {
        override fun createFromParcel(parcel: Parcel): OderDetails {
            return OderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OderDetails?> {
            return arrayOfNulls(size)
        }
    }
}