package com.example.yelp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yelp.Adapters.ImageSliderAdapter
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.databinding.FragmentInfoBinding
import com.example.yelp.databinding.FragmentMapBinding
import com.google.gson.Gson
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class InfoFragment : Fragment() {

    private var __binding: FragmentInfoBinding? = null
    private val binding get() = __binding
    private var detailData: Detail? = null
    private val calendar: Calendar = Calendar.getInstance()
    val currDate = calendar.timeInMillis
    val currYear = calendar.get(Calendar.YEAR)
    val currDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currMonth = calendar.get(Calendar.MONTH)

    var TAG = "InfoFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            detailData =  Gson().fromJson(it.getString("detailData"), Detail::class.java)
        }
        Log.d(TAG, detailData.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        __binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
     binding!!.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.rv.adapter = ImageSliderAdapter(detailData!!.photos)
//        for(i in detailData!!.photos.indices) {
//            var scale: Float = context?.resources?.displayMetrics!!.density
//            var imageView: ImageView = ImageView(context)
//            imageView?.layoutParams?.width = (300 * scale + 0.5).toInt()
//            imageView?.layoutParams?.height = LinearLayout.LayoutParams.MATCH_PARENT
//            Glide.with(binding!!.root).load(detailData!!.photos[i]).into(imageView)
//
//         //   imageView.adjustViewBounds = true
//
//           binding?.linearLayout?.addView(imageView, i)
//
//        }



        binding!!.btnReserve.setOnClickListener {
            calendar.set(Calendar.YEAR, currYear)
            calendar.set(Calendar.MONTH, currMonth)
            calendar.set(Calendar.DAY_OF_MONTH, currDay)
            val resDialogView = LayoutInflater.from(context).inflate(R.layout.reservation_dialog, null)
            resDialogView.findViewById<TextView>(R.id.tvTitle).text = detailData?.name
            val mBuilder = AlertDialog.Builder(context,)
                .setView(resDialogView)
            val date : DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                var format = "MM-dd-yyyy"
                val dateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.US)
                resDialogView.findViewById<EditText>(R.id.etdate).setText(dateFormat.format(calendar.time))
            }
            resDialogView.findViewById<EditText>(R.id.etdate).setOnClickListener {
                val dialog: DatePickerDialog = DatePickerDialog(it.context, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                dialog.datePicker.minDate = currDate
                dialog.show()
            }
            val mAlertDialog = mBuilder.show()
        }




        return binding?.root
    }


}