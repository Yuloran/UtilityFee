package com.yuloran.utilityfee.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.yuloran.utilityfee.R

class TenantFeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvPosition = itemView.findViewById<TextView>(R.id.tv_position)
    val etName = itemView.findViewById<TextView>(R.id.et_name)
    val startDate = itemView.findViewById<TextView>(R.id.et_start_date)
    val endDate = itemView.findViewById<TextView>(R.id.et_end_date)
    val days = itemView.findViewById<TextView>(R.id.tv_fee_days)
    val tvFeePercent = itemView.findViewById<TextView>(R.id.tv_fee_percent)
    val tvFee = itemView.findViewById<TextView>(R.id.tv_fee)
}