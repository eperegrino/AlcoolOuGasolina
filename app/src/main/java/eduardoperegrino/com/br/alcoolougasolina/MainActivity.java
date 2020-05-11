package eduardoperegrino.com.br.alcoolougasolina;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static eduardoperegrino.com.br.alcoolougasolina.InfoActivity.INFO_ACTIVITY_RESULT_CODE;

public class MainActivity extends AppCompatActivity {

    //PREÇO MÁXIMO DO ÁLCOOL = PREÇO DA GASOLINA X 0,70
    //PA / PG = 0,70
    private SeekBar gasolina;
    private SeekBar alcool;

    private ProgressBar result;

    private TextView precoGasolina;
    private TextView precoAlcool;
    private TextView resultText;

    private Switch keepAlcohol;

    private ImageView info;

    public static final String INFO_ACTIVITY_RESULT_NAME_VALUE = "rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    //inicializa as variaveis e suas funçoes e eventos
    private void init() {
        gasolina = findViewById(R.id.seekBar_gasolina);
        gasolina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //altera o valor do text view do preco da gasolina
                int reais = gasolina.getProgress() / 100;
                int centavos = gasolina.getProgress() % 100;
                precoGasolina.setText("R$ " + reais + "." + (centavos < 10 ? "0"+centavos : centavos));

                if(!keepAlcohol.isChecked()) {
                    calculaVantagem();
                } else {
                    calculaPrecoAlcool();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        alcool = findViewById(R.id.seekBar_alcool);
        alcool.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //altera o valor do text view do preco do alcool
                int reais = alcool.getProgress() / 100;
                int centavos = alcool.getProgress() % 100;
                precoAlcool.setText("R$ " + reais + "." + (centavos < 10 ? "0"+centavos : centavos));

                if(!keepAlcohol.isChecked()) {
                    calculaVantagem();
                } else {
                    calculaPrecoGasolina();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        result = findViewById(R.id.progress_result);

        precoGasolina = findViewById(R.id.preco_gasolina);
        precoAlcool = findViewById(R.id.preco_alcool);
        resultText = findViewById(R.id.result);

        keepAlcohol = findViewById(R.id.keep_alcohol);

        info = findViewById(R.id.info_btn);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
               startActivityForResult(intent, INFO_ACTIVITY_RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == INFO_ACTIVITY_RESULT_CODE) {
                Toast toast = Toast.makeText(getApplicationContext(), data.getExtras().get(INFO_ACTIVITY_RESULT_NAME_VALUE).toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.please_rate), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    //verifica o preco do alcool pela gasolina e seta o resultado
    public void calculaVantagem() {
        float precoG = gasolina.getProgress();
        float precoA = alcool.getProgress();
        if(precoA/precoG < 0.70) { //alcool é mais vantagem
            resultText.setText(R.string.alcool);
        } else if(precoA/precoG > 0.70) { //gasolina é mais vantagem
            resultText.setText(R.string.gasolina);
        } else { //tanto faz
            resultText.setText("Tanto faz");
        }
        result.setProgress((int)((precoA/precoG)*100));
    }

    //Ao alterar o preco do alcool, altera o preco da gasolina de forma a manter o alcool melhor
    public void calculaPrecoGasolina() {
        float precoA = alcool.getProgress();
        float precoG = (float) (precoA / 0.70);
        gasolina.setProgress((int) precoG);
    }

    //Ao alterar o preco da gasolina, altera o preco do alcool de forma a mante-lo melhor
    public void calculaPrecoAlcool() {
        float precoG = gasolina.getProgress();
        float precoA = (float) (precoG * 0.70);
        alcool.setProgress((int) precoA);
    }
}
