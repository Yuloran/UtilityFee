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
import kotlinx.android.synthetic.main.rv_item_tenant.view.*
import java.text.SimpleDateFormat
import java.util.*

class TenantAdapter(private val mActivity: Activity, private val mTenants: List<Tenant>) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantAdapter.TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_tenant, parent, false)
        return TenantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenants.size
    }

    override fun onBindViewHolder(viewHolder: TenantViewHolder, position: Int) {
        viewHolder.itemView.tv_position.text = (position + 1).toString()

        viewHolder.itemView.et_name.setText(mTenants[position].name)
        viewHolder.itemView.et_name.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                mTenants[viewHolder.adapterPosition].name = s.toString()
            }
        })

        viewHolder.itemView.et_start_date.text = mTenants[position].startDate
        viewHolder.itemView.et_start_date.setOnClickListener {
            val datePicker = WheelDatePicker(mActivity)
            AlertDialog.Builder(mActivity)
                    .setTitle("选择日期")
                    .setView(datePicker)
                    .setPositiveButton("确定") { _, _ ->
                        val dateStr = TimeUtils.date2String(datePicker.currentDate, SimpleDateFormat("yyyy-M-d", Locale.US))
                        viewHolder.itemView.et_start_date.text = dateStr
                        mTenants[viewHolder.adapterPosition].startDate = dateStr
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }

        viewHolder.itemView.et_end_date.text = mTenants[position].endDate
        viewHolder.itemView.et_end_date.setOnClickListener {
            val datePicker = WheelDatePicker(mActivity)
            AlertDialog.Builder(mActivity)
                    .setTitle("选择日期")
                    .setView(datePicker)
                    .setPositiveButton("确定") { _, _ ->
                        val dateStr = TimeUtils.date2String(datePicker.currentDate, SimpleDateFormat("yyyy-M-d", Locale.US))
                        viewHolder.itemView.et_end_date.text = dateStr
                        mTenants[viewHolder.adapterPosition].endDate = dateStr
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }
    }

    class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}