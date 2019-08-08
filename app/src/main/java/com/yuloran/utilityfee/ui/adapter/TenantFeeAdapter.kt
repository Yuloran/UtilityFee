package com.yuloran.utilityfee.ui.adapter

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aigestudio.wheelpicker.widgets.WheelDatePicker
import com.blankj.utilcode.util.TimeUtils
import com.yuloran.utilityfee.R
import com.yuloran.utilityfee.model.SimpleTextWatcher
import com.yuloran.utilityfee.model.Tenant
import kotlinx.android.synthetic.main.rv_item_tenant_fee.view.*
import java.text.SimpleDateFormat
import java.util.*

class TenantFeeAdapter(private val mActivity: Activity, private val mTenants: List<Tenant>) :
    RecyclerView.Adapter<TenantFeeAdapter.TenantFeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantFeeAdapter.TenantFeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_tenant_fee, parent, false)
        return TenantFeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenants.size
    }

    override fun onBindViewHolder(viewHolder: TenantFeeViewHolder, position: Int) {
        viewHolder.itemView.tv_position.text = (position + 1).toString()
        viewHolder.itemView.et_name.text = mTenants[position].name
        viewHolder.itemView.et_start_date.text = mTenants[position].startDate
        viewHolder.itemView.et_end_date.text = mTenants[position].endDate
        viewHolder.itemView.tv_fee_days.text = mTenants[position].days.toString()
        viewHolder.itemView.tv_fee_percent.text = mTenants[position].feePercent
        viewHolder.itemView.tv_fee.text = mTenants[position].fee
    }

    class TenantFeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}