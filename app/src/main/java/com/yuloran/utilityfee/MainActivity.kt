package com.yuloran.utilityfee

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.yuloran.utilityfee.model.Tenant
import com.yuloran.utilityfee.ui.adapter.TenantAdapter
import com.yuloran.utilityfee.ui.FeeCalActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mTenantsCount = -1

    private val mTenants = arrayListOf<Tenant>()
    private val mTenantAdapter = TenantAdapter(this, mTenants)

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.getConfig().globalTag = "rwx"

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_ok.setOnClickListener {
            if (isInvalidNumber(et_tenants_count.text.toString())) {
                ToastUtils.showShort("输入的缴费人数不对，请重新输入！")
                return@setOnClickListener
            }
            initTenants()
        }

        rv_tenants.layoutManager = LinearLayoutManager(this)
        rv_tenants.adapter = mTenantAdapter

        btn_fee_cal.setOnClickListener {
            if (isInvalidTenants()) {
                ToastUtils.showShort("输入的缴费信息不对，请重新输入！")
                return@setOnClickListener
            }
            val intent = Intent(this, FeeCalActivity::class.java)
            intent.putParcelableArrayListExtra("tenants", mTenants)
            startActivity(intent)
        }
    }

    private fun isInvalidNumber(text: String): Boolean {
        try {
            mTenantsCount = text.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return mTenantsCount <= 0
    }

    private fun initTenants() {
        mTenants.clear()
        val endDate = TimeUtils.date2String(Date(), SimpleDateFormat("yyyy-M-d", Locale.US))
        for (i in 0 until mTenantsCount) {
            mTenants.add(Tenant(endDate = endDate))
        }
        mTenantAdapter.notifyDataSetChanged()
    }

    private fun isInvalidTenants(): Boolean {
        if (mTenants.isEmpty()) {
            return true
        }

        var isInvalid = false
        for (tenant in mTenants) {
            if (tenant.name.isEmpty() || tenant.startDate.isEmpty() || tenant.endDate.isEmpty()) {
                isInvalid = true
                break
            }
        }
        return isInvalid
    }
}
