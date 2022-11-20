package com.example.yelp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yelp.Adapters.ImageSliderAdapter
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.Reservation
import com.example.yelp.Models.ReservationModel
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
    lateinit var sharedPref: SharedPreferences
   // lateinit var resDialogView: View
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


        sharedPref = requireContext().getSharedPreferences("MyReservations", 0)
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

            setInfoDetails()

        binding!!.btnReserve.setOnClickListener {
            calendar.set(Calendar.YEAR, currYear)
            calendar.set(Calendar.MONTH, currMonth)
            calendar.set(Calendar.DAY_OF_MONTH, currDay)
            val resDialogView = LayoutInflater.from(context).inflate(R.layout.reservation_dialog, null)
            resDialogView.findViewById<TextView>(R.id.tvTitle).text = detailData?.name
            val mBuilder = AlertDialog.Builder(context,)
                .setView(resDialogView)
                .setNegativeButton("CANCEL", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, i: Int) {

                    }

                })
                .setPositiveButton("SUBMIT", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int) {
                        if(CheckFields(resDialogView)) {
                            addReservation(resDialogView)
                            Toast.makeText(context, "Rerservation Booked", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            val date : DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val format = "MM-dd-yyyy"
                val dateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.US)
                resDialogView.findViewById<EditText>(R.id.etdate).setText(dateFormat.format(calendar.time))
            }
            resDialogView.findViewById<EditText>(R.id.etdate).setOnClickListener {
                val dialog: DatePickerDialog = DatePickerDialog(it.context, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                dialog.datePicker.minDate = currDate
                dialog.show()
            }
            resDialogView.findViewById<EditText>(R.id.ettime).setOnClickListener {
                val currTime = Calendar.getInstance()
                val startHour= currTime.get(Calendar.HOUR_OF_DAY)
                val startMinutes = currTime.get(Calendar.MINUTE)

                TimePickerDialog(it.context, TimePickerDialog.OnTimeSetListener { timePicker, hour, minutes ->
                    resDialogView.findViewById<EditText>(R.id.ettime).setText("${hour.toString().padStart(2,'0')}:${minutes.toString().padStart(2, '0')}")
                }, startHour, startMinutes, false).show()
            }
            val mAlertDialog = mBuilder.show()
        }




        return binding?.root
    }

    private fun setInfoDetails() {
        binding?.tvAddress?.text = detailData!!.location.display_address.joinToString(" ")
        detailData!!.display_phone?.let {
            if(it.isNotEmpty()) {
                binding?.tvPhno?.text =it
            }
        }

        detailData!!.hours?.let {
            it[0].is_open_now?.let {
                if(it)
                {
                    binding?.tvStatus?.text = "Open Now"
                    binding?.tvStatus?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                else{
                    binding?.tvStatus?.text = "Closed"
                    binding?.tvStatus?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
        }


        detailData!!.price?.let {
            binding?.tvPrice?.text = it
        }

        detailData!!.categories?.let {
            val catList = it.map {
                it.title
            }
            binding?.tvCategory?.text = catList.joinToString("|")
        }
    }


    fun addReservation(resDialogView : View){
        val _email: String = resDialogView.findViewById<EditText>(R.id.etemail).text.toString()
        val _date: String = resDialogView.findViewById<EditText>(R.id.etdate).text.toString()
        val _time: String = resDialogView.findViewById<EditText>(R.id.ettime).text.toString()
        val reservation: Reservation = Reservation(detailData!!.id, detailData!!.name, _email, _date, _time)
        val reservations: ReservationModel = Gson().fromJson(sharedPref.getString("reservations", ""), ReservationModel::class.java)
        reservations.reservations.add(reservation)
        val editor = sharedPref.edit()
        editor.putString("reservations", Gson().toJson(reservations))
        editor.commit()
        Log.d("Shared Pref", Gson().fromJson(sharedPref.getString("reservations", ""), ReservationModel::class.java).toString())

    }


    private fun CheckFields(resDialogView: View) : Boolean {
        val _email: String = resDialogView.findViewById<EditText>(R.id.etemail).text.toString()
        val _time: String = resDialogView.findViewById<EditText>(R.id.ettime).text.toString()
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            Toast.makeText(context, "InValid Email Address", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
            return false
        }
        val _hours = _time.split(":")[0].toInt()
        val _mins = _time.split(":")[1].toInt()
        if(!((_hours in 10..16 && _mins in 0..60) || (_hours == 17 && _mins == 0))){
            Toast.makeText(context, "Time should be between 10AM and 5PM", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
            return false
        }

        return true
    }


}