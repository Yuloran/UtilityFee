package com.yuloran.utilityfee.ui

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.aigestudio.wheelpicker.widgets.WheelDatePicker
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.yuloran.utilityfee.R
import com.yuloran.utilityfee.model.Tenant
import com.yuloran.utilityfee.ui.adapter.TenantFeeAdapter
import kotlinx.android.synthetic.main.activity_fee_cal.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class FeeCalActivity : AppCompatActivity() {

    private val mTenants = arrayListOf<Tenant>()

    private val mAdapter = TenantFeeAdapter(this, mTenants)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fee_cal)

        val actionBar = this.supportActionBar
        actionBar!!.title = "缴费明细计算"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val tenants = intent.getParcelableArrayListExtra<Tenant>("tenants")
        LogUtils.d("onCreate: %s", tenants)

        rv_fee_detail.layoutManager = LinearLayoutManager(this)
        rv_fee_detail.adapter = mAdapter

        mTenants.addAll(tenants)
        mAdapter.notifyDataSetChanged()

        et_start_date.setOnClickListener {
            val datePicker = WheelDatePicker(this@FeeCalActivity)
            AlertDialog.Builder(this@FeeCalActivity)
                    .setTitle("选择日期")
                    .setView(datePicker)
                    .setPositiveButton("确定") { _, _ ->
                        val dateStr = TimeUtils.date2String(datePicker.currentDate, SimpleDateFormat("yyyy-M-d", Locale.US))
                        et_start_date.text = dateStr
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }

        et_end_date.text = TimeUtils.date2String(Date(), SimpleDateFormat("yyyy-M-d", Locale.US))
        et_end_date.setOnClickListener {
            val datePicker = WheelDatePicker(this@FeeCalActivity)
            AlertDialog.Builder(this@FeeCalActivity)
                    .setTitle("选择日期")
                    .setView(datePicker)
                    .setPositiveButton("确定") { _, _ ->
                        val dateStr = TimeUtils.date2String(datePicker.currentDate, SimpleDateFormat("yyyy-M-d", Locale.US))
                        et_end_date.text = dateStr
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }

        bt_cal.setOnClickListener {
            if (isInvalidFeeSum()) {
                ToastUtils.showShort("总额信息输入有误，请重新输入!")
                return@setOnClickListener
            }

            cal()
        }
    }

    private fun isInvalidFeeSum(): Boolean {
        return et_fee_sum.text.isEmpty() || et_start_date.text.isEmpty() || et_end_date.text.isEmpty()
    }

    private fun cal() {
        val feeSumStartDate = et_start_date.text.toString()
        val feeSumEndDate = et_end_date.text.toString()

        var daysSum = 0
        for (tenant in mTenants) {
            val preStartSpan = TimeUtils.getTimeSpan(tenant.startDate, feeSumStartDate, SimpleDateFormat("yyyy-M-d", Locale.US), TimeConstants.DAY)
            val postStartSpan = TimeUtils.getTimeSpan(feeSumEndDate, tenant.startDate, SimpleDateFormat("yyyy-M-d", Locale.US), TimeConstants.DAY)
            if (preStartSpan < 0 || postStartSpan <= 0) {
                ToastUtils.showShort(String.format("%s 的入住日期[%s]填写错误，请重新填写！", tenant.name, tenant.startDate))
                return
            }

            val diff = TimeUtils.getTimeSpan(tenant.endDate, tenant.startDate, SimpleDateFormat("yyyy-M-d", Locale.US), TimeConstants.DAY)
            val postEndSpan = TimeUtils.getTimeSpan(feeSumEndDate, tenant.endDate, SimpleDateFormat("yyyy-M-d", Locale.US), TimeConstants.DAY)
            if (diff < 0 || postEndSpan < 0) {
                ToastUtils.showShort(String.format("%s 的退住日期[%s]填写错误，请重新填写！", tenant.name, tenant.endDate))
                return
            }
            tenant.days = diff.toInt()
            daysSum += tenant.days
        }

        val feeSum: Float = et_fee_sum.text.toString().toFloat()
        LogUtils.d("cal: fee sum : %f", feeSum)
        for (tenant in mTenants) {
            val percent = tenant.days * 1.0F / daysSum
            tenant.feePercent = BigDecimal((percent * 100).toString()).setScale(2, RoundingMode.HALF_UP).toDouble().toString() + "%"
            tenant.fee = BigDecimal((feeSum * percent).toString()).setScale(2, RoundingMode.HALF_UP).toDouble().toString() + "元"
            LogUtils.d("cal: %s, %s, %s", tenant.name, tenant.feePercent, tenant.fee)
        }

        mAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}