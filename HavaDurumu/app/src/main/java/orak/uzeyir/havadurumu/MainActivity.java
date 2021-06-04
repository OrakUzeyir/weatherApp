package orak.uzeyir.havadurumu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    TextView Progress;
    int ProgressValue;

    protected Handler handler;
    Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Progress = findViewById(R.id.textView1);
        myThread = new Thread(new CountingThread(0));
        myThread.start();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) { // update progress.
                    Progress.setText("Uygulama Açılıyor... \n" +
                            " " + ProgressValue + "%");
                } else if (msg.what == 1) { //finished.
                    Progress.setText("Yönlendiriliyor... \n" +
                            " " + ProgressValue + "%");

                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

    }

    class CountingThread implements Runnable {
        int i = 0;

        CountingThread(int start) {
            i = start;
        }

        @Override
        public void run() {
            while (i < 100) {
                SystemClock.sleep(100);
                i++;
                if (i % 5 == 0) {
                    ProgressValue = i;
                    handler.sendEmptyMessage(0);
                }
            }
            ProgressValue = i;
            handler.sendEmptyMessage(1);
        }
    }
}