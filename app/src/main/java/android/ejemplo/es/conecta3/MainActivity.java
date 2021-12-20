package android.ejemplo.es.conecta3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private boolean rojo=true;
    private String[][] tablero= new String[3][3];
    private boolean ganado=false;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rellenarTablero();
    }

    public void rellenarTablero(){
        for(int i=0; i<tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j]=" ";
            }
        }
    }

    public void rellenar(View v){
        //Falta lo de reiniciar
        if(!ganado){
            ImageView casilla= (ImageView) v;
            String[] pos=v.getTag().toString().split(",");
            if(tablero[Integer.parseInt(pos[0])][Integer.parseInt(pos[1])].equals(" ")){
                if(rojo){
                    casilla.setImageResource(R.drawable.yellow);
                    tablero[Integer.parseInt(pos[0])][Integer.parseInt(pos[1])]="rojo";
                    rojo=false;
                } else{
                    casilla.setImageResource(R.drawable.red);
                    tablero[Integer.parseInt(pos[0])][Integer.parseInt(pos[1])]="amarillo";
                    rojo=true;
                }
                casilla.setTranslationY(-1000);
                casilla.animate().translationYBy(1000).rotation(3600).setDuration(1000);
                if(ganar()){
                    ganado=true;
                    if(rojo){
                        mensaje("El jugador rojo ha ganado");
                    } else{
                        mensaje("El jugador amarillo ha ganado");
                    }
                    sonido();
                    reiniciar();
                }
            }
        }
    }

    public boolean ganar(){
        for(int i=0; i<tablero.length; i++) {
            if (!tablero[i][1].equals(" ")) {
                if (tablero[i][0].equals(tablero[i][1]) && tablero[i][1].equals(tablero[i][2])) {
                    return true;
                }
            }
            if(!tablero[1][i].equals(" ")){
                if (tablero[0][i].equals(tablero[1][i]) && tablero[1][i].equals(tablero[2][i])) {
                    return true;
                }
            }
        }
        if(!tablero[1][1].equals(" ")){
            if(tablero[0][0].equals(tablero[1][1]) && tablero[1][1].equals(tablero[2][2])){
                return true;
            }
            if(tablero[2][0].equals(tablero[1][1]) && tablero[1][1].equals(tablero[0][2])){
                return true;
            }
        }
        return false;
    }

    public void mensaje(String message){
        TextView mensaje = (TextView) findViewById(R.id.mensaje);
        mensaje.setText(message);
    }

    public void sonido(){
        if(rojo){
            mp=MediaPlayer.create(this, R.raw.rojo);
        } else{
            mp=MediaPlayer.create(this, R.raw.amarillo);
        }
        mp.start();
    }

    public void reiniciar(){
        Button reiniciar = (Button) findViewById(R.id.reiniciar);
        reiniciar.setVisibility(View.VISIBLE);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rellenarTablero();
                quitarFichas();
                mensaje(null);
                ganado=false;
                reiniciar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void quitarFichas(){
        GridLayout tablero = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < tablero.getChildCount(); i++) {
            ImageView casilla = (ImageView) tablero.getChildAt(i);
            casilla.setImageResource(0);
        }
    }
}
