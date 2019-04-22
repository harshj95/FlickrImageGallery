package com.application.flickr.ui.main.adapter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.application.flickr.R
import com.application.flickr.ui.common.BaseDialog
import com.application.flickr.ui.main.MainViewModel
import kotlinx.android.synthetic.main.dialog_grid_picker.*

/**
 * Created by Harsh Jain on 22/04/19.
 */

class GridPickerDialog : BaseDialog() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun getLayout(): Int = R.layout.dialog_grid_picker

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(resources.displayMetrics.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (arguments!!.getInt("cols")) {
            2 -> {
                rb_two_columns.isChecked = true
                rb_three_columns.isChecked = false
                rb_four_columns.isChecked = false
            }
            3 -> {
                rb_two_columns.isChecked = false
                rb_three_columns.isChecked = true
                rb_four_columns.isChecked = false
            }
            4 -> {
                rb_two_columns.isChecked = false
                rb_three_columns.isChecked = false
                rb_four_columns.isChecked = true
            }
        }

        radio_group_columns.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_two_columns -> {
                    mainViewModel.columns.value = 2
                    dismiss()
                }

                R.id.rb_three_columns -> {
                    mainViewModel.columns.value = 3
                    dismiss()
                }

                R.id.rb_four_columns -> {
                    mainViewModel.columns.value = 4
                    dismiss()
                }
            }
        }
    }

    companion object {
        internal fun newInstance(columns: Int): GridPickerDialog {
            val bundle = Bundle().apply {
                putInt("cols", columns)
            }

            return GridPickerDialog().apply {
                arguments = bundle
            }
        }
    }
}