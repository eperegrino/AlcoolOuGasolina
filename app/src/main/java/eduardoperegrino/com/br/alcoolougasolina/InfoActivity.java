package eduardoperegrino.com.br.alcoolougasolina;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    private Button rateBtn;

    private ImageView myLink;

    public static final int INFO_ACTIVITY_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void init() {
        rateBtn = findViewById(R.id.rate_btn);
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Criar função de avaliação do aplicativo e mandar o valor de volta para a main activity (startActivityForResult)
                Intent data = new Intent();
                data.putExtra(MainActivity.INFO_ACTIVITY_RESULT_NAME_VALUE, getString(R.string.rate_app_msg));
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        myLink = findViewById(R.id.my_link);
        myLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Activity implicita view com link para meu linkedin
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MY_LINKEDIN_ACCOUNT));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
