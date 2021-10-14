package com.lazday.covid_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lazday.covid_19.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        getDataGlobal()
        getData()

        val button : Button = findViewById(R.id.button1)
        button.setOnClickListener(this)
    }

    private fun getData(){
        showLoading(true)
        ApiService.endpoint.getData().enqueue(object : Callback<List<MainModel>> {
            override fun onResponse(
                call: Call<List<MainModel>>,
                response: Response<List<MainModel>>
            ) {
                if (response.isSuccessful){
                    showLoading(false)
                    val mainModel : List<MainModel> = response.body()!!
                    setValue(mainModel)
                }
            }

            override fun onFailure(call: Call<List<MainModel>>, t: Throwable) {
                println("pap gagal ${t.message}")
                showLoading(false)
            }
        })
    }

    private fun getProvince(key: String){
        progresBar2.visibility = View.VISIBLE
        ApiService.endpoint.getProvince().enqueue(object : Callback<List<ProvinceModel>>{
            override fun onResponse(
                call: Call<List<ProvinceModel>>,
                response: Response<List<ProvinceModel>>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    progresBar2.visibility = View.GONE
                    val province : List<ProvinceModel> = response.body()!!
                    setProvince(province, key)
                }
            }

            override fun onFailure(call: Call<List<ProvinceModel>>, t: Throwable) {
                showLoading(false)
            }

        })
    }

    private fun getDataGlobal(){
        ApiService.endpoint.getGlobalPositif().enqueue(object : Callback<GlobalResponse>{
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                if (response.isSuccessful){
                    val response = response.body() as GlobalResponse
                    global_positif.setText(response.value)
                }
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        ApiService.endpoint.getGlobalSembuh().enqueue(object : Callback<GlobalResponse>{
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                if (response.isSuccessful){
                    val response = response.body() as GlobalResponse
                    global_sembuh.setText(response.value)
                }
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        ApiService.endpoint.getGlobalMeninggal().enqueue(object : Callback<GlobalResponse>{
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                if (response.isSuccessful){
                    val response = response.body() as GlobalResponse
                    global_meninggal.setText(response.value)
                }
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setProvince(provinceModel: List<ProvinceModel>, key:String){
        val response =  provinceModel.filter {
            it.attributes.Provinsi.toLowerCase() == key.toLowerCase()
        }.singleOrNull()
        if (response == null){
            text_value2.setText("provinsi tidak ditemukan")
        }else{
            text_value2.setText("Data Covid 19 di provinsi ${response.attributes.Provinsi} : " +
                    "\n positif  ${response.attributes.Kasus_Posi} orang" +
                    "\n sembuh ${response.attributes.Kasus_Semb} orang" +
                    "\n Meninggal ${response.attributes.Kasus_Meni} orang")
        }
    }

    private fun setValue(mainModel: List<MainModel>){
        val response = mainModel[0]
        println("pap sukses")
        text_value.setText("Positif :\n ${response.positif} \n\nSembuh :\n ${response.sembuh} \n\nMeninggal :\n ${response.meninggal}")
    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> progresBar.visibility = View.VISIBLE
            false -> progresBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button1 -> {
                val key = edit_text.text
                getProvince(key.toString())
            }
        }
    }
}
