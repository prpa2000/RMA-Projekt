package com.example.klubovi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class MainAdapter(options: FirebaseRecyclerOptions<MainModel>) :
    FirebaseRecyclerAdapter<MainModel, MainAdapter.MyViewHolder>(options) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var img: CircleImageView = itemView.findViewById(R.id.img1)
        var name: TextView = itemView.findViewById(R.id.nametext)
        var year: TextView = itemView.findViewById(R.id.yeartext)
        var trophies: TextView = itemView.findViewById(R.id.trophiestext)

        var btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: MainModel) {
        holder.name.text = model.name
        holder.year.text = model.year
        holder.trophies.text = model.trophies


        Glide.with(holder.img.context).load(model.curl).placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
            .circleCrop()
            .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
            .into(holder.img)

        holder.btnEdit.setOnClickListener {
            val dialogPlus = DialogPlus.newDialog(holder.img.context)
                .setContentHolder(ViewHolder(R.layout.update_popup))
                .setExpanded(true, 1200)
                .create()



            val view = dialogPlus.holderView
            val name = view.findViewById<EditText>(R.id.nameTxt)
            val trophies = view.findViewById<EditText>(R.id.trophiesTxt)
            val year = view.findViewById<EditText>(R.id.yearTxt)
            val curl = view.findViewById<EditText>(R.id.imgUrl)
            val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)

            name.setText(model.name)
            trophies.setText(model.trophies)
            year.setText(model.year)
            curl.setText(model.curl)

            dialogPlus.show();

            btnUpdate.setOnClickListener {
                val map = HashMap<String, Any>()
                map["name"] = name.text.toString()
                map["trophies"] = trophies.text.toString()
                map["year"] = year.text.toString()
                map["curl"] = curl.text.toString()
                if(TextUtils.isEmpty(name.text) || TextUtils.isEmpty(trophies.text) || TextUtils.isEmpty(year.text) ||TextUtils.isEmpty(curl.text)){
                    Toast.makeText(holder.name.context, "Enter all fields", Toast.LENGTH_SHORT).show()
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("clubs")
                        .child(getRef(position).key!!)
                        .updateChildren(map)
                        .addOnSuccessListener {
                            Toast.makeText(
                                holder.name.context,
                                "Updated Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialogPlus.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(holder.name.context, "Error", Toast.LENGTH_SHORT).show()
                            dialogPlus.dismiss()
                        }
                }

            }



        }

        holder.btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(holder.name.context)
            builder.setTitle("Are you sure?")

            builder.setPositiveButton("Delete") { dialog, which ->
                FirebaseDatabase.getInstance().getReference().child("clubs").child(getRef(position).key!!).removeValue()

            }
                .setNegativeButton("Cancel") {dialog, which ->

                }
            builder.show();

        }



    }
}