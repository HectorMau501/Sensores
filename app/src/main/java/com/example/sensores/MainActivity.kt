package com.example.sensores

import android.annotation.SuppressLint
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener{

    //Instancias
    private lateinit var detalle: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var existeSensorProximidad: Boolean = false
    private lateinit var listadoSensores: List<Sensor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Asociar con componente grafico
        detalle = findViewById(R.id.txtDetalle)
        //Gestionar los sensores del dispositivo
        sensorManager= getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
    }

    fun clickPosicion(view: View?){
        //Validar la exitencia del sensor de de temperatura
        val orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if(orientationSensor != null){
            Toast.makeText(applicationContext, "El dispositivo tiene sensor de posicion",Toast.LENGTH_SHORT).show()
            detalle.setBackgroundColor(Color.BLUE)
            detalle.text = "Propiedades del sensor de Posicion: \n Nombre: ${orientationSensor.name.toString()}"+
                    "\nVersion: ${orientationSensor.version}\nFabricante: ${orientationSensor.vendor.toString()}"
        }else{
            Toast.makeText(applicationContext, "El dispositivo no tiene sensor de posicion",Toast.LENGTH_SHORT).show()
        }

    }

    fun clickMovimiento(view: View?){
        // Validar la existencia del sensor de gravedad
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if(gravitySensor != null){
            Toast.makeText(applicationContext, "El dispositivo tiene sensor de gravedad",Toast.LENGTH_SHORT).show()
            detalle.setBackgroundColor(Color.GRAY)
            detalle.text = "Propiedades del sensor de Gravedad: \n Nombre: ${gravitySensor.name}\n" +
                    "Version: ${gravitySensor.version}\nFabricante: ${gravitySensor.vendor}"
        } else {
            Toast.makeText(applicationContext, "El dispositivo no tiene sensor de gravedad",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun clickListado(view: View?){
        //Gestion de sensores
        //Gestionar los sensores del dispositivo
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        //Obtener los detalles y colocar en una lista
        listadoSensores = sensorManager.getSensorList(Sensor.TYPE_ALL)
        detalle.setBackgroundColor(Color.WHITE)
        detalle.text="Lista de sensores del dispositivo"
        //Para cada uno de los sensores identificados, mostrar nombres
        for(sensor in listadoSensores){
            detalle.text = "${detalle.text}\nNombre: ${sensor.name}\nVersion: ${sensor.version}"
        }//for
    }

    @SuppressLint("SetTextI18n")
    fun clickMagnetico(view: View?){
        //Validar la existencia del sensor magnetivo
        if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            Toast.makeText(applicationContext, "El dispositivo tiene sensor magnetivo",Toast.LENGTH_SHORT).show()
            //Vincular con el sensor
            detalle.setBackgroundColor(Color.GRAY)
            detalle.text = "Propiedades del sensor Magnetico: \n Nombre: ${sensor.name.toString()}"+
                    "\nVersion: ${sensor.version}\nFabricante: ${sensor.vendor.toString()}"
        }else{
            Toast.makeText(applicationContext, "El dispositivo no tiene sensor magnetivo",Toast.LENGTH_SHORT).show()
        }
    }

    fun clickProximidad(view: View?){
        //Validar la exitencia del sensor de proximidad
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            val proximidadSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            existeSensorProximidad = true
            detalle.text = "El dispositivo tiene sensor: ${proximidadSensor!!.name}"
            detalle.setBackgroundColor(Color.GREEN)
        }else{
            detalle.text = "No se cuenta con sensor de proximidad"
            existeSensorProximidad = false
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //Obtiene el valor que arroja el sensor
        val valorCambio: Float
        //Evaluar el valor para realizar alguna acción
        if (existeSensorProximidad) {
            valorCambio = event!!.values[0]
            if (valorCambio < 1.0) {
                detalle.textSize = 30f
                detalle.setBackgroundColor(Color.BLUE)
                detalle.setTextColor(Color.WHITE)
                detalle.text = "\nCERCA $valorCambio"
            } else {
                detalle.textSize = 14f
                detalle.setBackgroundColor(Color.GREEN)
                detalle.setTextColor(Color.BLACK)
                detalle.text = "\nLEJOS $valorCambio"
            } //if-else valorCambio
        } else {
            Toast.makeText(applicationContext, "Sin cambios.",Toast.LENGTH_SHORT).show()
        } //if-else existeSensorProximidad
    }//onSensorChanged

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}