package com.yuloran.utilityfee.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.yuloran.utilityfee.R

class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvPosition = itemView.findViewById<TextView>(R.id.tv_position)
    val etName = itemView.findViewById<EditText>(R.id.et_name)
    val startDate = itemView.findViewById<TextView>(R.id.et_start_date)
    val endDate = itemView.findViewById<TextView>(R.id.et_end_date)
}