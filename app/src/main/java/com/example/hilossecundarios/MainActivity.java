package com.example.hilossecundarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

/*
Autor: Juan Francisco Sánchez González
Fecha: 05/02/2023
Clase: Actividad que contiene 2 objetos, una barra de progreso (ProgressBar) y un ToggleButton. Al pulsar el
botón se inicia un hilo secundario con el método runOnUiThread() que inicia el progreso de la barra.
*/

public class MainActivity extends AppCompatActivity {

    // Constantes ProgressBar y Tiempo de progreso
    private final static int INICIO_BARRA_PROGRESO = 0;
    private final static int MAX_BARRA_PROGRESO = 1000;
    private final static int BARRA_PROGRESO = 50;
    private final static int TIEMPO_PROGRESO = 500;

    private ProgressBar barra;
    private ToggleButton boton;
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Componente ProgressBar
        barra = (ProgressBar) findViewById(R.id.progressBar);
        barra.setMax(MAX_BARRA_PROGRESO);
        barra.setProgress(INICIO_BARRA_PROGRESO);

        // Componente ToggleButton
        boton = (ToggleButton) findViewById(R.id.toggleButton);
        boton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cont = INICIO_BARRA_PROGRESO;
                    // Creamos el hilo secundario que actualizará la barra de progreso
                    new Thread() {
                        public void run() {
                            while (cont <= MAX_BARRA_PROGRESO) {
                                try {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            barra.setProgress(cont);
                                        }
                                    });
                                    Thread.sleep(TIEMPO_PROGRESO);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                cont += BARRA_PROGRESO;
                            }
                        }
                    }.start();
                } else {
                    // Reiniciamos la ProgressBar
                    barra.setProgress(INICIO_BARRA_PROGRESO);
                }
            }
        });

    }
}