package com.yuloran.utilityfee.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tenant(var name: String = "", var startDate: String = "", var endDate: String = "", var days: Int = 0, var feePercent:
String = "", var fee: String = "") : Parcelable