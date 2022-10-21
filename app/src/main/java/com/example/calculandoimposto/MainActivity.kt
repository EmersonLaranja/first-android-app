package com.example.calculandoimposto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.calculandoimposto.databinding.ActivityMainBinding
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.buttonCalcula.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        var salario = binding.salario.text.toString().toFloatOrNull() ?: 0F
        var gastos = binding.gastos.text.toString().toFloatOrNull() ?: 0F

        if (salario == 0F) {
            Toast.makeText(applicationContext, "Digite um valor válido!", Toast.LENGTH_SHORT).show()
            return;
        }


        var aliquota = retornaAliquota(salario)
        var isencao = calculaIsencao(salario, aliquota)
        var imposto = String.format("%.2f",calculaImposto(salario, aliquota, gastos, isencao))
        binding.roubo.text = "R$ " + imposto

        Toast.makeText(applicationContext, "Valor calculado!", Toast.LENGTH_SHORT).show()


    }

    fun calculaImposto(salario: Float, aliquota: Float, gastos: Float, isencao: Float): Float {
        return (isencao * aliquota)-gastos
    }

    fun calculaIsencao(salario: Float, aliquota: Float): Float {
        if (salario <= 1903.98) {
            return 0F
        } else if (salario >= 1903.99 && salario <= 2826.65) {
            return (salario - 1903.99).toFloat()
        } else if (salario >= 2826.66 && salario <= 3751.05) {
            return (salario - 2826.66).toFloat()
        } else if (salario >= 3751.06 && salario <= 4664.68) {
            return (salario - 3751.06).toFloat()
        } else {
            return (salario - 3751.06).toFloat()
        }
    }

    fun retornaAliquota(salario: Float): Float {
        if (salario <= 1903.98) {
            return 0F
        } else if (salario >= 1903.99 && salario <= 2826.65) {
            return (7.5 / 100F).toFloat()
        } else if (salario >= 2826.66 && salario <= 3751.05) {
            return (15 / 100F).toFloat()
        } else if (salario >= 3751.06 && salario <= 4664.68) {
            return (22.5 / 100F).toFloat()
        } else {
            return (27.5 / 100F).toFloat()
        }
    }
}