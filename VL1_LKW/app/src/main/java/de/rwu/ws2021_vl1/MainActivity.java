package de.rwu.ws2021_vl1;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import de.rwu.ws2021_vl1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ImageView resultImage;

    private int truckSelected = 0;

    private int weight = 0;

    private boolean isValidDay;

    private DatePickerDialog datePickerDialog;

    private Button btnDate;

    private Spinner spinner;

    private EditText editText;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areInputsValid()) {
                    resultImage.setImageResource(R.drawable.ok);
                } else {
                    resultImage.setImageResource(R.drawable.notok);
                }

            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerLkwType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_lkw_types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                truckSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        btnDate = (Button) findViewById(R.id.btnDate);

        datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 7 = Samstag, 1 = Sonntag
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek != 1 && dayOfWeek != 7) {
                            isValidDay = true;
                        } else {
                            isValidDay = false;
                        }
                        btnDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        resultImage = (ImageView) findViewById(R.id.imageResult);

        editText = (EditText) findViewById(R.id.editTextNumberDecimal);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupLadung);
        radioGroup.check(R.id.radioButtonErde);

    }

    public void onRadioButtonSelection(View view) {
        switch (view.getId()) {
            case R.id.radioButtonErde:
                Toast.makeText(this, "Erde", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButtonKies:
                Toast.makeText(this, "Kies", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void buttonClicked(View view) {
        datePickerDialog.show();
    }

    public boolean areInputsValid() {
        if (!isValidDay) return false;

        if (truckSelected == 0 && weight > 30)
            return false;

        if (truckSelected == 1 && weight > 20)
            return false;

        if (truckSelected == 2 && weight > 21)
            return false;

        return true;
    }

    public void clearInputs() {
        btnDate.setText(R.string.btnDate);
        spinner.setSelection(0);
        editText.setText("");
        radioGroup.check(R.id.radioButtonErde);
        isValidDay = false;
        truckSelected = 0;
        weight = 0;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_eingabe_loeschen) {
            clearInputs();
        }

        return super.onOptionsItemSelected(item);
    }

}