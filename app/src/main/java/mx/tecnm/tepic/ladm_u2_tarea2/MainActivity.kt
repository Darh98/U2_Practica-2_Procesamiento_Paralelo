package mx.tecnm.tepic.ladm_u2_tarea2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var detenido=true
    var pau=false
    var contadorHilo=0
    var hilito=Hilo(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play.setOnClickListener {
            if(detenido){
                try{
                    hilito.start()
                    detenido=false
                }catch (e:Exception) {
                    AlertDialog.Builder(this).setMessage("El hilo no se puede volver a ejecutar")
                        .setTitle("Aviso").show()
                }
            }else if(pau){
                hilito.pausar()
                pau=false
            }else{
                Toast.makeText(this,"LA CANCION YA SE ESTÁ REPRODUCIENDO", Toast.LENGTH_LONG).show()
            }
        }
        pause.setOnClickListener {
            if(!pau) {
                try {
                    pau = true
                    hilito.pausar()
                } catch (e: Exception) {
                    AlertDialog.Builder(this).setMessage("Ha ocurrido un error")
                        .setTitle("Aviso").show()
                }
            }else{
                Toast.makeText(this, "LA CANCION YA ESTÁ PAUSADA", Toast.LENGTH_LONG).show()
            }
        }
        stop.setOnClickListener {
            try {
                contadorHilo = 0
                detenido=true
                pau=false
                hilito.terminarHilo()
            }catch(e:Exception){
                Toast.makeText(this,"LA CANCION YA ESTÁ DETENIDA", Toast.LENGTH_LONG).show()
            }
        }
    }
}


//CLASE HILO
class Hilo(p:MainActivity):Thread(){
    var puntero = p//Existe solo en esta linea.
    var mantener = true
    var despausado=true

    fun pausar(){
        despausado=!despausado
    }
    fun reepHilo(){
        mantener=true
    }
    fun terminarHilo(){
        mantener=false
        puntero.contador.text = "0"

    }

    override fun run(){
        super.run()
        //Realmente se ejecuta una vez en segundo plano.
        while(mantener) {
            if (despausado) {
                puntero.contadorHilo++
                puntero.runOnUiThread {
                    puntero.contador.text = ""+puntero.contadorHilo
                }

            }
            sleep(1000)
        }
        sleep(1000)
    }
}