package com.yuloran.utilityfee.ui.adapter

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aigestudio.wheelpicker.widgets.WheelDatePicker
import com.blankj.utilcode.util.TimeUtils
import com.yuloran.utilityfee.R
import com.yuloran.utilityfee.model.SimpleTextWatcher
import com.yuloran.utilityfee.model.Tenant
import java.text.SimpleDateFormat
import java.util.*

class TenantFeeAdapter(private val mActivity: Activity, private val mTenants: List<Tenant>) : RecyclerView.Adapter<TenantFeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantFeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_tenant_fee, parent, false)
        return TenantFeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenants.size
    }

    override fun onBindViewHolder(viewHolder: TenantFeeViewHolder, position: Int) {
        viewHolder.tvPosition.text = (position + 1).toString()
        viewHolder.etName.text = mTenants[position].name
        viewHolder.startDate.text = mTenants[position].startDate
        viewHolder.endDate.text = mTenants[position].endDate
        viewHolder.days.text = mTenants[position].days.toString()
        viewHolder.tvFeePercent.text = mTenants[position].feePercent
        viewHolder.tvFee.text = mTenants[position].fee
    }

}